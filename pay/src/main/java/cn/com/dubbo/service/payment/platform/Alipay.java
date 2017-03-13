package cn.com.dubbo.service.payment.platform;

import org.springframework.stereotype.Service;

import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.jiuyao.pay.common.util.Md5Encrypt;
import cn.com.jiuyao.util.payments.alipay.Payment;
import cn.com.jiuyao.util.payments.alipay.SignatureHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Alipay 支付宝
 * 
 * @author qun.su
 * @version 2014年5月8日
 */
@Service
public class Alipay extends AlipayBase{
	@Override
	public String requestMessagePackage(HttpServletRequest request,
			HttpServletResponse response, OrderPaymentLog orderPaymentLog) {
		Map<String, String> ecPaymentTypeParames = orderPaymentLog.getEcPaymentTypeParames();
		EcPaymentType ecPaymentType = orderPaymentLog.getEcPaymentType();
		String message = "";
		try {
			//取得支付参数
			String out_trade_no = orderPaymentLog.getPaymentNo(); // 商户网站订单流水号
			String body = ecPaymentTypeParames.get("body")+ orderPaymentLog.getPaymentNo(); // 商品描述，推荐格式：商品名称（订单编号：订单编号）
			BigDecimal paymentFee = orderPaymentLog.getPaymentFee().setScale(2,BigDecimal.ROUND_HALF_EVEN);
			String total_fee = paymentFee.toString(); // 订单总价
			String subject = "9yao-"+request.getParameter("orderId"); // 商品名称
			String show_url = ecPaymentType.getShowUrl()+request.getParameter("orderId");
			String return_url = ecPaymentType.getReturnUrl(); // 通知接收URL
			String notify_url = ecPaymentType.getNotifyUrl();
			
			String payment_type = ecPaymentTypeParames.get("payment_type");// 支付宝类型.1代表商品购买
			// 获取数据库信息
			
			String paygateway = ecPaymentTypeParames.get("paygateway"); // '支付接口
			String service = ecPaymentTypeParames.get("service");// 快速付款交易服务
			String sign_type = ecPaymentTypeParames.get("sign_type");
			String input_charset = ecPaymentTypeParames.get("input_charset");
			String partner = ecPaymentTypeParames.get("partner"); // 支付宝合作伙伴id (账户内提取)
			String key = ecPaymentTypeParames.get("private_key"); // 支付宝安全校验码(账户内提取)
			String seller_email = ecPaymentType.getManagerEmail(); // 卖家支付宝帐户()
			String paymethod = ecPaymentTypeParames.get("paymethod");// 赋值:bankPay(网银);cartoon(卡通); directPay(余额)
			// 三种付款方式都要，参数为空
			String defaultbank = (ecPaymentTypeParames.get("defaultbank")==null)?"":ecPaymentTypeParames.get("defaultbank");
			String it_b_pay = getCancelMinutes(orderPaymentLog.getStartTime())+"m";//根据订单提交时间+12小时
			//签名
			Map params = new HashMap();
			params.put("service", service);
			params.put("partner", partner);
			params.put("subject", subject);
			params.put("body", body);
			params.put("out_trade_no", out_trade_no);
			params.put("total_fee", total_fee);
			params.put("show_url", show_url);
			params.put("payment_type",payment_type);
			params.put("seller_email", seller_email);
			params.put("return_url", return_url);
			params.put("notify_url", notify_url);
			params.put("_input_charset", input_charset);
			params.put("paymethod", paymethod);
			params.put("defaultbank", defaultbank);
			params.put("it_b_pay",it_b_pay);
			String sign = Md5Encrypt.md5(Payment.getContent(params, key));
			
			request.setAttribute("out_trade_no", out_trade_no); 
			request.setAttribute("body", body); 
			request.setAttribute("total_fee", total_fee); 
			request.setAttribute("subject", subject); 
			request.setAttribute("show_url", show_url); 
			request.setAttribute("return_url", return_url); 
			request.setAttribute("notify_url", notify_url); 
			request.setAttribute("payment_type", payment_type); 
			request.setAttribute("paygateway", paygateway); 
			request.setAttribute("service", service); 
			request.setAttribute("sign_type", sign_type); 
			request.setAttribute("input_charset", input_charset); 
			request.setAttribute("partner", partner); 
			request.setAttribute("key", key); 
			request.setAttribute("seller_email", seller_email); 
			request.setAttribute("defaultbank", defaultbank); 
			request.setAttribute("sign", sign);
			request.setAttribute("paymethod", paymethod);
			request.setAttribute("it_b_pay", it_b_pay);

			/**拼接请求信息*/
			params.put("paygateway", paygateway);
			params.put("sign", sign);
			message = getContent(params);
			
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
			// 接收Server返回的支付结果
			String privateKey = ecPaymentTypeParames.get("private_key"); // partner
			
			String out_trade_no = request.getParameter("out_trade_no");
			String trade_no     = request.getParameter("trade_no");
			String total_fee    = request.getParameter("total_fee");
			String sign = request.getParameter("sign");
			String trade_status = request.getParameter("trade_status");
			// 获取订单日志信息和订单信息
			OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
			orderPaymentLog.setPaymentNo(out_trade_no);
			orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
			orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog,false);

			// 拼接记录order_payment_message_log
			String message = getContentByParameterMap(request.getParameterMap());
			paymentMessageLog(orderPaymentLog, "response","return", message);

			
			// 订单有效性校验
			String be = checkPamentBackBeforeSign(request, orderPaymentLog);
			if (!be.equals("")) {
				request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
				request.getRequestDispatcher(be).forward(request, response);
				return null;
			}
			//验签 获得POST 过来参数设置到新的params中
			Map params = new HashMap();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化（现在已经使用）
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
				if ("subject".equals(name)) {
					valueStr = new String(request.getParameter("subject").getBytes("iso-8859-1"), "utf-8");
				}
				if(!"notify_id".equals(name)){
//					valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
					valueStr = URLDecoder.decode(valueStr,"UTF-8");
					//valueStr = URLDecoder.decode(valueStr);
				}
				params.put(name, valueStr);
			}
			String mysign = SignatureHelper.sign(params, privateKey);
			
