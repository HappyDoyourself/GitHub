package cn.com.dubbo.service.payment.platform;

import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.OrderPaymentMessageLog;
import cn.com.dubbo.model.OrderPaymentTypeList;
import cn.com.dubbo.redis.CacheUtil;
import cn.com.dubbo.service.MyPayService;
import cn.com.dubbo.service.OrderService;
import cn.com.dubbo.service.impl.BaseServiceImpl;

import cn.com.dubbo.model.PaymentResult;
import cn.com.dubbo.task.RefundQueryTask;
import cn.com.jiuyao.pay.common.constant.ResponseCodeConstants;
import cn.com.jiuyao.pay.common.util.*;
import cn.com.jiuyao.util.payments.alipayWap.RSA;
import cn.com.jiuyao.util.payments.alipayWap.config.AlipayConfig;
import com.alibaba.fastjson.JSONObject;
import com.jiuyao.ec.common.model.SysCode;
import cn.com.dubbo.service.SystemService;
import com.jiuyao.ec.common.type.OrderBusinessType;
import com.jiuyao.ec.common.type.OrderStatus;

import cn.com.dubbo.model.OrderInfo;
import cn.com.dubbo.model.OrderLog;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

//import com.founder.ec.order.model.*;

/**
 * 类 <code>PlatformPayment</code>具体的支付平台父类，提供公共方法，继承于Platform
 * 
 * @author jinjin
 * @version 2016-10-20
 */
@Service
public class PlatformPayment extends BaseServiceImpl implements Platform {
	public static final String PAY_DONE_JSP_PATH = "/jsp/myec/pay_done.jsp";
	@Autowired
	protected MyPayService myPayService;
	@Autowired
	protected OrderService orderService;
	@Autowired
	protected SystemService systemService;
	@Autowired
	protected CacheUtil cacheUtil;
	@Override
	public String requestMessagePackage(HttpServletRequest request,
			HttpServletResponse response,OrderPaymentLog orderPaymentLog) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnMessageHandle(HttpServletRequest request,
			HttpServletResponse response, String paymentTypeNo)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String notifyMessageHandle(HttpServletRequest request,
			HttpServletResponse response, String paymentTypeNo)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Map query(OrderPaymentLog orderPaymentLog)
			throws Exception {
		Map<String, Object> orderInfoMap = new HashMap<String, Object>();
		orderInfoMap.put("result", "fail");
		try {
			Map<String, String> parameter = myPayService
					.findPaymentTypeListInfo(orderPaymentLog.getPaymentTypeId());
			orderPaymentLog.setEcPaymentTypeParames(parameter);
			PaymentResult paymentResult = queryByOrder(orderPaymentLog);
			if (null != paymentResult.getIsDone() && paymentResult.getIsDone()) {
				orderPaymentLog.setBackState(paymentResult.getTradeStatus());
				orderPaymentLog.setBackNo(paymentResult.getTradeNo());
				orderPaymentLog.setPaidFee(new BigDecimal(paymentResult.getTotalFee()));
				orderPaymentLog.setQueryState("Y");
				String result = doSaveResultByLog(orderPaymentLog);
				//将获取的信息放入map中返回
				orderInfoMap.put("result", result);
				return orderInfoMap;
			}
		} catch (Exception e) {
			logger.error("=====支付查询异常，paymentTypeId：" + orderPaymentLog.getPaymentTypeId()
					+ ", orderID：" + orderPaymentLog.getBusinessId());
			e.printStackTrace();
		}
		return orderInfoMap;
	}

