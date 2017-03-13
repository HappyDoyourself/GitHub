package cn.com.dubbo.service.payment.platform;

import cn.com.jiuyao.pay.common.constant.ResponseCodeConstants;
import cn.com.dubbo.model.PaymentResult;
import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.service.payment.platform.PlatformPayment;
import cn.com.jiuyao.pay.common.util.DateUtil;
import cn.com.jiuyao.util.payments.alipay.SignatureHelper;
import cn.com.jiuyao.util.payments.alipayWap.AlipayNotify;
import cn.com.jiuyao.util.payments.alipayWap.AlipaySubmit;
import cn.com.jiuyao.util.payments.weixin.TenpayUtil;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;

/**
 * Alipay 支付宝
 * 
 * @author qun.su
 * @version 2014年5月8日
 */
@Service
public class AlipayBase extends PlatformPayment implements Platform {
	/**
	 * 订单查询请求
	 * @param orderPaymentLog
	 * @return PaymentResult
	 */
	@Override
	public PaymentResult queryByOrder(OrderPaymentLog orderPaymentLog) throws Exception{
		String out_trade_no = orderPaymentLog.getPaymentNo();
		String trade_no = orderPaymentLog.getBackNo();
		String input_charset = orderPaymentLog.getEcPaymentTypeParames().get("input_charset");
		String partner = orderPaymentLog.getEcPaymentTypeParames().get("partner"); // 支付宝合作伙伴id (账户内提取)
		String key = orderPaymentLog.getEcPaymentTypeParames().get("private_key");
		// 支付宝安全校验码(账户内提取)
		String gateway_new = orderPaymentLog.getEcPaymentTypeParames().get("gateway_new");
		String sign_type = orderPaymentLog.getEcPaymentTypeParames().get("sign_type") == null
				? "MD5" : orderPaymentLog.getEcPaymentTypeParames().get("sign_type");
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "single_trade_query");
		sParaTemp.put("partner", partner);
		sParaTemp.put("_input_charset", input_charset);
		sParaTemp.put("trade_no", trade_no);
		sParaTemp.put("out_trade_no", out_trade_no);

		//建立请求获取返回XML报文
		String responseString = AlipaySubmit.buildRequest(gateway_new,"", "",sParaTemp,key,input_charset,sign_type);

		//记录查询返回信息
		paymentMessageLog(orderPaymentLog, "response","query", responseString);

