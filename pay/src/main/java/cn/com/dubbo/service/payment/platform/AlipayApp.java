package cn.com.dubbo.service.payment.platform;

import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.PaymentResult;
import cn.com.jiuyao.pay.common.constant.ResponseCodeConstants;
import cn.com.jiuyao.pay.common.util.*;
import cn.com.jiuyao.util.payments.alipayWap.AlipayNotify;
import cn.com.jiuyao.util.payments.alipayWap.RSA;
import cn.com.jiuyao.util.payments.alipayWap.config.AlipayConfig;
import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Alipay app  手机端支付宝
 * 
 * @author jinjin
 * @version 2016年11月3日
 */
@Service
public class AlipayApp extends AlipayBase {

	@Override
	public String requestMessagePackage(HttpServletRequest request,
			HttpServletResponse response,  OrderPaymentLog orderPaymentLog) {
		Map<String, String> ecPaymentTypeParames = orderPaymentLog.getEcPaymentTypeParames();
		String message = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		//String methodType = request.getParameter("method");
		try {
			//获取参数 系统参数
			String app_id = ecPaymentTypeParames.get("app_id");
			String method = "";//接口名称
			String return_url = "";//前台返回地址，wap端用
			if ("wap".equals(orderPaymentLog.getChannel())) {
				method = ecPaymentTypeParames.get("methodWap");
				return_url = request.getParameter("return_url");
			}else {
				method = ecPaymentTypeParames.get("method");
			}
			String charset = ecPaymentTypeParames.get("charset");//字符集
			String sign_type  = ecPaymentTypeParames.get("sign_type");//签名方式
			String timestamp = format.format(new Date());
			String version = ecPaymentTypeParames.get("version"); // 版本号
			String notify_url = orderPaymentLog.getEcPaymentType().getNotifyUrl(); // 服务器异步通知页面路径

			String private_key = ecPaymentTypeParames.get("private_key"); // 私钥

			//业务参数
			BigDecimal paymentFee = orderPaymentLog.getPaymentFee().setScale(2,BigDecimal.ROUND_HALF_EVEN);
			String body = "商品"; // 商品描述 非空
			String subject = "9yao-"+request.getParameter("orderId"); // 商品名称 非空
			String out_trade_no = orderPaymentLog.getPaymentNo(); // 商户网站订单流水号
			String timeout_express = getCancelMinutes(orderPaymentLog.getStartTime())+"m";//交易自动关闭时间 单位为分

			String total_amount = paymentFee.toString(); // 订单总价
			String product_code = "QUICK_MSECURITY_PAY";
			String seller_id = ecPaymentTypeParames.get("partner"); // 支付宝合作伙伴id

			String biz_content =  "{\"timeout_express\":\""+timeout_express+"\",\"product_code\":\""+product_code+"\",\"total_amount\":\""+
			total_amount+"\",\"subject\":\""+subject+"\",\"body\":\""+body+"\",\"out_trade_no\":\"" +out_trade_no +  "\",\"seller_id\":\""+seller_id+"\"}";

			//组装数据
			/**
			 * app_id=2014072300007148&biz_content=44&charset=GBK&method=alipay.mobile.public.menu.add&sign_type=RSA&timestamp=2014-07-24 03:07:50&version=1.0
			 */
			Map<String,String> sigMap = new HashMap<String	, String>();
			StringBuilder sb = new StringBuilder();
			sb.append("app_id=");
			sb.append(app_id);
			sigMap.put("app_id", app_id);

			sb.append("&biz_content=");
//			sb.append(URLEncoder.encode(biz_content, "UTF-8"));
			sb.append(biz_content);
			sigMap.put("biz_content", biz_content);

			sb.append("&charset=");
			sb.append(charset);
			sigMap.put("charset", charset);

			sb.append("&method=");
//			sb.append(URLEncoder.encode(method, "UTF-8"));
			sb.append(method);
			sigMap.put("method", method);

			sb.append("&sign_type=");
			sb.append(sign_type);
			sigMap.put("sign_type", sign_type);

			sb.append("&timestamp=");
//			sb.append(URLEncoder.encode(timestamp, "UTF-8"));
			sb.append(timestamp);
			sigMap.put("timestamp", timestamp);

			sb.append("&version=");
			sb.append(version);
			sigMap.put("version", version);

			sb.append("&notify_url=");
			// 网址需要做URL编码
//			sb.append(URLEncoder.encode(notify_url, "UTF-8"));
			sb.append(notify_url);
			sigMap.put("notify_url", notify_url);

			if (!"".equals(return_url)) {
				sb.append("&return_url=");
				sb.append(return_url);
				sigMap.put("return_url", return_url);
			}

			//签名0
			String signInfo = RSA.getStringSort(sigMap);
			String sign = RSA.sign(signInfo, private_key, charset);
			sign = URLEncoder.encode(sign, "UTF-8");

			sb.append("&sign=");
			sb.append(sign);

			String info = sb.toString();
			//返回给app端
			JSONObject jb = new JSONObject();
			JSONObject data = new JSONObject();
			jb.element("status", 0);
			jb.element("msg", "OK");
			data.element("orderId", request.getParameter("orderId"));
			data.element("paymentTypeNo", request.getParameter("paymentTypeNo"));
			data.element("channel", request.getParameter("channel"));
			data.element("businessType", request.getParameter("businessType"));
			data.element("orderInfo", info);
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
			//将请求信息拼接以便记录至数据库
			message = info;
			response.getWriter().flush();
			response.getWriter().close();
			response.flushBuffer();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
		return message;
	}

	/**
	 * 同步通知
	 */
	@Override
	public String returnMessageHandle(HttpServletRequest request,
			HttpServletResponse response, String paymentTypeNo){
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
			String _input_charset = ecPaymentTypeParames.get("charset");//字符集
			String public_key = ecPaymentTypeParames.get("public_key");// 公钥
			String partner = ecPaymentTypeParames.get("partner"); // 支付宝合作伙伴id
			String sign_type  = ecPaymentTypeParames.get("sign_type");//签名方式
			System.out.println(request.getParameter("body"));
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
//				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				System.out.println(name+":"+valueStr);
				if ("sign".equals(name)) {
					valueStr = valueStr.replace(" ", "+");
				}else if ("body".equals(name)&&!"商品".equals(valueStr)) {//支付宝有时此字段返回为乱码
					valueStr = "商品";
				}
				params.put(name, valueStr);
			}

			//商户订单号
			String out_trade_no = request.getParameter("out_trade_no");
			//支付宝交易号
			String trade_no = request.getParameter("trade_no");
			//交易金额
			String total_fee = request.getParameter("total_amount");
			//交易状态
			String trade_status = request.getParameter("trade_status");

			// 获取订单日志信息和订单信息
			OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
			orderPaymentLog.setPaymentNo(out_trade_no);
			orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
			orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog,false);
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
			if(AlipayNotify.verify(params,partner,public_key,_input_charset,sign_type)){//验证成功
				if ("WAIT_BUYER_PAY".equals(trade_status)) {// WAIT_BUYER_PAY;等待买家付款
					return null;
				}else if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					doSaveResultByLog(orderPaymentLog);
					response.getWriter().print("success");
				} else {
					doSaveErrResultByLog(orderPaymentLog);
					response.getWriter().print("success");
				}
			}else{//验证失败
				response.getWriter().print("fail");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 支付宝退款接口（蚂蚁金服退款接口）
	 * @param orderPaymentLog
	 * @return
	 */
	    @Override
	    public PaymentResult refundByOrder(OrderPaymentLog orderPaymentLog) {
			PaymentResult paymentResult = new PaymentResult();
		 try {
			Map<String, String> parameter = myPayService
					.findPaymentTypeListInfo(orderPaymentLog.getPaymentTypeId());
			EcPaymentType ecPaymentType = myPayService.
					findPaymentInfoById(orderPaymentLog.getPaymentTypeId().toString());
			orderPaymentLog.setEcPaymentTypeParames(parameter);
			orderPaymentLog.setEcPaymentType(ecPaymentType);
			//获取支付宝sign的参数
			String app_id = parameter.get("app_id"); //支付宝分配给开发者的应用ID
			String method = AlipayConfig.REFUND_METHOD; //接口名称
			String charset = parameter.get("charset"); //字符集
			String sign_type = parameter.get("sign_type"); //签名方式
			String timestamp = DateUtil.DateTimeToStr(new Date());
			String version = parameter.get("version");  // 版本号
			String private_key = parameter.get("private_key"); // 私钥
			String refund_amount = orderPaymentLog.getPaymentFee().toString(); // 订单总价
			String trade_no = orderPaymentLog.getBackNo(); //支付宝交易号，和商户订单号不能同时为空
			String biz_content =  "{\"trade_no\":\"" + trade_no + "\",\"refund_amount\":\"" + refund_amount + "\"}";

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
			if (StringUtils.isEmpty(resJSON)) {
				logger.info("AliPayRefund fail, responseData is null, orderNo ：{" + orderPaymentLog.getBusinessId() +"}");
				return null;
			}
			com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(resJSON);
			com.alibaba.fastjson.JSONObject jsonObjectResponse = jsonObject.getJSONObject("alipay_trade_refund_response");
			if (!AlipayConfig.ALIPAY_CODE.equals(jsonObjectResponse.get("code"))) {
				logger.info("AliPayRefund fail, aliPayRefund responseData： " + jsonObject.get("aliApp_trade_refund_response") +", orderNo " + orderPaymentLog.getBusinessId());
				doSaveErrResultByLog(orderPaymentLog);
				return null;
			}else {
				logger.info("aliPay refund success, orderNo:" + orderPaymentLog.getBusinessId());
				paymentResult.setTradeNo(jsonObjectResponse.get("trade_no").toString());//支付宝返回的流水号和支付的相同，为了防止后面的重复故加上商户号
				paymentResult.setRefundStatus(ResponseCodeConstants.REFUND_APPLIED_STATE);
				paymentResult.setRefundFee(jsonObjectResponse.get("refund_fee").toString());
				return paymentResult;
			}
		}catch(Exception e){
			 logger.error("=====aliPay refund has exception ," + e);
		}
		return null;
	}

	/**
	 * 订单退款查询请求
	 * @param orderPaymentLog
	 * @return PaymentResult
	 */
	@Override
	public PaymentResult refundQueryByOrder(OrderPaymentLog orderPaymentLog) throws Exception{
		System.out.println("订单退款查询");
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
		String biz_content =  "{\"trade_no\":\"" + trade_no + "\",\"out_request_no\":\"" + orderPaymentLog.getPaymentTypeId() + "\"}";

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
		if (StringUtils.isEmpty(resJSON)) {
			logger.info("AliPayRefundQuery fail, responseData is null, orderNo ：{" + orderPaymentLog.getBusinessId() +"}");
			return null;
		}

		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(resJSON);
		com.alibaba.fastjson.JSONObject jsonObjectResponse = jsonObject.getJSONObject("alipay_trade_fastpay_refund_query_response");
		if (!AlipayConfig.ALIPAY_CODE.equals(jsonObjectResponse.get("code"))) {
			logger.info("AliPayRefund fail, aliPayRefund responseData： " + jsonObject.get("aliApp_trade_refund_response") +", orderNo " + orderPaymentLog.getBusinessId());
			doSaveErrResultByLog(orderPaymentLog);
			return null;
		}else {
			logger.info("aliPay refund success, orderNo:" + orderPaymentLog.getBusinessId());
			paymentResult.setRefundStatus(ResponseCodeConstants.REFUND_APPLIED_STATE);
			return paymentResult;
		}
	}
}
