package cn.com.dubbo.base.action;

import cn.com.dubbo.service.MyPayService;
import cn.com.dubbo.service.OrderService;
import cn.com.dubbo.service.SystemService;
import cn.com.dubbo.service.payment.constant.Constants;
import com.jiuyao.ec.common.type.OrderBusinessType;
import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.OrderPaymentMessageLog;
import cn.com.dubbo.model.OrderInfo;
import cn.com.jiuyao.pay.common.util.DateUtil;
import cn.com.jiuyao.pay.common.util.DateUtils;
import cn.com.jiuyao.pay.common.util.Md5Encrypt;
import cn.com.jiuyao.pay.common.util.PaymentUtil;

import cn.com.jiuyao.pay.common.constant.ResponseCodeConstants;
import com.jiuyao.ec.common.model.SysCode;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PayBaseAction extends BaseAction {
	Logger logger = Logger.getLogger(PayBaseAction.class);
	
	@Autowired
	protected MyPayService myPayService; 
	@Autowired
	protected SystemService systemService;
	@Autowired
	public OrderService orderService;

	/**
	 * 正则表达式校验方法
	 * 
	 * @param regex
	 * @param value
	 * @return boolean true 校验通过
	 */
	protected boolean matche(String regex, String value){
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(value.toString().trim());
		return matcher.matches();
	}

	/**
	 * 数据验签
	 * 
	 * @param request
	 * @param response
	 * @param message
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public Boolean verifySign(HttpServletRequest request,HttpServletResponse response,
			String message) throws Exception { 
		boolean flag = true;
		//从sys_code中获取paykey
		SysCode code = new SysCode();
		code.setCodeTypeNo("pay");
		code.setCodeNo("pay_key");
		SysCode returnCode = systemService.getCode(code);
		if (null == returnCode) {
			String error = "=============syscode error : pay_key not found!";
			logger.error(error);
			throw new Exception(error);
		}  
		String payKey = returnCode.getCodeValue();
		//验签
		System.out.printf(message+payKey+"******");
		String mysign = Md5Encrypt.md5(message+payKey);
		if (!mysign.equals(request.getParameter("sign"))) {
			logger.error("====================sign error");
			flag = false;
		}
		return flag; 
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
	 * 校验订单有效性
	 * 
	 * @param request
	 * @author suqun
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public String verifyOrder(HttpServletRequest request,
			HttpServletResponse response,OrderInfo orderInfo) throws ServletException, IOException {
		String jspUrl = "";
		// 判断支付的是否自己的订单
		if (null == orderInfo
				|| !orderInfo.getMemberId().toString().equals(
						request.getParameter("memberId"))) {
			logger.error("-----------------订单有效性校验:支付的不是自己的订单"+"orderId:"+request.getParameter("orderId"));
			request.setAttribute("paidSuccess", "对不起，支付初始化失败，请重新支付");
			request.setAttribute("orderId", request.getParameter("orderId"));
			request.setAttribute("payFlag", "failure");
			jspUrl = "jsp/myec/pay_done.jsp"; 
			return jspUrl;
		}
		// 判断是否已支付
		if ("Y".equals(orderInfo.getIsPaid())) {
			OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
			orderPaymentLog.setBusinessId(Long.parseLong(orderInfo.getOrderNo()));
			orderPaymentLog = myPayService.findOrderMaxTime(orderPaymentLog);
			request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo());
			request.setAttribute("payFlag", "success");
			jspUrl = "jsp/myec/pay_done.jsp"; 
			return jspUrl;
		}
		// 订单是否已经取消
		if (request.getParameter("businessType")
				.equals(OrderBusinessType.ORDER)) {
			if (orderInfo.getCodeNo().equals("cancel")) {
				logger.error("-----------------订单有效性校验:订单已取消"+"orderId:"+request.getParameter("orderId"));
				request.setAttribute("paidSuccess", "对不起，当前订单已经取消，请重新下单");
				request.setAttribute("orderId", orderInfo.getOrderNo());
				request.setAttribute("payFlag", "failure");
				jspUrl = "jsp/myec/pay_done.jsp";
				return jspUrl;
			}
		} 
		return jspUrl;
	}
	
	/**
	 * 校验订单有效性
	 * 
	 * @param orderInfo
	 * @author suqun
	 */
	public boolean verifyOrder(OrderInfo orderInfo, String memberId,
			String businessType) {
		logger.debug("====================订单有效性校验开始");
		// 判断支付的是否自己的订单
		if (null == orderInfo
				|| !orderInfo.getMemberId().toString().equals(memberId)) {
			logger.error("-----------------订单有效性校验:支付的不是自己的订单,订单号："+orderInfo.getOrderNo());
			return false;
		}
		// 判断是否已支付
		if (orderInfo.getIsPaid().equals("Y")) {
//			logger.error("-----------------订单有效性校验:订单已支付,订单号："+orderInfo.getOrderId());
			return false;
		}
		// 订单是否已经取消
		if (businessType.equals(OrderBusinessType.ORDER)) {
			if (orderInfo.getCodeNo().equals("cancel")) {
				logger.error("-----------------订单有效性校验:订单已取消,订单号："+orderInfo.getOrderNo());
				return false;
			}
		}
		logger.debug("====================订单有效性校验完成 ");
		return true;
	}

	/**
	 * 校验订单有效性
	 *
	 * @param orderInfo
	 * @author suqun
	 */
	public Map<String, String> verifyOrderMap(OrderInfo orderInfo, String memberId,
							   String businessType) {
		Map<String, String> map = new HashMap();
		// 判断支付的是否自己的订单
		if (null == orderInfo
				|| !orderInfo.getMemberId().toString().equals(memberId)) {
			logger.error("-----------------订单有效性校验:支付的不是自己的订单,订单号："+orderInfo.getOrderNo());
			map.put("status", ResponseCodeConstants.ORDER_MEMBER_UNMATCH);
			map.put("msg", "订单用户与当前支付用户不匹配");
			return map;
		}
		// 判断是否已支付
		if (orderInfo.getIsPaid().equals("Y")) {
//			logger.error("-----------------订单有效性校验:订单已支付,订单号："+orderInfo.getOrderId());
			map.put("status", ResponseCodeConstants.ORDER_IS_PAID);
			map.put("msg", "订单已支付");
			return map;
		}
		// 订单是否已经取消
		if (businessType.equals(OrderBusinessType.ORDER) && orderInfo.getCodeNo().equals("cancel")) {
			logger.error("-----------------订单有效性校验:订单已取消,订单号："+orderInfo.getOrderNo());
			map.put("status", ResponseCodeConstants.ORDER_IS_CANCELED);
			map.put("msg", "订单已取消");
			return map;
		}
		map.put("status", ResponseCodeConstants.SUCCESS);
		return map;
	}
	
	/**
	 * 根据支付方式获取促销规则并计算订单支付金额
	 * 
	 * @param orderPaymentLog 支付流水信息
	 * @return
	 */
	public BigDecimal paymentPormote(OrderPaymentLog orderPaymentLog) {
		BigDecimal orderPayFee = orderPaymentLog.getPaymentFee();
//    	//根据支付方式获取促销规则
//		EcPromoteRule ecPromoteRule = new EcPromoteRule();
//		ecPromoteRule.setDiscountType(ecPaymentType.getPaymentTypeId().toString());
//		ecPromoteRule.setOrderDiscountType("payment");
//		List<EcPromoteRule> ruleList = myPayService.getOrderPromoteRuleByDTO(ecPromoteRule);
//		OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
//		orderPaymentLog.setRuleList(ruleList);
//		orderPaymentLog.setPaymentFee(orderInfo.getOrderPayFee());
//		BigDecimal orderPayFee= orderInfo.getOrderPayFee();
//		if (orderPayFee.compareTo(new BigDecimal("0.00")) < 0) {
//			orderPayFee = BigDecimal.ZERO;
//		}
//		//策略模式计算订单费用
//		if(null != ruleList && ruleList.size()>0){
//			String discountPlan = ruleList.get(0).getDiscountPlan();
//			Strategy strategy = Factory.createPromoteStrategy(discountPlan);
//			Context context = new Context(strategy);
//			orderPayFee = context.ContextInterface(orderPaymentLog);
//		}
		//调用商品促销接口获取支付方式优惠后的订单支付金额
//		try {
//			orderPayFee = orderService.promoteByPayment(orderPaymentLog);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return orderPayFee;
//		}
		return orderPayFee;
    }
	
	/**
	 * 添加订单支付日志
	 * 
	 * @param request
	 * @param response
	 * @param ecPaymentType
	 */
	public OrderPaymentLog doSaveOrderPaymentLog(HttpServletRequest request,
			HttpServletResponse response,EcPaymentType ecPaymentType,String businessType,String memberId){
		if(null == request.getAttribute("paymentFee")){
			request.setAttribute("paymentFee", request.getParameter("paymentFee").toString());
		} 
		 
		logger.debug("====================添加订单支付日志");
		String curTime = DateUtils.longToDateAll(System.currentTimeMillis());
		int m = Integer.parseInt(memberId);

		OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
		orderPaymentLog.setBusinessType(businessType);
		orderPaymentLog.setBusinessId(Long.parseLong(request.getParameter("orderId")));
		orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
		orderPaymentLog.setPaidState("N");
		orderPaymentLog.setQueryState("N");
		//根据业务类型，业务ID，支付方式查询数据库是否已经存在支付流水，不存在则添加，存在则更新支付时间(万证通与手机银联支付,赔付宝，万里通无论是否存在都新增支付流水)
//		OrderPaymentLog opl = myPayService.findOrderPaymentLog(orderPaymentLog,false);
//		if(opl==null ||
//				ecPaymentType.getPaymentTypeNo().equals(Constants.WZT)
//				|| ecPaymentType.getPaymentTypeNo().equals(Constants.UNIONPAYMOBILE)
//				|| ecPaymentType.getPaymentTypeNo().equals(Constants.PEIFUBAO)
//				|| ecPaymentType.getPaymentTypeNo().equals(Constants.WANLITONG)){//添加
			//生成支付流水号
//			orderPaymentLog.setPaymentNo(Payment.generatePaymentNo(Long.parseLong(request.getParameter("orderId"))));
			orderPaymentLog.setPaymentNo(PaymentUtil.getPaymentNo(memberId));
			orderPaymentLog.setPaymentFee(new BigDecimal(request.getAttribute("paymentFee").toString()));
			orderPaymentLog.setPaymentTime(curTime);
//			if(null != request.getAttribute("tempPrhase")){
//				orderPaymentLog.setTempPrhase(request.getAttribute("tempPrhase").toString());
//			}
			orderPaymentLog.setIsDelete("N");
			orderPaymentLog.setChannel(request.getParameter("channel"));
			orderPaymentLog.setReturnUrl(request.getParameter("returnUrl"));
			orderPaymentLog.setMemberId(Long.parseLong(memberId));
			orderPaymentLog.setAddTime(curTime);
			orderPaymentLog.setAddUserId(m);
		    myPayService.saveOrderPaymentLog(orderPaymentLog);
//		}else{//修改
//			orderPaymentLog = opl;
//			orderPaymentLog.setPaymentFee(new BigDecimal(request.getAttribute("paymentFee").toString()));
//			orderPaymentLog.setChannel(request.getParameter("channel"));
//			orderPaymentLog.setReturnUrl(request.getParameter("returnUrl"));
//			orderPaymentLog.setPaymentTime(curTime);
//			orderPaymentLog.setEditTime(curTime);
//			orderPaymentLog.setEditUserId(m);
//			myPayService.updateOrderPaymentLog(orderPaymentLog);
//		}
		return orderPaymentLog;
	}

	/**
	 * 添加订单支付日志
	 *
	 * @param orderPaymentLog 支付信息
	 */
	public OrderPaymentLog doSaveOrderPaymentLog(OrderPaymentLog orderPaymentLog){
		String curTime = DateUtils.longToDateAll(System.currentTimeMillis());
		int memberId = Integer.parseInt(orderPaymentLog.getMemberId().toString());
		//根据业务类型，业务ID，支付方式查询数据库是否已经存在支付流水，不存在则添加，存在则更新支付时间(万证通与手机银联支付,赔付宝，万里通无论是否存在都新增支付流水)
		OrderPaymentLog opl = myPayService.findOrderPaymentLog(orderPaymentLog,false);
		if(opl==null 
//				|| ecPaymentType.getPaymentTypeNo().equals(Constants.WZT)
//				|| ecPaymentType.getPaymentTypeNo().equals(Constants.UNIONPAYMOBILE)
//				|| ecPaymentType.getPaymentTypeNo().equals(Constants.PEIFUBAO)
//				|| ecPaymentType.getPaymentTypeNo().equals(Constants.WANLITONG)
				){//添加
		//生成支付流水号
//		orderPaymentLog.setPaymentNo(PaymentUtil.getPaymentNo(orderPaymentLog.getMemberId().toString()));
		orderPaymentLog.setPaymentNo(orderPaymentLog.getBusinessId()+"");
		orderPaymentLog.setPaymentTime(curTime);
		orderPaymentLog.setAddTime(curTime);
		orderPaymentLog.setAddUserId(memberId);
		orderPaymentLog.setIsDelete("N");
		orderPaymentLog.setPaidState("N");
		orderPaymentLog.setQueryState("N");
		orderPaymentLog.setReqTxnTime(DateUtil.date2String(new Date(), "yyyyMMddHHmmss"));
		myPayService.saveOrderPaymentLog(orderPaymentLog);
		}else{//修改
			orderPaymentLog = opl;
			orderPaymentLog.setPaymentTime(curTime);
			orderPaymentLog.setEditTime(curTime);
			myPayService.updateOrderPaymentLog(orderPaymentLog);
		}
		return orderPaymentLog;
	}



	/**
	 * 添加订单支付日志  fht 2017-01-22
	 *
	 * @param orderPaymentLog 支付信息
	 */
	public OrderPaymentLog doSaveOrderPaymentLog(OrderPaymentLog orderPaymentLog,String paymentTypeNo){
		String curTime = DateUtils.longToDateAll(System.currentTimeMillis());
		int memberId = Integer.parseInt(orderPaymentLog.getMemberId().toString());
		//根据业务类型，业务ID，支付方式查询数据库是否已经存在支付流水，不存在则添加，存在则更新支付时间(万证通与手机银联支付,赔付宝，万里通无论是否存在都新增支付流水)
		OrderPaymentLog opl = myPayService.findOrderPaymentLog(orderPaymentLog,false);
		if(opl==null
//				|| ecPaymentType.getPaymentTypeNo().equals(Constants.WZT)
//				|| ecPaymentType.getPaymentTypeNo().equals(Constants.UNIONPAYMOBILE)
//				|| ecPaymentType.getPaymentTypeNo().equals(Constants.PEIFUBAO)
//				|| ecPaymentType.getPaymentTypeNo().equals(Constants.WANLITONG)
				){//添加
			//生成支付流水号
//		orderPaymentLog.setPaymentNo(PaymentUtil.getPaymentNo(orderPaymentLog.getMemberId().toString()));
			orderPaymentLog.setPaymentNo(orderPaymentLog.getBusinessId()+"");
			orderPaymentLog.setPaymentTime(curTime);
			orderPaymentLog.setAddTime(curTime);
			orderPaymentLog.setAddUserId(memberId);
			orderPaymentLog.setIsDelete("N");
			orderPaymentLog.setPaidState("N");
			orderPaymentLog.setQueryState("N");
			orderPaymentLog.setReqTxnTime(DateUtil.date2String(new Date(), "yyyyMMddHHmmss"));
			myPayService.saveOrderPaymentLog(orderPaymentLog);
		}else{//修改
			//ehking 或者平安  新增查询记录   fht 2017-1-22
			if(Constants.YHJ.equals(paymentTypeNo) || Constants.PINGAN.equals(paymentTypeNo)){
				Integer sumLog=myPayService.getCountOrderPaymentLog(orderPaymentLog.getBusinessId().toString());
				String paymentNo=String.valueOf(orderPaymentLog.getBusinessId()+sumLog);
				orderPaymentLog.setPaymentNo(paymentNo);
				orderPaymentLog.setPaymentTime(curTime);
				orderPaymentLog.setAddTime(curTime);
				orderPaymentLog.setAddUserId(memberId);
				orderPaymentLog.setIsDelete("N");
				orderPaymentLog.setPaidState("N");
				orderPaymentLog.setQueryState("N");
				orderPaymentLog.setReqTxnTime(DateUtil.date2String(new Date(), "yyyyMMddHHmmss"));
				myPayService.saveOrderPaymentLog(orderPaymentLog);
			}else {
				orderPaymentLog = opl;
				orderPaymentLog.setPaymentTime(curTime);
				orderPaymentLog.setEditTime(curTime);
				myPayService.updateOrderPaymentLog(orderPaymentLog);
			}
		}
		return orderPaymentLog;
	}

	/**
	 * 记录向银行等支付平台发送的请求信息
	 * 
	 * @param paymentLogId 支付日志ID
	 * @param messageType 信息类型 request向银行发送请求，response银行响应信息
	 * @param responseType return银行前台响应，notify银行后台响应
	 * @param message	请求或响应信息内容
	 * @param memberId	会员ID
	 */
	public void paymentMessageLog(Long paymentLogId,String messageType,String responseType,String message,String memberId) {
		logger.debug("====================添加请求信息日志");
		String curTime = DateUtils.longToDateAll(System.currentTimeMillis());
		OrderPaymentMessageLog orderPaymentMessageLog = new OrderPaymentMessageLog();
		orderPaymentMessageLog.setPaymentLogId(paymentLogId);
		orderPaymentMessageLog.setMessageType(messageType);
		orderPaymentMessageLog.setResponseType(responseType);
		if(message.length()>3600){
			message = message.substring(0,3600);
		}
		orderPaymentMessageLog.setMessage(message);
		orderPaymentMessageLog.setIsDelete("N");
		orderPaymentMessageLog.setAddTime(curTime);
		orderPaymentMessageLog.setAddUserId(Integer.parseInt(memberId));

		myPayService.saveOrderPaymentMessageLog(orderPaymentMessageLog);



	}

	/**
	 * 拼接参数 参数顺序：上送的参数数组里的每一个key从a到z的顺序排序，
	 * 若遇到相同首字母，则看第二个字母，以此类推。排序完成之后，再把所有数组值以“&”字符连接起来, 最后降privateKey拼接至尾部
	 * 
	 * @param params
	 * @return
	 */
	public String getContent(Map params) {
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
			// sign不拼接
			if (key.equals("sign")) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "&" + key + "=" + value;
			}
		}

		return prestr;
	}

	/**
	 * 输出json格式响应消息
	 *
	 * @param responseMsg
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected String resutnJsonMessage(String responseMsg) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("responseCode", responseMsg);
		JSONObject promoteJson = JSONObject.fromObject(resultMap);
		return URLEncoder.encode(promoteJson.toString(), "UTF-8");
	}
	
}