			if (!mysign.equals(sign)) {
				request.setAttribute("paidSuccess", "抱歉,您的订单支付失败！签名无效");
				request.setAttribute("payFlag", "failure");
				request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
				request.getRequestDispatcher("/jsp/myec/pay_done.jsp").forward(
						request, response);
				return null;
			}
			 
			//验签后判断返回码
			orderPaymentLog.setBackState(trade_status);
			orderPaymentLog.setBackNo(trade_no);
			orderPaymentLog.setPaidFee(new BigDecimal(total_fee));
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				doSaveResultByLog(orderPaymentLog);
				request.setAttribute("payFlag", "success");
				request.setAttribute("paymentNo",orderPaymentLog.getPaymentNo());
				request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
				request.setAttribute("paidSuccess", "订单支付成功");
				request.setAttribute("orderId", orderPaymentLog.getBusinessId());
				request.setAttribute("total_fee", total_fee);
				request.setAttribute("deliveryReceiver", "");
				request.setAttribute("deliveryAddressFull", "");
				request.getRequestDispatcher(PAY_DONE_JSP_PATH).forward(request, response);
				return null;
			} else {
				doSaveErrResultByLog(orderPaymentLog);
				request.setAttribute("paidSuccess", "抱歉,您的订单支付失败！");
				request.setAttribute("payFlag", "failure");
				request.setAttribute("paymentNo",
						orderPaymentLog.getPaymentNo());
				request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
				request.getRequestDispatcher(PAY_DONE_JSP_PATH).forward(
						request, response);
				return null;
			}
		} catch (Exception e) {
			logger.error("-------Ec-pay_error: alipay return, paymentNo : + " + request.getParameter("out_trade_no"));
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
			// 接收Server返回的支付结果
			String privateKey = ecPaymentTypeParames.get("private_key"); // partner
			 
			String out_trade_no = request.getParameter("out_trade_no");
			String trade_no     = request.getParameter("trade_no");
			String total_fee    = request.getParameter("total_fee");
			String sign = request.getParameter("sign");
			String trade_status = request.getParameter("trade_status");
			
			// 获取订单日志信息和订单信息
			OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
			orderPaymentLog.setPaymentNo(out_trade_no);
			orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
			orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog,false);

			String message = getContentByParameterMap(request.getParameterMap());
			paymentMessageLog(orderPaymentLog, "response","return", message);
			
			// 订单有效性校验
			String be = checkPamentBackBeforeSign(request, orderPaymentLog);
			if (!be.equals("")) {
				return null;
			}
			//验签 获得POST 过来参数设置到新的params中
			Map params = new HashMap();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化（现在已经使用）
				if(!"notify_id".equals(name)){
					valueStr = URLDecoder.decode(valueStr,"UTF-8");
				}
				params.put(name, valueStr);
			}
			String mysign = SignatureHelper.sign(params, privateKey);
			
			if (mysign.equals(sign)) {
				orderPaymentLog.setBackState(trade_status);
				orderPaymentLog.setBackNo(trade_no);
				orderPaymentLog.setPaidFee(new BigDecimal(total_fee));
				/* 可以在不同状态下获取订单信息，操作商户数据库使数据同步 */
				if ("WAIT_BUYER_PAY".equals(request
						.getParameter("trade_status"))) {// WAIT_BUYER_PAY;等待买家付款
					response.getOutputStream().println("success");// 输出返回一定要是不包含HTML语言的success
				} 
				if ("TRADE_FINISHED".equals(trade_status)
						|| "TRADE_SUCCESS".equals(trade_status)) {// TRADE_FINISHED交易完成;TRADE_SUCCESS 交易成功
					doSaveResultByLog(orderPaymentLog);
					response.getOutputStream().println("success");
				} else {
					doSaveErrResultByLog(orderPaymentLog);
					response.getOutputStream().println("success");
				}
			} else {
				response.getOutputStream().println("fail" + "<br>");
				// 打印，收到消息比对sign的计算结果和传递来的sign是否匹配
				response.getOutputStream().println(
						mysign + "--------------------"
								+ request.getParameter("sign") + "<br>");
			}
		} catch (Exception e) {
			//logger.error("-------Ec-pay_error: alipay return, paymentNo : + " + request.getParameter("out_trade_no"));
			e.printStackTrace();
		}
		return null;
	}

}
