package cn.com.dubbo.service.payment.platform;

import cn.com.dubbo.constant.PinganConfig;
import cn.com.dubbo.model.*;
import cn.com.dubbo.service.payment.constant.Constants;
import cn.com.jiuyao.pay.common.constant.ResponseCodeConstants;
import cn.com.jiuyao.pay.common.util.DateUtils;
import cn.com.jiuyao.pay.common.util.HttpClientUtils;
import cn.com.jiuyao.pay.common.util.MathUtil;
import cn.com.jiuyao.util.payments.pingan.StringEncrypt;
import cn.com.jiuyao.util.payments.pingan.httpClient.*;
import com.alibaba.fastjson.JSON;
import com.jiuyao.ec.common.type.OrderBusinessType;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;


/**平安健康卡
 * Created by jinjin
 */
@Service
public class PingAn extends PinganBase {


	@Resource(name = "pinganConfig")
	private PinganConfig pinganConfig;
	/**
	 * 平安健康卡支付
	 *
	 * @param request request
	 * @param response
	 * @param orderPaymentLog
	 * @return ""
	 */
	@Override
	public String requestMessagePackage(final HttpServletRequest request,
										final HttpServletResponse response,
										final OrderPaymentLog orderPaymentLog) {
		Map<String, String> ecPaymentTypeParames = orderPaymentLog.getEcPaymentTypeParames();
		String message = "";
		try {
			Map<String, String[]> sPara = new HashMap<String, String[]>();
			// 金额
			BigDecimal fee = MathUtil.changeY2F(orderPaymentLog.getPaymentFee());
			//获取支付参数
			sPara = this.requestData(ecPaymentTypeParames, sPara);
			String transType = TransType.CONSUMPTION; //下单支付
			sPara.put("transType", new String[]{transType});
			String transCode = ecPaymentTypeParames.get("transCode");
			sPara.put("transCode", new String[]{transCode});
			String merchantId = ecPaymentTypeParames.get("merchantId");
			sPara.put("merchantId", new String[]{merchantId});
			String backEndUrl = orderPaymentLog.getEcPaymentType().getReturnUrl(); //前台通知
			sPara.put("backEndUrl", new String[]{backEndUrl});
			String frontEndUrl = orderPaymentLog.getEcPaymentType().getNotifyUrl(); //后台通知
			sPara.put("frontEndUrl", new String[]{frontEndUrl});
			String mercOrderNo = orderPaymentLog.getPaymentNo(); //订单号
			sPara.put("mercOrderNo", new String[]{mercOrderNo});
			String orderAmount = fee.toString(); //交易金额
			sPara.put("orderAmount", new String[]{orderAmount});
			String orderCurrency = ecPaymentTypeParames.get("orderCurrency");
			sPara.put("orderCurrency", new String[]{orderCurrency});
			String reserved = getPayTpyeGroup(orderPaymentLog.getBusinessId() + "",fee); //分类汇总
			String merReserved = reserved.replace("sortAmount","SortAmount").replace("sortCode","SortCode");
			sPara.put("merReserved", new String[]{merReserved});
			String merReserved2 = getOrderItemPayTpye(orderPaymentLog.getBusinessId() + ""); //所购买商品的明细
			sPara.put("merReserved2", new String[]{merReserved2});
			String businessScene = ecPaymentTypeParames.get("businessScene");
			sPara.put("businessScene", new String[]{businessScene});
			//签名
			String signature = cn.com.jiuyao.util.payments.pingan.StringUtil.mapArrayToString(sPara);
		    //SHA256加密，第一参数为请求对象的字符串格式，第二个参数为商户的约定密钥，每个商户不同
		    String encryStr = StringEncrypt.encryptRequest(signature, ecPaymentTypeParames.get("signKey"));
		    sPara.put("signature", new String[]{encryStr});

			//封装请求参数
			request.setAttribute("postUrl", ecPaymentTypeParames.get("postUrl"));
			request.setAttribute("charset",  ecPaymentTypeParames.get("char_set"));
			request.setAttribute("version", ecPaymentTypeParames.get("version_no"));
			request.setAttribute("signMethod", ecPaymentTypeParames.get("signMethod"));
			request.setAttribute("transType", transType);
			request.setAttribute("transCode", transCode);
			request.setAttribute("merchantId", merchantId);
			request.setAttribute("backEndUrl", backEndUrl);
			request.setAttribute("frontEndUrl", frontEndUrl);
			request.setAttribute("mercOrderNo", mercOrderNo);
			request.setAttribute("orderCurrency", orderCurrency);
			request.setAttribute("orderAmount", orderAmount);
			request.setAttribute("merReserved", merReserved.replace("\"", "&quot;"));
			request.setAttribute("merReserved2", merReserved2.replace("\"", "&quot;"));
			request.setAttribute("signature", encryStr);
			request.setAttribute("businessScene", businessScene);
			request.setAttribute("postUrl", ecPaymentTypeParames.get("postUrl"));

			if (!TransType.CHANNEL.equals(orderPaymentLog.getChannel())){
				//返回给app端
				JSONObject jb = new JSONObject();
				JSONObject data = new JSONObject();
				jb.element("status", 0);
				jb.element("msg", "OK");
				data.element("orderId", request.getParameter("orderId"));
				data.element("paymentTypeNo", request.getParameter("paymentTypeNo"));
				data.element("channel", request.getParameter("channel"));
				data.element("businessType", request.getParameter("businessType"));
				data.element("postUrl",ecPaymentTypeParames.get("postUrl"));
				data.element("wapReturnUrl",ecPaymentTypeParames.get("wapReturnUrl"));
				String res = cn.com.jiuyao.util.payments.pingan.StringUtil.mapArrayToString(sPara);
				data.element("orderInfo", res);
				jb.element("data", data);

				StringBuilder str = new StringBuilder();
				String callback = request.getParameter("callback");
				if (callback != null && !"".equals(callback)) {
					str.append(callback);
				}
				str.append(jb.toString());
				response.resetBuffer();
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(str.toString());

				response.getWriter().flush();
				response.getWriter().close();
				response.flushBuffer();
			}



			message = this.pinganMToString(sPara);
			paymentMessageLog(orderPaymentLog, "request", "", message); //添加请求参数日志
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("pingan pay exception" + e);
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 封装merReserved，订单明细分类汇总金额
	 *
	 * @param orderNo 订单编号
	 *
	 * @return [{“SortCode”:”D01”,”SortAmount”:”100.01”},{“SortCode”:”D05”,”SortAmount”:”10.44”}]
	 */
	private String getPayTpyeGroup(String orderNo,BigDecimal payFee) {
		//保留域的金额必须和支付的金额一样，负责无法换出健康卡
		List<PayType> list = new ArrayList<PayType>();
		PayType shipment = this.payType(orderNo); //获取运费
		if (shipment != null){
			list.add(shipment);
			payFee = payFee.subtract(MathUtil.changeY2F(shipment.getSortAmount()));
		}
		PayType payType = new PayType();
		payType.setSortAmount(MathUtil.changeF2Y(payFee));
		payType.setSortCode(TransType.YAOPIN);
		list.add(payType);
		return JSON.toJSONString(list);
	}


	/**
	 * 封装merReserved1，订单明细分类
	 * @param orderNo 订单编号
	 * @return [{ “code”:”D01 D01”,”name”:” 板蓝 根 ”,”price”:”4.994.994.994.99”,”number”:”3”}]
	 *
	 */
	private String getOrderItemPayTpye(String orderNo) {
		List<PayOrderItemType> list = orderService.getOrderItemDetailType(orderNo, Constants.PINGAN);
		for (PayOrderItemType payOrderItemType : list) {
			payOrderItemType.setName(payOrderItemType.getName().replaceAll("\r|\n", ""));
			payOrderItemType.setCode(TransType.YAOPIN);
		}
		PayOrderItemType payOrderItemType = payOrderItemType(orderNo);
		if (payOrderItemType != null){
			list.add(payOrderItemType);
		}
		return JSON.toJSONString(list);
	}

	/**
	 * 根据订单号查询出来是否包含运费
	 * @param orderNo
	 * @return
	 */
	private PayType payType(String orderNo){
		PayType payType = null;
		try {
			OrderInfo orderInfo = orderService.findOrderById(orderNo);
			BigDecimal bigDecimal = orderInfo.getDeliveryFee(); //实际运费 单位：元 保留两位小数
			if (bigDecimal.compareTo(BigDecimal.ZERO) == 1){ //如果运费大于0
				payType =new PayType();
				payType.setSortAmount(bigDecimal);
				payType.setSortCode(TransType.QT);
			}
		} catch (Exception e) {
			logger.error("exception :" + e);
			e.printStackTrace();
		}
		return payType;
	}


	/**
	 * 根据订单号查询出来是否包含运费
	 * @param orderNo
	 * @return
	 */
	private PayOrderItemType payOrderItemType(String orderNo){
		PayOrderItemType payOrderItemType = null;
		try {
			OrderInfo orderInfo = orderService.findOrderById(orderNo);
			BigDecimal bigDecimal = orderInfo.getDeliveryFee(); //实际运费 单位：元 保留两位小数
			if (bigDecimal.compareTo(BigDecimal.ZERO) == 1){ //如果运费大于0
				payOrderItemType =new PayOrderItemType();
				payOrderItemType.setPrice(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP ));
				payOrderItemType.setNumber(TransType.NUMBER);
				payOrderItemType.setName(TransType.YUNFEI);
				payOrderItemType.setCode(TransType.QT);
			}
		} catch (Exception e) {
			logger.error("exception :" + e);
			e.printStackTrace();
		}
		return payOrderItemType;
	}



	/**
	 * 异步
	 * @param request
	 * @param response
	 * @param paymentTypeNo
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String returnMessageHandle(final HttpServletRequest request,
									  final HttpServletResponse response, String paymentTypeNo) throws Exception {
		try {
			Map<String, String[]> responsData = request.getParameterMap();
			//如果传了回调地址，平安无论是否支付都会回调该地址，支付成功的话是返回22个参数，不支付返回19个参数
			if (responsData == null || responsData.size() < 22) {
				return null;
			}
			String transMsg = cn.com.jiuyao.util.payments.pingan.StringUtil.mapArrayToString(responsData);
			String paymentNo = request.getParameter("mercOrderNo"); //商户订单号
			OrderPaymentLog orderPaymentLog = getOrderPaymentLog(paymentTypeNo, paymentNo);


			String signature = StringEncrypt.encryptRequest(transMsg, orderPaymentLog.getEcPaymentTypeParames().get("signKey"));    //加签
			String returnSignatrue = request.getParameter("signature");
			String message = this.pinganMToString(responsData);
			paymentMessageLog(orderPaymentLog, "response", "return", message);
			if (returnSignatrue.equals(signature)) {
				logger.info("验签通过");
				this.httpPinganURL(orderPaymentLog); //远程调用接口
				orderPaymentLog.setBackNo(request.getParameter("orderTraceNo")); //平安付交易号
				orderPaymentLog.setBackState("0000".equals(request.getParameter("resCode")) ? "SUCCESS" : "failed"); //交易代码
				doSaveResultByLog(orderPaymentLog);
			} else {
				logger.info("验签失败,商户号：{" + request.getParameter("mercOrderNo") + "}");
				doSaveErrResultByLog(orderPaymentLog);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 同步通知
	 * @param request
	 * @param response
	 * @param paymentTypeNo
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String notifyMessageHandle(HttpServletRequest request,
			HttpServletResponse response, String paymentTypeNo)
			throws Exception {
		Map<String, String[]> responsData = request.getParameterMap();
		//注意：pingan无论是否支付成功 都会回调该接口
		if (responsData == null) {
			return null;
		}
		String paymentNo = request.getParameter("mercOrderNo");
		//注意此处有乱码
		String transMsg = cn.com.jiuyao.util.payments.pingan.StringUtil.mapArrayToString(responsData);
		String resData = new String(transMsg.getBytes("ISO-8859-1"), "UTF-8");
		//判断返回的值是否是支付成功
		//通知类型notifyType 此类型枚举类型如下：
		//00 支付成功
		//01 订单创建成功
		//03 订单创建失败
		if (!transMsg.contains("notifyType=00")) {
			logger.info("pingan  pay failed,paymentNo:" + paymentNo);
            return  null;
		}
        OrderPaymentLog orderPaymentLog = getOrderPaymentLog(paymentTypeNo, paymentNo);
		String signature = StringEncrypt.encryptRequest(resData, orderPaymentLog.getEcPaymentTypeParames().get("signKey"));
		String returnSignatrue = request.getParameter("signature");
		if (returnSignatrue.equals(signature)) {
			logger.info("验签通过");
			this.httpPinganURL(orderPaymentLog);
			orderPaymentLog.setBackNo(request.getParameter("orderTraceNo")); //平安付交易号
			orderPaymentLog.setBackState("0000".equals(request.getParameter("resCode")) ? "SUCCESS" : "failed"); //交易代码
			doSaveResultByLog(orderPaymentLog);
			request.setAttribute("payFlag", "success");
			request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo());
			request.setAttribute("returnUrl",  orderPaymentLog.getReturnUrl());
			request.setAttribute("paidSuccess", "订单支付成功");
			request.setAttribute("orderId", orderPaymentLog.getBusinessId());
			request.setAttribute("total_fee", orderPaymentLog.getPaymentFee());
			request.setAttribute("deliveryReceiver", "");
			request.setAttribute("deliveryAddressFull", "");
			request.getRequestDispatcher(PAY_DONE_JSP_PATH).forward(request, response);
		} else {
			logger.info("pingan tongbu failed,error:sign not passed,paymentNo:{" + responsData.get("mercOrderNo") + "}");
			doSaveErrResultByLog(orderPaymentLog);
			request.setAttribute("paidSuccess", "抱歉,您的订单支付失败!");
			request.setAttribute("payFlag", "failure");
			request.setAttribute("paymentNo",
					orderPaymentLog.getPaymentNo());
			request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
			request.getRequestDispatcher(PAY_DONE_JSP_PATH).forward(
					request, response);
		}
		return null;
	}


	/**
	 * 调用平安的打印和成功平安付接口
	 * @param orderPaymentLog
	 * @throws Exception
	 */
	public void httpPinganURL(OrderPaymentLog orderPaymentLog) throws Exception {
		try {
			Map<String,String> map = new HashMap<String, String>();
			map.put("memberId",orderPaymentLog.getMemberId().toString());
			map.put("memberSource", Constants.PINGAN);
			String resJSON = HttpClientUtils.doPost(pinganConfig.getPinganUrl(), map);
			logger.info("pingan user responseData:" + resJSON + " , orderNo:" + orderPaymentLog.getBusinessId().toString());
			Map<String,String> mapPrint = new HashMap<String, String>();
			mapPrint.put("orderNo",orderPaymentLog.getBusinessId().toString());
			String printJSON = HttpClientUtils.doPost(pinganConfig.getPinganPrintUrl(),mapPrint);
			logger.info("pingan user responseData:" + printJSON + " , orderNo:" + orderPaymentLog.getBusinessId().toString());
		} catch (Exception e) {
			logger.error("调用接口失败,errorMessage:" + e);
			e.printStackTrace();
		}
	}


	/**
	 * 同步异步 公用方法，
	 * @param
	 * @return
	 */
	private   OrderPaymentLog getOrderPaymentLog(String paymentTypeNo, String paymentNo) {
		EcPaymentType ecPaymentType = myPayService.findPaymentInfoByNo(paymentTypeNo);
		Map<String, String> ecMap = myPayService.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
		OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
		orderPaymentLog.setPaymentNo(paymentNo); //商户订单号
		orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
		orderPaymentLog.setBusinessType(OrderBusinessType.ORDER);
		orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog, false);
		orderPaymentLog.setEcPaymentTypeParames(ecMap);
		return orderPaymentLog;
	}