	@Override
	public Object extra(Object object)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map refund(OrderPaymentLog orderPaymentLog)
			throws Exception {
		Map<String, Object> orderInfoMap = new HashMap<String, Object>();
		JSONObject jsonObject = new JSONObject();
		try {
			Map<String, String> parameter = myPayService
					.findPaymentTypeListInfo(orderPaymentLog.getPaymentTypeId());
			EcPaymentType ecPaymentType = myPayService.
					findPaymentInfoById(orderPaymentLog.getPaymentTypeId().toString());
			orderPaymentLog.setEcPaymentTypeParames(parameter);
			orderPaymentLog.setEcPaymentType(ecPaymentType);
			PaymentResult paymentResult = (PaymentResult)refundByOrder(orderPaymentLog);
			if (paymentResult == null) {
				jsonObject.put("code","0010");
				orderInfoMap.put("result",jsonObject.toString());
				return orderInfoMap;
			}
			//退款已申请状态
			if(ResponseCodeConstants.REFUND_APPLIED_STATE.equals(paymentResult.getRefundStatus())){
				String currentTime = DateUtils.longToDateAll(System
						.currentTimeMillis());
				orderPaymentLog.setBackTime(currentTime);// 返回时间
				orderPaymentLog.setEditTime(currentTime);// 修改时间
				orderPaymentLog.setBackState(paymentResult.getRefundStatus());
				orderPaymentLog.setBackNo(paymentResult.getTradeNo());
				orderPaymentLog.setPaidFee(new BigDecimal(paymentResult.getRefundFee()));
				orderPaymentLog.setRefundState(ResponseCodeConstants.SUCCESS);
				doSaveResultByLog(orderPaymentLog);
				jsonObject.put("code","0000");
				orderInfoMap.put("result",jsonObject.toString());
				/*orderInfoMap.put("result", result);*/
				orderInfoMap.put("refundAmt",paymentResult.getRefundFee());
			} else {
				jsonObject.put("code","0020");
				orderInfoMap.put("result",jsonObject.toString());
				//orderInfoMap.put("result", paymentResult.getRefundStatus());
				orderInfoMap.put("refundAmt","0");
			}
			return orderInfoMap;
		} catch (Exception e) {
			logger.error("=== An exception occurred while refunds,and the businessId is " + orderPaymentLog.getBusinessId());
			e.printStackTrace();
			orderInfoMap.put("result","0030");
			orderInfoMap.put("refundAmt","0");
		}

		return orderInfoMap;
	}

	@Override
	public Object refundNotify(HttpServletRequest request, HttpServletResponse response, String paymentTypeNo) throws Exception {
		return null;
	}
	/**
	 * 订单退款查询
	 * @param orderPaymentLog
	 * @return PaymentResult
	 */
	@Override
	public Map refundQuery(OrderPaymentLog orderPaymentLog) throws Exception {
		Map<String,String> mapQuery = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject();
		Map<String,String > parameter =null;
		//如果是支付宝支付则调用新接口
		if (ArrayUtils.contains(RefundQueryTask.ALIPAY, orderPaymentLog.getPaymentTypeId())){
			parameter = myPayService.findPaymentTypeListInfo(2);
		}

		EcPaymentType ecPaymentType = myPayService.findPaymentInfoById(orderPaymentLog.getPaymentTypeId().toString());

		orderPaymentLog.setEcPaymentTypeParames(parameter);
		orderPaymentLog.setEcPaymentType(ecPaymentType);
		PaymentResult paymentResult = (PaymentResult) refundQueryByOrder(orderPaymentLog);
		//退款已申请状态
		if(paymentResult!=null && ResponseCodeConstants.REFUND_APPLIED_STATE.equals(paymentResult.getRefundStatus())){
			jsonObject.put("code","0000");
			mapQuery.put("result",jsonObject.toString());
		} else {
			jsonObject.put("code","0050");
			mapQuery.put("result",jsonObject.toString());
		}
		return mapQuery;
	}


	public PaymentResult queryByOrder(OrderPaymentLog orderPaymentLog) throws Exception{
		return null;
	}