		//URLDECODE返回的信息set到PaymentResult 中
		responseString = URLDecoder.decode(responseString,input_charset);
//		System.out.println(responseString);
		//解析报文获取支付结果数据
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new ByteArrayInputStream(responseString.getBytes()));
		List<Node> nodeList = doc.selectNodes("//alipay/*");
		Map<String, String> paraMap = new HashMap<String, String>();//返回的交易信息
		String sign = "";//返回的签名信息,签名类型为MD5
		for (Node node : nodeList) {
			// 截取部分不需要解析的信息
			if (node.getName().equals("is_success") && node.getText().equals("T")) {
				// 判断是否有成功标示
				List<Node> nodeList1 = doc.selectNodes("//trade/*");
				for (Node node1 : nodeList1) {
					paraMap.put(node1.getName(),node1.getText());
				}
			} else if (node.getName().equals("sign")) {
				sign = node.getText();
			}
		}
		//map非空验证签名
		if(paraMap.size()==0){
			throw new Exception("支付宝订单查询出错，商户流水号："+out_trade_no);
		}
		String mysign = SignatureHelper.sign(paraMap, key);
		if (!mysign.equals(sign)) {
			throw new Exception("支付宝订单查询签名无效，商户流水号："+out_trade_no);
		}
		//查询成功，返回结果
		PaymentResult pr = new PaymentResult();
		pr.setIsSuccess(paraMap.get("is_success"));
		pr.setTradeNo(paraMap.get("trade_no"));
		pr.setTotalFee(paraMap.get("total_fee"));
		pr.setOutTradeNo(paraMap.get("out_trade_no"));
		pr.setTradeStatus(paraMap.get("trade_status"));
		pr.setIsDone("TRADE_FINISHED".equals(paraMap.get("trade_status"))
				|| "TRADE_SUCCESS".equals(paraMap.get("trade_status")));
		return pr;
	}

	/**
	 * 订单退款请求
	 * @param
	 * @return PaymentResult
	 */
	/*@Override
	public PaymentResult refundByOrder(OrderPaymentLog orderPaymentLog) throws Exception{
		System.out.println("父方法");
		if (1==1){
			return refundByOrder(orderPaymentLog);
		}

		PaymentResult pr = new PaymentResult();
		String key = orderPaymentLog.getEcPaymentTypeParames().get("private_key"); // 支付宝安全校验码(账户内提取)
		String gateway_new = orderPaymentLog.getEcPaymentTypeParames().get("gateway_new");
		String input_charset = orderPaymentLog.getEcPaymentTypeParames().get("input_charset");
		String partner = orderPaymentLog.getEcPaymentTypeParames().get("partner"); // 支付宝合作伙伴id (账户内提取)
		//String notify_url = orderPaymentLog.getEcPaymentTypeParames().get("refund_notify_url");//服务器异步通知页面路径,需http://格式的完整路径，不允许加?id=123这类自定义参数
		String notify_url = "http://118.187.58.130:8086/pay/refundNotify/alipay.html";
				String sign_type = orderPaymentLog.getEcPaymentTypeParames().get("sign_type") == null ? "MD5" : orderPaymentLog.getEcPaymentTypeParames().get("sign_type");
		//退款批次号
//		String batch_no = orderPaymentLog.getPaymentNo();
		String batch_no = DateUtil.date2String(new Date(),"yyyyMMddhhmmss")+ "01";
		//必填，每进行一次即时到账批量退款，都需要提供一个批次号，必须保证唯一性

		//退款请求时间
		String refund_date = DateUtil.date2String(new Date(),"yyyy-MM-dd HH:mm:ss");
		//必填，格式为：yyyy-MM-dd hh:mm:ss

		//退款总笔数
		String batch_num = "1";
		//必填，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的最大数量999个）

		//单笔数据集
		String detail_data = orderPaymentLog.getOrderPaymentLog().getBackNo() + "^"
				+ orderPaymentLog.getPaymentFee().toString() + "^协商退款" ;
		//必填，格式详见“4.3 单笔数据集参数说明” 原付款支付宝交易号^退款总金额^退款理由


		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
		sParaTemp.put("partner", partner);
		sParaTemp.put("_input_charset", input_charset);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("batch_no", batch_no);
		sParaTemp.put("refund_date", refund_date);
		sParaTemp.put("batch_num", batch_num);
		sParaTemp.put("detail_data", detail_data);
		
		sParaTemp.put("seller_user_id", partner);
		

		paymentMessageLog(orderPaymentLog, "response","refundApply", getContent(sParaTemp));

		//建立请求
		String responseString = AlipaySubmit.buildRequest(gateway_new,"", "",sParaTemp,key,input_charset,sign_type);
		if (responseString.contains("ILLEGAL_SIGN")) {
			logger.error("====An error occurred while applying refund. :"+responseString
					+"; paymentNo : " + orderPaymentLog.getPaymentNo());
			return null;
		}
		//解析报文获取支付结果数据
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new ByteArrayInputStream(responseString.getBytes()));
		List<Node> nodeList = doc.selectNodes("//alipay*//*");
		for (Node node : nodeList) {
			// 截取部分不需要解析的信息
			if (node.getName().equals("is_success") && node.getText().equals("T")) {
				logger.debug("====alipay refund success :" + node.getText()
						+ "; paymentNo : " + orderPaymentLog.getPaymentNo());
				pr.setRefundStatus(ResponseCodeConstants.REFUND_APPLIED_STATE);//已退款申请
			} else if (node.getName().equals("error")) {
				logger.error("====An error occurred while applying refund. :"+node.getText()
						+"; paymentNo : " + orderPaymentLog.getPaymentNo());
			}
		}

		return pr;
	}*/

	@Override
	public Object refundNotify(HttpServletRequest request,
							   HttpServletResponse response, String paymentTypeNo) throws Exception {
		// 取得支付参数
		EcPaymentType ecPaymentType = myPayService.findPaymentInfoByNo(paymentTypeNo);
		Map<String,String> ecPaymentTypeParames = myPayService.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
		// 接收Server返回的支付结果
		String partner = ecPaymentTypeParames.get("partner"); // partner合作伙伴id（必须填写）
		String privateKey = ecPaymentTypeParames.get("private_key"); // partner
		String sign_type = ecPaymentTypeParames.get("sign_type");
		String input_charset = ecPaymentTypeParames.get("input_charset");
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

		//退款批次号
		String batch_no = new String(request.getParameter("batch_no").getBytes("ISO-8859-1"),"UTF-8");
		//退款成功总数 必填，0<= success_num<= batch_num
		String success_num = new String(request.getParameter("success_num").getBytes("ISO-8859-1"),"UTF-8");
		//处理结果详情 result_details:2015052005450347^0.01^SUCCESS$jianyi_021@163.com^2088601128166168^0.00^SUCCESS
		String result_details = new String(request.getParameter("result_details").getBytes("ISO-8859-1"),"UTF-8");
		System.out.println(result_details);
		String[] resultArr =  result_details.split("\\^");
		String refundAmt = resultArr[1];
		String status = resultArr[2].split("\\$")[0];
		String trade_no = resultArr[0];

		// 获取订单日志信息和订单信息
		OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
		orderPaymentLog.setPaymentNo(batch_no);
		orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
		orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog,false);

		// 拼接记录order_payment_message_log
		String message = getContentByParameterMap(request.getParameterMap());
		paymentMessageLog(orderPaymentLog, "response","refund",message);

		if(AlipayNotify.verify(params, partner, privateKey, input_charset, sign_type)){//验证成功
			if("SUCCESS".equals(status)){
				orderPaymentLog.setRefundState(ResponseCodeConstants.SUCCESS);
				orderPaymentLog.setPaidFee(new BigDecimal(refundAmt));
			}else{
				orderPaymentLog.setRefundState(ResponseCodeConstants.FAIL);
				orderPaymentLog.setPaidFee(new BigDecimal(0));
			}
			orderPaymentLog.setBackState(status);
			orderPaymentLog.setBackNo(trade_no);
			doSaveResultByLog(orderPaymentLog);

			response.getOutputStream().println("success");
		}else{//验证失败
			response.getOutputStream().println("fail");
		}
		return null;
	}

}