	/**
	 * 订单查询请求
	 * @param orderPaymentLog
	 * @return PaymentResult
	 */
	@Override
	public Map<String, String> refundQuery(OrderPaymentLog orderPaymentLog) throws Exception {
        Map<String, String> queryMap = new ConcurrentHashMap<String, String>();
		Map<String, String> ecMap = orderPaymentLog.getEcPaymentTypeParames();
		Map<String, String[]> requestData = new ConcurrentHashMap<String, String[]>();
		//获取支付参数
		requestData = this.requestData(ecMap, requestData);
		String transType = TransType.QUERY; //信息查询
		requestData.put("transType", new String[]{transType});
		String merchantId = ecMap.get("merchantId");
		requestData.put("merId", new String[]{merchantId});
		String orderTraceNo = orderPaymentLog.getBackNo() + ""; //平安返回的订单号
		requestData.put("orderTraceNo", new String[]{orderTraceNo});
		String mercOrderNo = orderPaymentLog.getPaymentNo() + ""; //订单号
		requestData.put("mercOrderNo", new String[]{mercOrderNo});
		String signature = cn.com.jiuyao.util.payments.pingan.StringUtil.mapArrayToString(requestData);
		String encryStr = StringEncrypt.encryptRequest(signature, ecMap.get("signKey"));
		requestData.put("signature", new String[]{encryStr});
		NameValuePair[] nameValuePair = cn.com.jiuyao.util.payments.pingan.StringUtil.mapArrayToNameValuePair(requestData);
		String result = post(nameValuePair, ecMap.get("postUrl"), orderPaymentLog, requestData);
		queryMap.put("result", result);
		if (StringUtils.isEmpty(result)) {
			logger.info("pingan query result is null，orderNo：{" + orderPaymentLog.getPaymentNo() + "}");
			return  null;
		}
		Map<String, String> resultMap = cn.com.jiuyao.util.payments.pingan.StringUtil.splitStringToMap(result);
		if (StringEncrypt.validateSignature(result, ecMap.get("signKey"), resultMap.get("signature"))) {
			logger.info("pingan query yanqian success");
		} else {
			logger.info("pingan query yanqian failure");
			queryMap.put("result", ResponseCodeConstants.NO_FOUND_CARD); //未查询到
		}
		return queryMap;
	}

