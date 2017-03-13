package cn.com.dubbo.service.payment.platform;

import cn.com.dubbo.model.PaymentResult;
import cn.com.jiuyao.pay.common.util.DateUtils;
import cn.com.jiuyao.util.IPUtil;
import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.WeixinPay;
import cn.com.jiuyao.util.HexUtil;
import cn.com.jiuyao.util.payments.weixin.*;
import cn.com.jiuyao.util.payments.weixin.client.ClientResponseHandler;
import cn.com.jiuyao.util.payments.weixin.client.TenpayHttpClient;
import cn.com.dubbo.model.OrderInfo;
import cn.com.dubbo.redis.CacheUtil;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jdom.input.JDOMParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * wxpay 主站微信支付
 * 
 * @author qun.su
 * @version 2015年01月05日
 */
@Service
public class Wxpay extends WeixinBase implements Platform {

	@Override
	public String requestMessagePackage(HttpServletRequest request,
			HttpServletResponse response,  OrderPaymentLog orderPaymentLog) {
		String message = "";
		try {
			request.setCharacterEncoding("utf-8");
			//判断是否获取过微信二维码链接
			if (StringUtils.isNotEmpty(orderPaymentLog.getFieldOne())) {
				request.setAttribute("content", HexUtil.bytes2Hex(orderPaymentLog.getFieldOne().getBytes()));
				message = orderPaymentLog.getFieldOne();
			}else{
				/*******************************************************获取支付参数*********************************************/
				WeixinPay pay = getParas(request, orderPaymentLog,"NATIVE");
				
				/*******************************************************创建微信统一下单接口的订单*********************************************/
				Map<String,String> map =createWeiXinOrder(orderPaymentLog, pay.getMapPara(), pay.getAppKey());
				if (map == null || map.size() == 0) {
					
					return "";
				}
				
				//将url传到jsp中，异步生产二维码
				if ("SUCCESS".equals(map.get("return_code"))) {
					request.setAttribute("content", HexUtil.bytes2Hex(map.get("code_url").getBytes()));
					OrderPaymentLog opl = new OrderPaymentLog();
					opl.setPaymentLogId(orderPaymentLog.getPaymentLogId());
					opl.setFieldOne(map.get("code_url"));
					myPayService.updateOrderPaymentLog(opl);
				}else {
					return "";
				}
				message = map.toString();
			}
			
			request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo());
			request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
			request.setAttribute("paymentFee", orderPaymentLog.getPaymentFee().setScale(2).toString());
			request.setAttribute("orderId", orderPaymentLog.getBusinessId().toString());
			//供二维码页面轮询用，当key值为true时，页面跳转到支付成功页面
//			String key = "WX"+orderPaymentLog.getPaymentNo();
//			boolean b = cache.put(key,false,60);
			/**拼接请求信息*/  
			
		}catch(JDOMParseException jdm){
			jdm.printStackTrace();
			request.setAttribute("paidSuccess", "原因，商户订单号重复");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return message;
	}