	public Object refundQueryByOrder(OrderPaymentLog orderPaymentLog) throws Exception{
		System.out.println("******************");
		PaymentResult paymentResult = new PaymentResult();
		Map<String, String> parameter = orderPaymentLog.getEcPaymentTypeParames();
		//获取支付宝sign的参数
		String app_id = parameter.get("app_id"); //支付宝分配给开发者的应用ID
		String method = AlipayConfig.REFUND_QUERY; //接口名称
		String charset = parameter.get("charset"); //字符集
		String sign_type = parameter.get("sign_type"); //签名方式
		String timestamp = DateUtil.DateTimeToStr(new Date());
		String version = parameter.get("version");  // 版本号
		String private_key = parameter.get("private_key"); // 私钥
		String trade_no = orderPaymentLog.getBackNo(); //支付宝交易号，和商户订单号不能同时为空
		String biz_content =  "{\"trade_no\":\"" + trade_no + "\",\"out_request_no\":\"" + orderPaymentLog.getPaymentNo() + "\"}";

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("timestamp",timestamp);
		map.put("app_id", app_id);
		map.put("method", method);
		map.put("charset", charset);
		map.put("sign_type", sign_type);
		map.put("version", version);
		map.put("biz_content",biz_content);
		String signInfo = RSA.getStringSort(map);
		String sign = RSA.sign(signInfo, private_key, charset);
		sign = URLEncoder.encode(sign, "UTF-8"); //转utf-8
		map.put("sign",sign);
		map.put("timestamp", URLEncoder.encode(timestamp,"UTF-8")); //注意发送请求的时间有空格 需要转码,但是求sign是不需要的
		map.put("biz_content", URLEncoder.encode(biz_content,"UTF-8"));  //注意有： 需要转码,但是求sign是不需要的
		String resJSON = HttpClientTools.doGet(parameter.get("paygateway"), map);
		if (org.springframework.util.StringUtils.isEmpty(resJSON)) {
			logger.info("AliPayRefundQuery fail, responseData is null, orderNo ：{" + orderPaymentLog.getBusinessId() +"}");
			return null;
		}

		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(resJSON);
		com.alibaba.fastjson.JSONObject jsonObjectResponse = jsonObject.getJSONObject("alipay_trade_fastpay_refund_query_response");
		if (!AlipayConfig.ALIPAY_CODE.equals(jsonObjectResponse.get("code"))) {
			logger.info("AliPayRefundQuery fail, aliPayRefund responseData： " + jsonObject.get("aliApp_trade_refund_response") + ", orderNo " + orderPaymentLog.getBusinessId());
			return null;
		}else {
			logger.info("AliPayRefundQuery  success, orderNo:" + orderPaymentLog.getBusinessId());
			paymentResult.setRefundStatus(ResponseCodeConstants.REFUND_APPLIED_STATE);
			return paymentResult;
		}
	}



	/**
	 * aliApp订单退款请求
	 * @param orderPaymentLog
	 * @return PaymentResult
	 */
	public Object refundByOrder(OrderPaymentLog orderPaymentLog) throws Exception{
		try {
			Map<String, String> parameter = myPayService.findPaymentTypeListInfo(orderPaymentLog.getPaymentTypeId());
			EcPaymentType ecPaymentType = myPayService.findPaymentInfoById(orderPaymentLog.getPaymentTypeId().toString());
			orderPaymentLog.setEcPaymentTypeParames(parameter);
			orderPaymentLog.setEcPaymentType(ecPaymentType);
			//获取sign签名前的参数
			String app_id = parameter.get("app_id"); //支付宝分配给开发者的应用ID
			String method = AlipayConfig.REFUND_METHOD; //接口名称
			String charset = parameter.get("charset"); //字符集
			String sign_type = parameter.get("sign_type"); //签名方式
			String timestamp = DateUtil.DateTimeToStr(new Date());
			String version = parameter.get("version"); // 版本号
			String private_key = parameter.get("private_key"); // 私钥
			String refund_amount = MathUtil.changeY2F(orderPaymentLog.getPaymentFee()).toString(); // 订单总价
			String trade_no = orderPaymentLog.getBackNo(); //支付宝交易号，和商户订单号不能同时为空
			String bizContentUnEncoder = "{\"trade_no\":\"" + trade_no + "\",\"refund_amount\":\"" + refund_amount + "\"}"; //请求参数的集合 除公共参数外所有请求参数都必须放在这个参数中传递
			String biz_content = URLEncoder.encode(bizContentUnEncoder,"utf-8");  //因为里面含有：在调用get请求时需要转码

			Map<String, String> map = new HashMap<String, String>();
			map.put("app_id", app_id);
			map.put("method", method);
			map.put("charset", charset);
			map.put("sign_type", sign_type);
			map.put("timestamp", timestamp);
			map.put("version", version);
			map.put("biz_content", biz_content);
			String signInfo = RSA.getStringSort(map);
			String sign = RSA.sign(signInfo, private_key, charset);
			sign = URLEncoder.encode(sign, "UTF-8"); //转utf-8
			map.put("sign",sign);

			String resJSON = HttpClientTools.doGet(parameter.get("paygateway"), map);
			if (StringUtils.isEmpty(resJSON)) {
				logger.info("AliPayRefund fail, responseData is null, orderNo ：{" + orderPaymentLog.getBusinessId() +"}");
				return null;
			}

			com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(resJSON);
			if (!AlipayConfig.ALIPAY_CODE.equals(jsonObject.get("code"))) {
				logger.info("AliPayRefund fail, aliPayRefund code " + jsonObject.get("code") +", orderNo " + orderPaymentLog.getBusinessId());
				return null;
			}
			doSaveResultByLog(orderPaymentLog); //添加退款记录
			logger.info("aliPayRefund success, orderNo:" + orderPaymentLog.getBusinessId());
		}catch(Exception e){
			logger.error("=====aliPay refund has exception ," + e);
		}

		return null;
	}