	/**
	 * 订单退款请求
	 * @param orderPaymentLog
	 * @return PaymentResult
	 */
	@Override
	public final Map<String, String> refund(OrderPaymentLog orderPaymentLog) throws Exception {
		Map<String, String> refundMap = new ConcurrentHashMap<String, String>();
		Map<String, String> ecMap = orderPaymentLog.getEcPaymentTypeParames();
		Map<String, String[]> requestData = new ConcurrentHashMap<String, String[]>();
		//获取支付参数
		requestData = this.requestData(ecMap, requestData);
		String transType = TransType.REFUND; //退款
		requestData.put("transType", new String[]{transType});
		String merchantId = ecMap.get("merchantId");
		requestData.put("merchantId", new String[]{merchantId});
		String backNo = orderPaymentLog.getBackNo() + "";
		requestData.put("origOrderTraceNo", new String[]{backNo});
		//String paymentNo =orderPaymentLog.getBusinessId().toString()+Math.random();//订单号
		String paymentNo = orderPaymentLog.getBusinessId().toString() + ThreadLocalRandom.current().nextDouble(); //订单号  退款时候订单号不能重复 故采用了订单号+随机数的方式
		requestData.put("mercOrderNo", new String[]{paymentNo});
		BigDecimal orderAmount = MathUtil.changeY2F(orderPaymentLog.getPaymentFee()); //交易金额
		requestData.put("orderAmount", new String[]{orderAmount.toString()});
		String orderCurrency = ecMap.get("orderCurrency");
		requestData.put("orderCurrency", new String[]{orderCurrency});
		//签名
		String signature = cn.com.jiuyao.util.payments.pingan.StringUtil.mapArrayToString(requestData);
		//SHA256加密，第一参数为请求对象的字符串格式，第二个参数为商户的约定密钥，每个商户不同
		String encryStr = StringEncrypt.encryptRequest(signature, ecMap.get("signKey"));
		requestData.put("signature", new String[]{encryStr});
		NameValuePair[] nameValuePair = cn.com.jiuyao.util.payments.pingan.StringUtil.mapArrayToNameValuePair(requestData);
		String result = post(nameValuePair, ecMap.get("postUrl"),orderPaymentLog,requestData);
		refundMap.put("result", result);
         if (StringUtils.isEmpty(result)) {
			 logger.info("退款失败");
			 return  null;
		 }
		com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
		Map<String, String> resultMap = cn.com.jiuyao.util.payments.pingan.StringUtil.splitStringToMap(result);
		if (StringEncrypt.validateSignature(result, ecMap.get("signKey"), resultMap.get("signature")) && "0000".equals(resultMap.get("respCode"))) {
			orderPaymentLog.setRefundState(ResponseCodeConstants.SUCCESS); //退款状态
			orderPaymentLog.setBackNo(resultMap.get("orderTraceNo")); //平安付交易号
			orderPaymentLog.setBackTime(DateUtils.getCurrentDateString());
			//orderPaymentLog.setReturnUrl(ecMap);
			logger.info("pingAn refund sign success");
			doSaveResultByLog(orderPaymentLog);
			jsonObject.put("code","0000");
		} else {
			logger.info("pingan refund sign failure");
			doSaveErrResultByLog(orderPaymentLog);
			jsonObject.put("code","0002");
		}
		refundMap.put("result",jsonObject.toString());
        return refundMap;
	}


