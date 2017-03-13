package cn.com.dubbo.service.payment.platform;

import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.jiuyao.util.payments.alipayWap.*;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Alipay wap  手机端支付宝
 * 
 * @author qun.su
 * @version 2014年9月23日
 */
@Service
public class AlipayWap extends AlipayBase {
 
	@Override
	public String requestMessagePackage(HttpServletRequest request,
			HttpServletResponse response,  OrderPaymentLog orderPaymentLog) {
		Map<String, String> ecPaymentTypeParames = orderPaymentLog.getEcPaymentTypeParames();
		String message = "";
		try {
			//获取参数
			String _input_charset = ecPaymentTypeParames.get("input_charset");//字符集
			String key = ecPaymentTypeParames.get("private_key"); // 支付宝安全校验码
			String createService = ecPaymentTypeParames.get("createService");//授权接口名称
			String tradeService = ecPaymentTypeParames.get("tradeService");//交易接口名称
			String paygateway = ecPaymentTypeParames.get("paygateway");//接口地址
			String format = ecPaymentTypeParames.get("format");//请求参数格式
			String v = ecPaymentTypeParames.get("v");//接口版本
			String partner = ecPaymentTypeParames.get("partner"); // 支付宝合作伙伴id 
			String req_id = UtilDate.getOrderNum();//请求号 String(32)
			String sec_id  = ecPaymentTypeParames.get("sign_type");//签名方式
			String subject = "9yao-"+request.getParameter("orderId"); // 商品名称
			String out_trade_no = orderPaymentLog.getPaymentNo(); // 商户网站订单流水号
			BigDecimal paymentFee = orderPaymentLog.getPaymentFee().setScale(2,BigDecimal.ROUND_HALF_EVEN);
			String total_fee = paymentFee.toString(); // 订单总价
			String seller_account_name = ecPaymentTypeParames.get("seller_account_name"); // 卖家支付宝账号
			String call_back_url = ecPaymentTypeParames.get("call_back_url"); // 支付成功 跳转页面路径
			String notify_url = ecPaymentTypeParames.get("notify_url"); // 服务器异步通知页面路径
			String out_user = orderPaymentLog.getMemberId().toString();//商城会员ID
			String merchant_url = ecPaymentTypeParames.get("merchant_url"); // 用户付款中途退出返回商户的地址
			String pay_expire = getCancelMinutes(orderPaymentLog.getStartTime());//交易自动关闭时间 单位为分钟
			
			String req_token_data = "<direct_trade_create_req>" + "<subject>" + subject
					+ "</subject>" + "<out_trade_no>" + out_trade_no
					+ "</out_trade_no>" + "<total_fee>" + total_fee
					+ "</total_fee>" + "<seller_account_name>"
					+ seller_account_name + "</seller_account_name>"
					+ "<call_back_url>" + call_back_url + "</call_back_url>"
					+ "<notify_url>" + notify_url + "</notify_url>"
					+ "<out_user>" + out_user + "</out_user>"
					+ "<merchant_url>" + merchant_url + "</merchant_url>"
					+ "<pay_expire>" + pay_expire + "</pay_expire>"
					+ "</direct_trade_create_req>";
			
			//授权接口参数排序后签名
			Map<String, String> sParaTempToken = new HashMap<String, String>();
			sParaTempToken.put("service", createService);
			sParaTempToken.put("req_data", req_token_data);
			sParaTempToken.put("partner", partner);
			sParaTempToken.put("req_id", req_id);
			sParaTempToken.put("sec_id", sec_id);
			sParaTempToken.put("format", format);
			sParaTempToken.put("v", v); 
			sParaTempToken.put("_input_charset",_input_charset);
			
			//建立请求 调用手机网页即时到账授权接口(alipay.wap.trade.create.direct)获得授权令牌
			String sHtmlTextToken = AlipaySubmit.buildRequest(paygateway,"", "",sParaTempToken,key,_input_charset,sec_id);
			//URLDECODE返回的信息
			sHtmlTextToken = URLDecoder.decode(sHtmlTextToken,_input_charset);
			//获取token
			String request_token = AlipaySubmit.getRequestToken(sHtmlTextToken,key,_input_charset);
			
			//业务详细
			String req_data = "<auth_and_execute_req><request_token>"
					+ request_token + "</request_token></auth_and_execute_req>";
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", tradeService);
			sParaTemp.put("partner", partner);
			sParaTemp.put("_input_charset", _input_charset);
			sParaTemp.put("sec_id", sec_id);
			sParaTemp.put("format", format);
			sParaTemp.put("v", v);
			sParaTemp.put("req_data", req_data);
			
			// 建立请求 调用手机网页即时到账交易接口(alipay.wap.auth.authandexecute)
			String signData = AlipayCore.createLinkString(sParaTemp);
			String sign = MD5.sign(signData, key, _input_charset);
			// sParaTemp.put("sign", sign);
			request.setAttribute("service", tradeService);
			request.setAttribute("partner", partner);
			request.setAttribute("_input_charset", _input_charset);
			request.setAttribute("sec_id", sec_id);
			request.setAttribute("format", format);
			request.setAttribute("v", v);
			request.setAttribute("req_data", req_data);
			request.setAttribute("sign", sign);
			request.setAttribute("paygateway", paygateway);
			
			//将请求信息拼接以便记录至数据库
			sParaTempToken.putAll(sParaTemp);
			message = getContent(sParaTempToken);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 同步通知
	 */
	@Override
	public String returnMessageHandle(HttpServletRequest request,
			HttpServletResponse response, String paymentTypeNo){
		try {
			// 取得支付参数
			EcPaymentType ecPaymentType = myPayService.findPaymentInfoByNo(paymentTypeNo);
			Map<String,String> ecPaymentTypeParames = myPayService.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
			String _input_charset = ecPaymentTypeParames.get("input_charset");//字符集
			String key = ecPaymentTypeParames.get("private_key"); // 支付宝安全校验码
			String sec_id  = ecPaymentTypeParames.get("sign_type");//签名方式
			//获取支付宝GET过来反馈信息 设置到新的params中
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			String out_trade_no = request.getParameter("out_trade_no");
			String result = request.getParameter("result");
			
			// 获取订单日志信息和订单信息
			OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
			orderPaymentLog.setPaymentNo(out_trade_no);
			orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
			orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog,
					false);
//			OrderInfo orderInfo = orderService.findOrderById(orderPaymentLog
//					.getBusinessId());
//
//			// 拼接记录order_payment_message_log
//			String isPaid = "N";
//			if (null != orderInfo) {
//				isPaid = orderInfo.getIsPaid();
//			}
//			String message = getContentByParameterMap(request.getParameterMap());
//			paymentMessageLog(orderPaymentLog.getPaymentLogId(), "response",
//					"return", message, orderPaymentLog.getMemberId(), isPaid);
			// 拼接记录order_payment_message_log
			String message = getContentByParameterMap(request.getParameterMap());
			paymentMessageLog(orderPaymentLog, "response","return", message);
			 
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verifyReturn(params,key,_input_charset,sec_id);
			if (!verify_result) {
				request.setAttribute("paidSuccess", "抱歉,您的订单支付失败！签名无效");
				request.setAttribute("payFlag", "failure");
				request.setAttribute("orderId",orderPaymentLog.getBusinessId().toString());
				request.setAttribute("returnUrl", URLDecoder.decode(orderPaymentLog.getReturnUrl(),"utf-8"));
				request.getRequestDispatcher("/jsp/myec/pay_done.jsp").forward(
						request, response);
				return null;
			}
			//验签后判断返回码
			if (result.equals("success")) {
				request.setAttribute("payFlag", "success");
				request.setAttribute("orderId",orderPaymentLog.getBusinessId().toString());
				request.setAttribute("returnUrl", URLDecoder.decode(orderPaymentLog.getReturnUrl(), "utf-8"));
				request.getRequestDispatcher("/jsp/myec/pay_done.jsp").forward(request, response);
				return null;
			} else {
				request.setAttribute("paidSuccess", "抱歉,您的订单支付失败！");
				request.setAttribute("payFlag", "failure");
				request.setAttribute("orderId",orderPaymentLog.getBusinessId().toString());
				request.setAttribute("returnUrl", URLDecoder.decode(orderPaymentLog.getReturnUrl(), "utf-8"));
				request.getRequestDispatcher("/jsp/myec/pay_done.jsp").forward(
						request, response);
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 异步通知
	 */
	@Override
	public String notifyMessageHandle(HttpServletRequest request,
			HttpServletResponse response, String paymentTypeNo)
			throws Exception {
		try {
			// 取得支付参数
			EcPaymentType ecPaymentType = myPayService.findPaymentInfoByNo(paymentTypeNo);
			Map<String,String> ecPaymentTypeParames = myPayService.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
			String _input_charset = ecPaymentTypeParames.get("input_charset");//字符集
			String key = ecPaymentTypeParames.get("private_key"); // 支付宝安全校验码
			String partner = ecPaymentTypeParames.get("partner"); // 支付宝合作伙伴id 
			String sec_id  = ecPaymentTypeParames.get("sign_type");//签名方式
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}

			//取得支付返回信息  XML解析notify_data数据
			Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
			//商户订单号
			String out_trade_no = doc_notify_data.selectSingleNode( "//notify/out_trade_no" ).getText();
			//支付宝交易号
			String trade_no = doc_notify_data.selectSingleNode( "//notify/trade_no" ).getText();
			//交易金额
			String total_fee = doc_notify_data.selectSingleNode( "//notify/total_fee" ).getText();
			//交易状态
			String trade_status = doc_notify_data.selectSingleNode( "//notify/trade_status" ).getText();
			
			// 获取订单日志信息和订单信息
			OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
			orderPaymentLog.setPaymentNo(out_trade_no);
			orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
			orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog,false);
//			OrderInfo orderInfo = orderService.findOrderById(orderPaymentLog.getBusinessId());
//
//			// 拼接记录order_payment_message_log
//			String isPaid = "N";
//			if(null != orderInfo){
//				isPaid = orderInfo.getIsPaid();
//			}
//			String message = getContentByParameterMap(request.getParameterMap());
//			paymentMessageLog(orderPaymentLog.getPaymentLogId(), "response",
//					"notify", message, orderPaymentLog.getMemberId(),isPaid);
//
//			// 订单有效性校验
//			String be = checkPamentBackBeforeSign(request, orderPaymentLog.getBusinessId().toString(), new BigDecimal(
//					total_fee), orderPaymentLog, orderInfo);
//			if (!be.equals("")) {
//				return null;
//			}
			// 拼接记录order_payment_message_log
			String message = getContentByParameterMap(request.getParameterMap());
			paymentMessageLog(orderPaymentLog, "response","notify", message);
			// 订单有效性校验
			String be = checkPamentBackBeforeSign(request, orderPaymentLog);
			if (!be.equals("")) {
				return null;
			}

			//验签及订单状态修改
			orderPaymentLog.setBackState(trade_status);
			orderPaymentLog.setBackNo(trade_no);
			orderPaymentLog.setPaidFee(new BigDecimal(total_fee));
			if(AlipayNotify.verifyNotify(params,key,_input_charset,partner,sec_id)){//验证成功
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					doSaveResultByLog(orderPaymentLog);
//					doSaveResult(request, orderPaymentLog.getPaymentNo(),
//							trade_no, new BigDecimal(total_fee), trade_status,
//							paymentTypeNo, ecPaymentType.getPaymentTypeId());
					response.getOutputStream().println("success");
				} else {
					doSaveErrResultByLog(orderPaymentLog);
//					doSaveErrResult(request, orderPaymentLog.getPaymentNo(),
//							trade_no, new BigDecimal(total_fee), trade_status,
//							ecPaymentType.getPaymentTypeId());
					response.getOutputStream().println("success");
				}  
			}else{//验证失败 
				response.getOutputStream().println("fail");
			} 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	
	private void jsonReturn(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> sParaTemp){
		try {
			JSONObject jb = new JSONObject();
			JSONObject data = new JSONObject();
			jb.element("status", sParaTemp.get("status"));
			jb.element("msg", sParaTemp.get("msg"));
			data.element("orderId", request.getParameter("orderId"));
			data.element("paymentTypeNo", request.getParameter("paymentTypeNo"));
			data.element("channel", request.getParameter("channel"));
			data.element("businessType", request.getParameter("businessType"));
			data.element("service", sParaTemp.get("service"));
			data.element("partner", sParaTemp.get("partner"));
			data.element("_input_charset", sParaTemp.get("_input_charset"));
			data.element("sec_id", sParaTemp.get("sec_id"));
			data.element("format", sParaTemp.get("format"));
			data.element("v", sParaTemp.get("v"));
			data.element("req_data", sParaTemp.get("req_data"));
			data.element("sign", sParaTemp.get("sign"));
			data.element("paygateway", sParaTemp.get("paygateway"));
			jb.element("data", data);
			
			StringBuilder sb = new StringBuilder();
			String callback = request.getParameter("callback");
			sb.append(callback);
			sb.append("(");
			sb.append(jb.toString());
			sb.append(")");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(sb.toString());
			System.out.println(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("#####APP支付通信异常#######");
		}
	}
	
	private void jsonErrorReturn(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> sParaTemp){
		try {
			JSONObject jb = new JSONObject();
			JSONObject data = new JSONObject();
			jb.element("status", sParaTemp.get("status"));
			jb.element("msg", sParaTemp.get("msg"));
			data.element("orderId", request.getParameter("orderId"));
			data.element("paymentTypeNo", request.getParameter("paymentTypeNo"));
			data.element("channel", request.getParameter("channel"));
			data.element("businessType", request.getParameter("businessType"));
			jb.element("data", data); 
			
			StringBuilder sb = new StringBuilder();
			String callback = request.getParameter("callback");
			sb.append(callback);
			sb.append("(");
			sb.append(jb.toString());
			sb.append(")");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(sb.toString());
			System.out.println(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("#####APP支付通信异常#######");
		}
	}
	
	public static void main(String[] args) {
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alilpay_pay");
		sParaTemp.put("partner", "2001423712");
		sParaTemp.put("_input_charset", "utf-8");
		sParaTemp.put("sec_id", "8276678");
		sParaTemp.put("format", "297819322");
		sParaTemp.put("v", "1.0");
		sParaTemp.put("req_data", "req_data"); 
		sParaTemp.put("status", "0");
		sParaTemp.put("msg", "ok");
		sParaTemp.put("sign", "UHHTTFGHY55678HG");
		sParaTemp.put("paygateway", "http://localhost:8080");
		 
		
		JSONObject jb = new JSONObject();
		JSONObject data = new JSONObject();
		jb.element("status", sParaTemp.get("status"));
		jb.element("msg", sParaTemp.get("msg"));
		data.element("orderId", "2014112501");
		data.element("paymentTypeNo", "alipayWap");
		data.element("channel", "appMall");
		data.element("businessType", "order");
		data.element("service", sParaTemp.get("service"));
		data.element("partner", sParaTemp.get("partner"));
		data.element("_input_charset", sParaTemp.get("_input_charset"));
		data.element("sec_id", sParaTemp.get("sec_id"));
		data.element("format", sParaTemp.get("format"));
		data.element("v", sParaTemp.get("v"));
		data.element("req_data", sParaTemp.get("req_data"));
		data.element("sign", sParaTemp.get("sign"));
		data.element("paygateway", sParaTemp.get("paygateway"));
		jb.element("data", data);
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("json1");
		sb.append("(");
		sb.append(jb.toString());
		sb.append(")");
		System.out.println(sb.toString());
	}
 
}