	/**
	 * 签名前校验订单有效性
	 * 
	 * @param request
	 * @param orderNo
	 *            商城订单号
	 * @param total_fee
	 *            单位：元
	 * @param orderPaymentLog
	 * @param orderInfo
	 * @return
	 */
	public String checkPamentBackBeforeSign(HttpServletRequest request,
			String orderNo, BigDecimal total_fee,
			OrderPaymentLog orderPaymentLog, OrderInfo orderInfo) {
		String result = "";
		if (StringUtils.isEmpty(orderNo)) {
			logger.error("====checkPamentBackBeforeSign===无此单号");
			request.setAttribute("paidSuccess", "支付不成功！");
			request.setAttribute("orderId", "无此单号");
			request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo());
			request.setAttribute("payFlag", "failure");
			result = "/jsp/myec/pay_done.jsp";

		} else {
			if (orderPaymentLog != null) {
				if (orderPaymentLog.getBusinessType().equals(
						OrderBusinessType.ORDER)) {
					if (null == orderInfo) {
						logger.error("====checkPamentBackBeforeSign===抱歉没有支付订单号对应的订单");
						request.setAttribute("paidSuccess", "抱歉没有支付订单号对应的订单！");
						request.setAttribute("orderId", "无此单号");
						request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo()); // 支付订单号
						request.setAttribute("payFlag", "failure");
						result = "/jsp/myec/pay_done.jsp";
					} else if (orderInfo.getIsPaid().equals("Y")) {// 判断是否已支付
						request.setAttribute("orderId", orderInfo.getOrderNo());
						request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo());
						request.setAttribute("total_fee", total_fee);
						request.setAttribute("deliveryReceiver",
								orderInfo.getReceiveUser());
						request.setAttribute("deliveryAddressFull",
								orderInfo.getReceiveFullAddress());
						request.setAttribute("payFlag", "success");
						result = "/jsp/myec/pay_done.jsp";
					} else {
						request.setAttribute("orderId", orderInfo.getOrderNo());
						request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo());
						request.setAttribute("total_fee", total_fee);
						request.setAttribute("deliveryReceiver",
								orderInfo.getReceiveUser());
						request.setAttribute("deliveryAddressFull",
								orderInfo.getReceiveFullAddress());
						result = "";
					}

				}
			} else {
				logger.error("====checkPamentBackBeforeSign===抱歉没有此支付订单号对应的记录！");
				request.setAttribute("paidSuccess", "抱歉没有此支付订单号对应的记录！");
				request.setAttribute("orderId", "无此单号");
				request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo()); // 支付订单号
				request.setAttribute("payFlag", "failure");
				result = "/jsp/myec/pay_done.jsp";
			}
		}
		return result;
	}

	/**
	 * 签名前校验订单有效性
	 *
	 * @param request
	 * @param orderPaymentLog
	 * @return
	 */
	public String checkPamentBackBeforeSign(HttpServletRequest request,OrderPaymentLog orderPaymentLog) {
		String result = "";
		if (orderPaymentLog == null) {
			request.setAttribute("paidSuccess", "抱歉没有此支付订单号对应的记录！");
			request.setAttribute("orderId", "无此单号");
			request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo()); // 支付订单号
			request.setAttribute("payFlag", "failure");
			result = "/jsp/myec/pay_done.jsp";
		}
		if (orderPaymentLog.getBusinessType().equals(
				OrderBusinessType.ORDER)) {
			OrderInfo orderInfo = orderService.findOrderById(orderPaymentLog.getBusinessId()+"");
			if (orderInfo == null) {
				request.setAttribute("paidSuccess", "抱歉没有支付订单号对应的订单！");
				request.setAttribute("orderId", "无此单号");
				request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo()); // 支付订单号
				request.setAttribute("payFlag", "failure");
				result = "/jsp/myec/pay_done.jsp";
			}
			//订单重复支付时注释下面代码，用来记录重复支付数据