	/**
	 * 需要的公共参数
	 * @param ecMap
	 * @param requestData
	 * @return
	 */
	private   Map<String, String[]> requestData(Map<String, String> ecMap, Map<String, String[]> requestData) {
		String version = ecMap.get("version_no"); //版本
		if (StringUtils.isNotEmpty(version)) {
			requestData.put("version", new String[]{version});
		}
		String charset = ecMap.get("char_set"); // 字符集，00-GBK、01-GB2312、02-UTF-8
		if (StringUtils.isNotEmpty(charset)) {
			requestData.put("charset", new String[]{charset});
		}
		String signMethod = ecMap.get("signMethod"); //签名方式,不参与签名
		if (StringUtils.isNotEmpty(signMethod)) {
			requestData.put("signMethod", new String[]{signMethod});
		}
		return requestData;
	}


	/**
	 * post方法
	 * @param nameValuePair
	 * @param url
	 * @return
	 * @throws
	 * @throws IOException
	 */
	private String post(NameValuePair[] nameValuePair, String url, OrderPaymentLog orderPaymentLog, Map<String, String[]> map) throws  IOException {
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		request.setCharset("UTF-8");
		request.setParameters(nameValuePair);
		request.setUrl(url);

		//添加请求日志信息
	    String message = this.pinganMToString(map);
		paymentMessageLog(orderPaymentLog, "request", "", message);
		HttpResponse response = httpProtocolHandler.execute(request, "", "");
		if (response == null) {
			logger.info("pingan response is null");
			return null;
		} else {
			return response.getStringResult();
		}
	}
}