	/**
	 *  支付回调商户后台获取package
	 */
	@Override
	public String returnMessageHandle(HttpServletRequest request,
			HttpServletResponse response, String paymentTypeNo){
		try {
			// 取得支付参数
			EcPaymentType ecPaymentType = myPayService.findPaymentInfoByNo(paymentTypeNo);
			Map<String,String> parameter = myPayService.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
			String notify_url = parameter.get("notify_url");//异步通知url
			String appId = parameter.get("appId");// 微信开发平台应用id
			String appKey = parameter.get("appKey");
			String appSecret = parameter.get("appSecret");
			String partner = parameter.get("partner");// 财付通商户号
			String key = parameter.get("partnerKey");// 商户号对应的密钥
			String fee_type = parameter.get("fee_type");// 币种
			String input_charset = parameter.get("input_charset");// 字符集编码
			String tokenUrl = parameter.get("tokenUrl");
			String gateUrl = parameter.get("gateUrl");// 获取预支付id的接口url
			// 获取收到的报文
			String xmlContent = HttpClientUtil.InputStreamTOString(request.getInputStream(), TenpayUtil.getCharacterEncoding(request, response));
			//解析xml,得到map
			Map map = XMLUtil.doXMLParse(xmlContent);
			String out_trade_no = map.get("ProductId").toString();
			String openId = map.get("OpenId").toString();//击链接准备购买商品的用户标识
			String AppSignature = map.get("AppSignature").toString();//参数的加密签名

			// 获取订单日志信息和订单信息
			OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
			orderPaymentLog.setPaymentNo(out_trade_no);
			orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
			orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog,
					false);
			OrderInfo orderInfo = orderService.findOrderById(orderPaymentLog
					.getBusinessId()+"");
			String isPaid = "N";
			if (null != orderInfo) {
				isPaid = orderInfo.getIsPaid();
			}

			//生成时间戳
			String timeStamp = TenpayUtil.getTimeStamp();
			//生成随机数
			String nonceStr = TenpayUtil.getNonceStr();

			ResponseHandler resHandler = new ResponseHandler(request, response);
			RequestHandler packageReqHandler = new RequestHandler(request, response);
			RequestHandler paySignReqHandler = new RequestHandler(request, response);
			resHandler.setWxparameter("appkey", appKey);
			if (WXUtil.isWeixinSign(map, AppSignature, appKey)) {
				//生成package參數值
				String total_fee = orderPaymentLog.getPaymentFee().multiply(new BigDecimal(100)).setScale(0).toString();// 订单总价（单位：分）
				packageReqHandler.setKey(key);
				packageReqHandler.setParameter("partner", partner);//商户号
				packageReqHandler.setParameter("fee_type", fee_type);//币种类型
				packageReqHandler.setParameter("input_charset", input_charset);//字符集编码
				packageReqHandler.setParameter("out_trade_no", out_trade_no);//订单号，此处如果设置为productId就可以实现一个二维码对应一个订单，获取方式：$resHandler->getProductId()
				packageReqHandler.setParameter("total_fee", total_fee);//商品总金额,以分为单位
				packageReqHandler.setParameter("notify_url", notify_url);//通知地址
				packageReqHandler.setParameter("body", "九药网-商品");//商品描述
				packageReqHandler.setParameter("spbill_create_ip", IPUtil.getIpAddr(request));//支付机器IP
				String packageValue = packageReqHandler.getPackageValue();

				// 拼接记录order_payment_message_log
				paymentMessageLog(orderPaymentLog.getPaymentLogId(), "request",
						"", packageValue, orderPaymentLog.getMemberId(), isPaid);
				// 获取access_token,记录到orderPaymentLog中，其他API接口需要这个token
				PayData payData = new PayData();
				payData.setAppId(appId);
				payData.setAppKey(appKey);
				payData.setAppSecret(appSecret);
				payData.setPackageValue(packageValue);
				payData.setGrantType("client_credential");
				payData.setTokenUrl(tokenUrl);
				payData.setGateUrl(gateUrl);
				payData.setTraceid(orderPaymentLog.getMemberId().toString());
				payData.setExpireErrCode("42001");
				payData.setFailErrCode("40001");
				//用户access_token，放入memcached中，有效期120分钟
				String access_token = (String)CacheUtil.readCache("WXPayToken" + orderPaymentLog.getBusinessId().toString());
				payData.setAccessToken(access_token == null ? "":access_token);
				access_token = AccessTokenRequestHandler.getWxPayAccessToken(payData);
				CacheUtil.setCache("WXPayToken"+orderPaymentLog.getBusinessId().toString(),access_token,120);

				orderPaymentLog.setOpenId(openId);
				orderPaymentLog.setEditTime(DateUtils.longToDateAll(System.currentTimeMillis()));
				myPayService.updateOrderPaymentLog(orderPaymentLog);

				//生成paySign參數值
				paySignReqHandler.setParameter("appid", appId);//公众号appid
				paySignReqHandler.setParameter("appkey", appKey);//公众号appid对应的密钥
				paySignReqHandler.setParameter("noncestr", nonceStr);//随机字符串
				paySignReqHandler.setParameter("package", packageValue);//package订单信息
				paySignReqHandler.setParameter("timestamp", timeStamp);//时间戳
				paySignReqHandler.setParameter("retcode", "0");
				paySignReqHandler.setParameter("reterrmsg", "OK");
				String paySign = paySignReqHandler.getPaySign();

				//响应post数据给微信
				StringBuffer packageXml = new StringBuffer();
				packageXml.append("<xml>");
				packageXml.append("<AppId>" + "<![CDATA[" + appId + "]]>" + "</AppId>");
				packageXml.append("<Package>" + "<![CDATA[" + packageValue + "]]>" + "</Package>");
				packageXml.append("<TimeStamp>" + timeStamp + "</TimeStamp>");
				packageXml.append("<NonceStr>" + "<![CDATA[" + nonceStr + "]]>" + "</NonceStr>");
				packageXml.append("<RetCode>" + "0" + "</RetCode>");//此处需要判断订单是否存在，如果一定下架则定义为-1或-2
				packageXml.append("<RetErrMsg>" + "<![CDATA[OK]]>" + "</RetErrMsg>");//判断订单不存在后此消息可以自定义，值为UTF8 编码的错误提示信息，比如“该商品已经下架”
				packageXml.append("<AppSignature>" + "<![CDATA[" + paySign + "]]>" + "</AppSignature>");
				packageXml.append("<SignMethod>" + "<![CDATA[sha1]]>" + "</SignMethod>");
				packageXml.append("</xml>");

				resHandler.sendToCFT(packageXml.toString());

			} else {
				String paySign = paySignReqHandler.getPaySign();
				//响应post数据给微信
				StringBuffer packageXml = new StringBuffer();
				packageXml.append("<xml>");
				packageXml.append("<AppId>" + "<![CDATA[" + appId + "]]>" + "</AppId>");
				packageXml.append("<Package>" + "<![CDATA[" + "wxpay" + "]]>" + "</Package>");
				packageXml.append("<TimeStamp>" + timeStamp + "</TimeStamp>");
				packageXml.append("<NonceStr>" + "<![CDATA[" + nonceStr + "]]>" + "</NonceStr>");
				packageXml.append("<RetCode>" + "-1" + "</RetCode>");
				packageXml.append("<RetErrMsg>" + "<![CDATA[" + URLEncoder.encode("验证签名失败错误！", "UTF-8") + "]]>" + "</RetErrMsg>");
				packageXml.append("<AppSignature>" + "<![CDATA[" + paySign + "]]>" + "</AppSignature>");
				packageXml.append("<SignMethod>" + "<![CDATA[sha1]]>" + "</SignMethod>");
				packageXml.append("</xml>");

				resHandler.sendToCFT(packageXml.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("PC端微信支付回调商户后台获取package异常！");
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
		baseNotifyMessage(request, response, paymentTypeNo);
		return null;
	}

	/**
	 * 发货通知
	 * @param orderPaymentLog 支付日志对象
	 * @return 支付结果
	 */
	public void deliverNotify(OrderPaymentLog orderPaymentLog) {
		try {
			Map<String, String> parameter = orderPaymentLog.getEcPaymentTypeParames();
			String access_token = (String)CacheUtil.readCache("WXPayToken" + orderPaymentLog.getBusinessId().toString());
			String out_trade_no = orderPaymentLog.getPaymentNo();
			String appId = parameter.get("appId");// 微信开发平台应用id
			String appKey = parameter.get("appKey");
			String openId = orderPaymentLog.getOpenId();
			String transId = orderPaymentLog.getBackNo();
			String deliver_status = "1";
			String deliver_msg = "ok";
			String deliverNotify = parameter.get("deliverNotify")+access_token;// 发货通知URL
			//生成时间戳
			String timeStamp = TenpayUtil.getTimeStamp();
			//生成paySign參數值
			RequestHandler paySignReqHandler = new RequestHandler(null, null);
			paySignReqHandler.setParameter("appid", appId);//公众号appid
			paySignReqHandler.setParameter("appkey", appKey);//公众号appid对应的密钥
			paySignReqHandler.setParameter("openid", openId);
			paySignReqHandler.setParameter("transid", transId);
			paySignReqHandler.setParameter("out_trade_no", out_trade_no);
			paySignReqHandler.setParameter("deliver_timestamp", timeStamp);//时间戳
			paySignReqHandler.setParameter("deliver_status", deliver_status);
			paySignReqHandler.setParameter("deliver_msg", deliver_msg);
			String app_signature = paySignReqHandler.getPaySign();

			JSONObject obj = new JSONObject();
			obj.element("appid", appId);
			obj.element("openid", openId);
			obj.element("transid", transId);
			obj.element("out_trade_no", out_trade_no);
			obj.element("deliver_timestamp", timeStamp);
			obj.element("deliver_status", deliver_status);
			obj.element("deliver_msg", deliver_msg);
			obj.element("app_signature", app_signature);
			obj.element("sign_method", "sha1");

			//http发送查询请求获取返回报文
			String content = WXUtil.post(deliverNotify, obj.toString());
			JSONObject jsonObject = JSONObject.fromObject(content);
			String errcode = jsonObject.get("errcode").toString();//如果有异常,会在errmsg 和 errcode 描述出来，如果成功 errcode 就是0
			String errmsg = jsonObject.get("errmsg").toString();

			if(!errcode.equals("0")) {
				logger.error("微信订单发货通知异常，商城支付流水号：" + out_trade_no +
						"微信返回码errcode：" + errcode + "微信返回错误信息errmsg：" + errmsg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 订单查询请求
	 * @param orderPaymentLog
	 * @return PaymentResult
	 */
	public PaymentResult queryByOrder(OrderPaymentLog orderPaymentLog) throws Exception{
		String access_token = CacheUtil.readCache("WXPayToken" + orderPaymentLog.getBusinessId().toString());
		String out_trade_no = orderPaymentLog.getPaymentNo();
		String appId = orderPaymentLog.getEcPaymentTypeParames().get("appId");// 微信开发平台应用id
		String appKey = orderPaymentLog.getEcPaymentTypeParames().get("appKey");
		String partner = orderPaymentLog.getEcPaymentTypeParames().get("partner");// 财付通商户号
		String key = orderPaymentLog.getEcPaymentTypeParames().get("partnerKey");// 商户号对应的密钥
		String orderQuery = orderPaymentLog.getEcPaymentTypeParames().get("orderQuery")+access_token;// 订单查询URL
//生产packageValue
		String signData = "out_trade_no="+out_trade_no+"&partner="+partner+"&key="+key;
		String sign = MD5Util.MD5Encode(signData,null).toUpperCase();
		String packageValue = "out_trade_no="+out_trade_no+"&partner="+partner+"&sign="+sign;
		//生成时间戳
		String timeStamp = TenpayUtil.getTimeStamp();
		//生成paySign參數值
		RequestHandler paySignReqHandler = new RequestHandler(null, null);
		paySignReqHandler.setParameter("appid", appId);//公众号appid
		paySignReqHandler.setParameter("appkey", appKey);//公众号appid对应的密钥
		paySignReqHandler.setParameter("package", packageValue);//package订单信息
		paySignReqHandler.setParameter("timestamp", timeStamp);//时间戳
		String app_signature = paySignReqHandler.getPaySign();

		//组成json格式
		JSONObject obj = new JSONObject();
		obj.element("appid", appId);
		obj.element("package", packageValue);
		obj.element("timestamp", timeStamp);
		obj.element("app_signature", app_signature);
		obj.element("sign_method", "sha1");

		//http发送查询请求获取返回报文
		String content = WXUtil.post(orderQuery, obj.toString());
		//记录查询返回信息
//		paymentMessageLog(orderPaymentLog.getPaymentLogId(), "response",
//				"query", content, orderPaymentLog.getMemberId(),"N");
		paymentMessageLog(orderPaymentLog, "response","query", content);
		JSONObject jsonObject = JSONObject.fromObject(content);
		String errcode = jsonObject.get("errcode").toString();//如果有异常,会在errmsg 和 errcode 描述出来，如果成功 errcode 就是0
		String errmsg = jsonObject.get("errmsg").toString();
		String order_info = jsonObject.get("order_info").toString();
		JSONObject orderInfoObj = JSONObject.fromObject(order_info);
		String ret_code = orderInfoObj.get("ret_code").toString();//查询结果状态码 0表明成功，其他表明错误；
		String ret_msg = orderInfoObj.get("ret_msg").toString(); //查询结果出错信息
		String total_fee = orderInfoObj.get("total_fee").toString();//总金额
		String trade_state = orderInfoObj.get("trade_state").toString();//0 成功，其他失败
		String transaction_id = orderInfoObj.get("transaction_id").toString();//财付通订单号
		String outTradeNo = orderInfoObj.get("out_trade_no").toString();
		String trade_mode = orderInfoObj.get("trade_mode").toString();

		if(!errcode.equals("0") || !ret_code.equals("0")){
			throw new Exception("微信订单支付状态查询异常，商城支付流水号：" + out_trade_no +
					"微信查询返回码errcode：" + errcode + "微信查询返回错误信息errmsg：" + errmsg +
					"微信查询返回码ret_code：" + ret_code + "微信查询返回错误信息ret_msg：" + ret_msg );
		}

		if(!trade_state.equals("0")){
			throw new Exception("微信订单支付状态查询异常，商城支付流水号：" + out_trade_no +
					"微信订单状态trade_state：" + trade_state);

		}
		PaymentResult pr = new PaymentResult();
		pr.setIsSuccess(ret_code);
		pr.setError(ret_msg);
		pr.setTradeNo(transaction_id);
		pr.setTotalFee(new BigDecimal(total_fee).divide(new BigDecimal(100)).toString());
		pr.setOutTradeNo(outTradeNo);
		pr.setTradeStatus(trade_state);
		pr.setTradeMode(trade_mode);
		pr.setIsDone("0".equals(trade_state) && "1".equals(trade_mode));
		return pr;
	}
	

	@Override
	public Map refund(OrderPaymentLog orderPaymentLog) throws Exception {
		// TODO Auto-generated method stub
		return super.refund(orderPaymentLog);
	}

	/**
	 * 订单退款请求
	 * @param orderPaymentLog
	 * @return PaymentResult
	 */
	@Override
	public PaymentResult refundByOrder(OrderPaymentLog orderPaymentLog) throws Exception{
		return super.refundByOrder(orderPaymentLog);
	}


	public PaymentResult refundQueryByOrder(OrderPaymentLog orderPaymentLog) throws Exception{
        return super.refundQueryByOrder(orderPaymentLog);
	}

	@Override
	public Map refundQuery(OrderPaymentLog orderPaymentLog) throws Exception {
		return super.refundQuery(orderPaymentLog);
	}
}