//			if (null != orderInfo.getIsPaid() && orderInfo.getIsPaid().equals("Y")) {// 判断是否已支付
//				request.setAttribute("orderId", orderInfo.getOrderId());
//				request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo());
//				request.setAttribute("payFlag", "success");
//				result = "/jsp/myec/pay_done.jsp";
//			}
		}
		return result;
	}

	/**
	 * 对日志和订单进行操作的共通函数
	 * @param paymentLog 支付日志对象
	 * @return
	 */
	public synchronized String doSaveResultByLog(OrderPaymentLog paymentLog) throws Exception{

		String result = "";

        Boolean isPaidFlag = false;
		// 重新查询订单支付日志，如果订单返回的流水号存在，且流水号等于返回的流水号，那么就是反复入库，就不再进行操作
		OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
		orderPaymentLog.setPaymentNo(paymentLog.getPaymentNo());
		orderPaymentLog.setPaymentTypeId(paymentLog.getPaymentTypeId());
		orderPaymentLog = myPayService.findOrderPaymentLog(paymentLog,false);
		if (orderPaymentLog == null) {
			return "fail";
		}
		if (orderPaymentLog.getBackNo() != null
				&& orderPaymentLog.getBackNo().equals(paymentLog.getBackNo()) && !OrderBusinessType.REFUND.equals(paymentLog.getBusinessType())) {
			return "success";
		}

		// 根据
		String currentTime = DateUtils.longToDateAll(System.currentTimeMillis());
		orderPaymentLog.setBackState(paymentLog.getBackState());// 支付状态
		orderPaymentLog.setBackNo(paymentLog.getBackNo());// 支付流水号
		orderPaymentLog.setBackTime(currentTime);// 返回时间
		orderPaymentLog.setPaidFee(paymentLog.getPaidFee());// 支付的金额
		orderPaymentLog.setEditTime(currentTime);// 修改时间
		orderPaymentLog.setCardNo(null == paymentLog.getCardNo() ? "" : paymentLog.getCardNo());
		orderPaymentLog.setOpenId(null == paymentLog.getOpenId() ? "" : paymentLog.getOpenId());
		orderPaymentLog.setQueryState(null == paymentLog.getQueryState() ? "N" : paymentLog.getQueryState());
		if (orderPaymentLog.getPaymentFee().setScale(2,BigDecimal.ROUND_HALF_UP).compareTo(paymentLog.getPaidFee().setScale(2, BigDecimal.ROUND_HALF_UP)) == 0) {
			orderPaymentLog.setBackNotes("支付成功");
			orderPaymentLog.setPaidState("Y");
		} else {
			orderPaymentLog.setBackNotes("部分支付");
			orderPaymentLog.setPaidState("P");
		}
		/**
		 * order订单支付
		 */
		if (orderPaymentLog.getBusinessType().equals(
				OrderBusinessType.ORDER)) {
			OrderInfo order = orderService.findOrderById(orderPaymentLog.getBusinessId()+"");
			if (order == null) {
				logger.error("====doSaveResult===该支付单号对应的订单不存在！"+orderPaymentLog.getBusinessId());
				orderPaymentLog.setBackNotes("该支付单号对应的订单不存在");
				result = "fail";
			} else {
				if (order.getIsPaid() == null
						|| order.getIsPaid().equals("N")
						|| order.getIsPaid().equals("P")) {

					//重新获取订单信息，用于修改订单支付状态
					OrderInfo orderInfo = orderService.findOrderById(order.getOrderNo());
					orderInfo.setPaymentTypeId(paymentLog.getPaymentTypeId());
					orderInfo.setPaymentId(paymentLog.getPaymentTypeId());
					orderInfo.setPaymentTypeNo(null);
					orderInfo.setPaymentNo(paymentLog.getPaymentNo());
					orderInfo.setPaidTime(currentTime);
					orderInfo.setEditTime(currentTime);
					//待支付金额 = 待支付金额 -支付金额
					orderInfo.setOrderPayFee(orderInfo.getOrderPayFee().subtract(paymentLog.getPaidFee()));
					if(orderInfo.getOrderPayFee().compareTo(BigDecimal.ZERO) < 0){
						logger.info(paymentLog.getPaymentTypeId() + " 支付完成后，重复修改订单状态，订单待支付金额小于0异常，" +
								"订单号： " + orderInfo.getOrderNo());
						orderInfo.setOrderPayFee(BigDecimal.ZERO);
						isPaidFlag = true;//重复支付orderLog不重复添加（订单支付金额仍然有错，所以重复的不处理，直接返回success）
						result="success";//fanhongtao 修改此处，否则无法修改订单日志表
						//return "success";//重复订单直接返回
					}
					if (orderInfo.getOrderPayFee().compareTo(BigDecimal.ZERO) == 0) {
						orderInfo.setIsPaid("Y");
						//处方订单标识(0-普通订单, 1-单轨处方药订单, 2-双轨处方药订单) 则order_status 做想应修改
						switch (orderInfo.getHaveCfy()){
							case 0:
								orderInfo.setOrderState(OrderStatus.add);
								break;
							case 1:
								orderInfo.setOrderState(OrderStatus.KEFU);
								break;
							case 2:
								orderInfo.setOrderState(OrderStatus.YAOSHI);
								break;
						}
					} else {
						orderInfo.setIsPaid("P");
					}


					//实际支付金额为预存账户支付的金额加上第三方或银行支付的金额
					orderInfo.setPaidFee(paymentLog.getPaidFee().add(orderInfo.getPaidFee()));
					orderService.doPaySuccess(orderInfo);

					if(!isPaidFlag){
						//订单支付方式修改，多支付方式记录到order_payment_type_list中
						//预存账户支付可以添加多次，其他支付方式相同backno只可以添加一次
						OrderPaymentTypeList paymentTypeList = new OrderPaymentTypeList();
						paymentTypeList.setOrderId(orderPaymentLog.getBusinessId()+"");
						paymentTypeList.setPaymentTypeId(Long.parseLong(paymentLog.getPaymentTypeId().toString()));
						paymentTypeList.setPaymentNo(paymentLog.getBackNo());
						paymentTypeList.setPaidTime(currentTime);
						paymentTypeList.setPaidFee(paymentLog.getPaidFee());
						paymentTypeList.setAddTime(currentTime);
						paymentTypeList.setAddUserId(Integer.parseInt(orderPaymentLog.getMemberId().toString()));
						paymentTypeList.setIsDelete("N");

						List<OrderPaymentTypeList> list = myPayService.getOrderPaymentTypeList(paymentTypeList);
						if(null != list && list.size()>0){
							logger.error("=======PaymentTypeId："+paymentLog.getPaymentTypeId().toString()+" adding several times ");
						}else{
							myPayService.saveOrderPaymentTypeList(paymentTypeList);
						}

						//添加订单日志
						OrderLog orderLog = new OrderLog();
						orderLog.setOrderId(orderPaymentLog.getBusinessId()+"");
						//处方订单标识(0-普通订单, 1-单轨处方药订单, 2-双轨处方药订单) 则order_status 做想应修改
						switch (orderInfo.getHaveCfy()){
							case 0:
								orderLog.setOrderStateId(OrderStatus.add);
								if (orderPaymentLog.getPaymentFee().compareTo(paymentLog.getPaidFee()) == 0) {
									orderLog.setLogContent("成功支付金额:￥"+paymentLog.getPaidFee());
									result = "success";
								} else {
									orderLog.setLogContent("部分支付成功,支付金额:￥"+paymentLog.getPaidFee());
									result = "fail";
								}
								break;
							case 1:
								orderLog.setOrderStateId(OrderStatus.KEFU);
								if (orderPaymentLog.getPaymentFee().compareTo(paymentLog.getPaidFee()) == 0) {
									orderLog.setLogContent("成功支付金额:￥"+paymentLog.getPaidFee() + ",待客服审核");
									result = "success";
								} else {
									orderLog.setLogContent("部分支付成功,支付金额:￥"+paymentLog.getPaidFee() + ",待客服审核");
									result = "fail";
								}
								break;
							case 2:
								orderLog.setOrderStateId(OrderStatus.YAOSHI);
								if (orderPaymentLog.getPaymentFee().compareTo(paymentLog.getPaidFee()) == 0) {
									orderLog.setLogContent("成功支付金额:￥"+paymentLog.getPaidFee() + ",待药师审核");
									result = "success";
								} else {
									orderLog.setLogContent("部分支付成功,支付金额:￥"+paymentLog.getPaidFee() +",待药师审核");
									result = "fail";
								}
								break;
						}
						orderLog.setLogTime(currentTime);
						orderLog.setAddTime(currentTime);
						orderLog.setAddUserId(Integer.parseInt(orderPaymentLog.getMemberId().toString()));
						orderLog.setLogUserId(orderLog.getAddUserId());
						orderLog.setOrderLogType("public");
						orderService.saveOrderlog(orderLog);
					}

				}

			}

			myPayService.updateOrderPaymentLog(orderPaymentLog);

			return result;
		}


		/**
		 * 订单退款
		 */
		if (orderPaymentLog.getBusinessType().equals(OrderBusinessType.REFUND)) {
			if(paymentLog.getRefundState().equalsIgnoreCase(ResponseCodeConstants.SUCCESS)){//退款成功
				if (orderPaymentLog.getPaymentFee().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() == paymentLog.getPaidFee().setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()) {
					orderPaymentLog.setBackNotes("退款成功！");
					orderPaymentLog.setPaidState("Y");
					result = "success";
				} else {
					orderPaymentLog.setBackNotes("退款成功,但是与应退款金额不一致！");
					orderPaymentLog.setPaidState("P");
					result = "fail";
				}
			} else {//退款失败
				orderPaymentLog.setBackNotes("退款失败！");
				orderPaymentLog.setPaidState("N");
				result = "success";
			}
			myPayService.updateOrderPaymentLog(orderPaymentLog);
			return result;
		}

		return result;
	}

	/**
	 * 支付结果非成功状态修改订单日志，记录支付结果
	 * @param paymentLog
	 * @return
	 */
	public void doSaveErrResultByLog(OrderPaymentLog paymentLog) {
		OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
		orderPaymentLog.setPaymentNo(paymentLog.getPaymentNo());
		orderPaymentLog.setPaymentTypeId(paymentLog.getPaymentTypeId());
		orderPaymentLog = myPayService.findOrderPaymentLog(paymentLog,false);
		if (orderPaymentLog != null) {
			String currentTime = DateUtils.longToDateAll(System
					.currentTimeMillis());
			orderPaymentLog.setBackTime(currentTime);// 返回时间
			orderPaymentLog.setEditTime(currentTime);// 修改时间
			orderPaymentLog.setBackNotes("支付不成功");
			myPayService.updateOrderPaymentLog(orderPaymentLog);
		}
	}

	/**
	 * 拼接参数 参数顺序：参数数组里每一个key从a到z的顺序排序，
	 * 若遇到相同首字母，则看第二个字母，以此类推。排序完成之后，再把所有数组值以“|”字符连接起来
	 * 
	 * @param params map对象
	 * @return 排序后以“|”连接的字符串
	 */
	public static String getContent(Map params) {
		// key从a到z的顺序排序
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);

		String prestr = "";
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value =  params.get(key)+"";
			// 空值参数不拼接
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "|" + key + "=" + value;
			}
		}
		return prestr;
	}

	public static String getContentByParameterMap(Map params) {
		// key从a到z的顺序排序
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);

		String prestr = "";
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = ((String[]) params.get(key))[0];
			// 空值参数不拼接
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "|" + key + "=" + value;
			}
		}
		return prestr;
	}

	/**
	 * 记录向银行等支付平台发送的请求信息
	 * 
	 * @param paymentLogId
	 *            支付日志ID
	 * @param messageType
	 *            信息类型 request向银行发送请求，response银行响应信息
	 * @param responseType
	 *            return银行前台响应，notify银行后台响应
	 * @param message
	 *            请求或响应信息内容
	 * @param memberId
	 *            会员ID
	 * @param isPaid
	 *            是否支付成功：Y（成功）、 N（未支付成功）
	 */
	public void paymentMessageLog(Long paymentLogId, String messageType,
			String responseType, String message, Long memberId, String isPaid) {
		//如果订单已支付成功，后台通知不再记录请求信息
		if(isPaid.equals("N") || (isPaid.equals("Y") && responseType.equals("return"))){
			String curTime = DateUtils.longToDateAll(System.currentTimeMillis());
			OrderPaymentMessageLog orderPaymentMessageLog = new OrderPaymentMessageLog();
			orderPaymentMessageLog.setPaymentLogId(paymentLogId);
			orderPaymentMessageLog.setMessageType(messageType);
			orderPaymentMessageLog.setResponseType(responseType);
			orderPaymentMessageLog.setMessage(message);
			orderPaymentMessageLog.setIsDelete("N");
			orderPaymentMessageLog.setAddTime(curTime);
			orderPaymentMessageLog.setAddUserId(Integer.parseInt(memberId
					.toString()));
			
			myPayService.saveOrderPaymentMessageLog(orderPaymentMessageLog);
		}
	}

	public void paymentMessageLog(OrderPaymentLog orderPaymentLog, String messageType,
								  String responseType, String message) {
		String isPaid = "N";
		if(null != orderPaymentLog && null != orderPaymentLog.getPaidState()){
			isPaid = orderPaymentLog.getPaidState();
		}
		//如果订单已支付成功，后台通知不再记录请求信息
		if(isPaid.equals("N") || (isPaid.equals("Y") && responseType.equals("return"))){
			String curTime = DateUtils.longToDateAll(System.currentTimeMillis());
			OrderPaymentMessageLog orderPaymentMessageLog = new OrderPaymentMessageLog();
			orderPaymentMessageLog.setPaymentLogId(orderPaymentLog.getPaymentLogId());
			orderPaymentMessageLog.setMessageType(messageType);
			orderPaymentMessageLog.setResponseType(responseType);
			orderPaymentMessageLog.setMessage(message);
			orderPaymentMessageLog.setIsDelete("N");
			orderPaymentMessageLog.setAddTime(curTime);
			orderPaymentMessageLog.setAddUserId(Integer.parseInt(orderPaymentLog.getMemberId().toString()));

			myPayService.saveOrderPaymentMessageLog(orderPaymentMessageLog);
		}
	}

	/**
	 * 返回处理结果至第三方服务器。
	 * @param msg: Success or fail。
	 * @throws IOException 
	 */
	public void responseResult(HttpServletResponse response,String msg) throws IOException {
		String strHtml = msg;
		PrintWriter out = response.getWriter();
		out.println(strHtml);
		out.flush();
		out.close(); 
	}

	/**
	 * 获取订单超时时间(分钟)
	 * @param commitTime 订单提交时间
	 * @return
	 * @throws Exception
	 */
	public String getCancelMinutes(String commitTime) throws Exception {
		if(null == commitTime){
		   return String.valueOf(getPaySysCode("order_effective_time"));
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
		//订单提交时间+12小时为取消时间
		Calendar cancel = Calendar.getInstance();
		cancel.setTime(format.parse(commitTime));
		cancel.add(Calendar.MINUTE,Integer.parseInt(getPaySysCode("order_effective_time")));
		//返回取消时间减去当前时间
		Calendar now = Calendar.getInstance();
		long minute=(cancel.getTimeInMillis()-now.getTimeInMillis())/(60*1000);
		
		//目前固定写死，保证都能支付 
		return String.valueOf(720);
	}
	
	/**
     * 生成一个定长的纯0字符串
     * 
     * @param length
     *            字符串长度
     * @return 纯0字符串
     */
    private static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够后面补0
     * 
     * @param str
     *            初始字符串
     * @param fixdlenth
     *            字符串长度
     * @return 定长的字符串
     */
    protected String toFixdLengthString(String str, int fixdlenth) {
        StringBuffer sb = new StringBuffer(); 
        if (fixdlenth - str.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - str.length()));
        } else {
            throw new RuntimeException("将字符串" + str + "转化为长度为" + fixdlenth + "的字符串发生异常！");
        }
        str += sb;
        return str;
    }

	public String getPaySysCode(String key) throws Exception{
		SysCode code = new SysCode();
		code.setCodeTypeNo("pay");
		code.setCodeNo(key);
		SysCode returnCode = systemService.getCode(code);
		if (null == returnCode) {
			throw new Exception("未获取到支付参数"+key);
		}
		return  returnCode.getCodeValue();
	}

	/**
	 * 调用支付查询接口，修改支付查询状态
	 * @param orderPaymentLog 支付信息
	 */
//	public void paymentQueryAndUpdate(OrderPaymentLog orderPaymentLog){
//		try {
//			PaymentResult paymentResult = queryByOrder(orderPaymentLog);
//			if (orderPaymentLog.getBackState().equals(paymentResult.getTradeStatus())) {
//				//比较金额
//				orderPaymentLog.setQueryState("Y");
//				orderPaymentLog.setEditTime(DateUtils.longToDateAll(System
//						.currentTimeMillis()));// 修改时间
//				if(orderPaymentLog.getPaidFee().compareTo(new BigDecimal(paymentResult.getTotalFee()))==0) {
//					orderPaymentLog.setPaidState("Y");
//				}
//				if(orderPaymentLog.getPaidFee().compareTo(new BigDecimal(paymentResult.getTotalFee()))>0) {
//					orderPaymentLog.setPaidState("P");
//				}
//				myPayService.updateOrderPaymentLog(orderPaymentLog);
//			}
//		} catch (Exception e) {
//			logger.error("台通知处理完成以后，支付状态查询出错！ 支付流水号：" + orderPaymentLog.getPaymentNo());
//			e.printStackTrace();
//		}
//	}

}
