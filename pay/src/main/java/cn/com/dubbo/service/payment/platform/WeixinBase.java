package cn.com.dubbo.service.payment.platform;

import cn.com.dubbo.model.EcPaymentType;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.PaymentResult;
import cn.com.dubbo.model.WeixinPay;
import cn.com.jiuyao.pay.common.constant.ResponseCodeConstants;
import cn.com.jiuyao.pay.common.util.HttpClientUtils;
import cn.com.jiuyao.pay.common.util.HttpsClientUtils;
import cn.com.jiuyao.pay.common.util.MathUtil;
import cn.com.jiuyao.util.IPUtil;
import cn.com.jiuyao.util.payments.weixin.*;
import com.alibaba.fastjson.JSONObject;
import org.jdom.JDOMException;
import org.jdom.input.JDOMParseException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信支付基础类
 * @author jinjin
 *
 */
@Service
public class WeixinBase extends PlatformPayment{

	/**
	 * 获取微信支付基本参数
	 * @param request
	 * @param orderPaymentLog
	 * @param trade_type 微信支付固定参数，视支付方式而定
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public WeixinPay getParas(HttpServletRequest request,OrderPaymentLog orderPaymentLog,String trade_type) throws UnsupportedEncodingException{
		WeixinPay pay = new WeixinPay();
		Map<String, String> parameter = orderPaymentLog.getEcPaymentTypeParames();
		/*******************************************************获取支付参数*********************************************/
		String out_trade_no = orderPaymentLog.getPaymentNo();
		String appId = parameter.get("appId");// 微信开发平台应用id
		pay.setAppId(appId);
		String appKey = parameter.get("appKey");
		pay.setAppKey(appKey);
		String mch_id = parameter.get("mch_id");
		pay.setMch_id(mch_id);
		String partnerId = parameter.get("partnerId");
		pay.setPartnerId(partnerId);
		String notify_url = orderPaymentLog.getEcPaymentType().getNotifyUrl();//异步通知url
		//body内容
		String body =  out_trade_no;
//		body= new String(body.getBytes("utf-8"),"iso8859-1");
		pay.setBody(body);
		//随机字符串
		String nonce_str = TenpayUtil.getNonceStr();
		pay.setNoncestr(nonce_str);
		// 订单总价（单位：分）
		String total_fee = orderPaymentLog.getPaymentFee().multiply(new BigDecimal(100)).setScale(0).toString();
		pay.setTotal_fee(total_fee);
		//支付机器IP
		String ip = IPUtil.getIpAddr(request);
		pay.setIp(ip);
		//生成支付请求sign值
		Map<String, String> mapPara = new LinkedHashMap<String, String>();
		mapPara.put("appid", appId);
		mapPara.put("body", body);//商品描述
		mapPara.put("mch_id", mch_id);
		mapPara.put("nonce_str", nonce_str);
		mapPara.put("notify_url", notify_url);
		if (trade_type != null && "JSAPI".equals(trade_type)){
			mapPara.put("openid",request.getParameter("openid"));
		}
		mapPara.put("out_trade_no", out_trade_no);
		mapPara.put("spbill_create_ip", ip);//支付机器IP
		mapPara.put("total_fee", total_fee);//商品总金额,以分为单位
		mapPara.put("trade_type",trade_type);
		pay.setMapPara(mapPara);
		return pay;
	}
	
	/**
	 * 创建微信支付订单（统一下单接口）,方法内按微信规范签名，无需传签名参数
	 * @param orderPaymentLog 支付对象
	 * @param mapPara 微信接口参数（参考对应微信文档）
	 * @param appKey 应用key
	 * @return 解析XML后转为map获取返回结果
	 * @throws JDOMParseException 返回结果解析错误
	 */
	public Map<String,String> createWeiXinOrder(OrderPaymentLog orderPaymentLog,Map<String,String> mapPara,String appKey) throws JDOMParseException{
		Map<String,String> map = new HashMap<String, String>();
		try {
			String paySign = WXUtil.createSign(mapPara, appKey);
			mapPara.put("sign", paySign);
			//响应post数据给微信
			String weiXinXml = XMLUtil.getXmlForMap(mapPara);
			//记录请求日志
			paymentMessageLog(orderPaymentLog, "request","init", weiXinXml);
			String content="";
			try {
				content = HttpClientUtils.do_post("https://api.mch.weixin.qq.com/pay/unifiedorder", weiXinXml);
				//content = HttpClientUtils.doPost("https://api.mch.weixin.qq.com/pay/unifiedorder", mapPara);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//记录响应日志
			paymentMessageLog(orderPaymentLog, "response","initResponse", content);
			
			map = XMLUtil.doXMLParse(content);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(JDOMException jdom){
			jdom.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 微信支付通知接口
	 * @param request
	 * @param response
	 * @param paymentTypeNo
	 * @return
	 */
	public String baseNotifyMessage(HttpServletRequest request,
			HttpServletResponse response,String paymentTypeNo){
		try {
			// 取得支付参数
			EcPaymentType ecPaymentType = myPayService.findPaymentInfoByNo(paymentTypeNo);
			Map<String,String> parameter = myPayService.findPaymentTypeListInfo(ecPaymentType.getPaymentTypeId());
			String partnerKey = parameter.get("appKey");
			String appKey = parameter.get("appKey");

			// 获取收到的报文
			String xmlContent = HttpClientUtil.InputStreamTOString(request.getInputStream(), TenpayUtil.getCharacterEncoding(request, response));
			
			//解析xml,得到map
			Map<String,String> map = XMLUtil.doXMLParse(xmlContent);
			String AppSignature = map.get("sign").toString();//参数的加密签名

			//财付通支付应答（处理回调）示例，商户按照此文档进行开发即可
			ResponseHandler resHandler = new ResponseHandler(request, response);
			resHandler.setKey(partnerKey);

			/** GET 接收财付通的数据*/
			Map requestParameterMap = request.getParameterMap();

			//判断签名
			String enc = TenpayUtil.getCharacterEncoding(request, response);//算出摘要
			if(!(WXUtil.isWeixinSign(map, AppSignature, appKey))) {
				logger.error("=====weixin====通知签名验证失败");
				resHandler.sendToCFT("fail");
				return null;
			}
			//支付返回值
			String trade_state = map.get("return_code");//交易状态 SUCCESS/FAIL
			String trade_mode = map.get("result_code");//业务结果
			String total_fee = map.get("total_fee");//通知的 total_fee+ discount = 请求的total_fee
			String out_trade_no =map.get("out_trade_no");//商户系统的订单号
			String transaction_id = map.get("transaction_id");//交易号， 28 位长的数值， 其中前 10 位为商户号， 之后 8 位为订单产生的日 期，如 20090415，最后10 位是流水号。
			BigDecimal totalFee = new BigDecimal(total_fee);

			// 获取订单日志信息和订单信息
			OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
			orderPaymentLog.setPaymentNo(out_trade_no);
			orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
			orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog,false);
			// 拼接记录order_payment_message_log
			String message = getContentByParameterMap(request.getParameterMap());
			paymentMessageLog(orderPaymentLog, "response","notify", message);
			// 订单有效性校验
			String be = checkPamentBackBeforeSign(request, orderPaymentLog);
			if (!be.equals("")) {
				resHandler.sendToCFT("success");
				return "";
			}

			//判断签名及结果
			BigDecimal realAmt = totalFee.divide(new BigDecimal(100));
			orderPaymentLog.setBackState(trade_state);
			orderPaymentLog.setBackNo(transaction_id);
			orderPaymentLog.setPaidFee(realAmt);
			if ("SUCCESS".equals(trade_state) && "SUCCESS".equals(trade_mode)) {
				doSaveResultByLog(orderPaymentLog);
				//调用查询接口修改orderPaymentLog查询状态
				orderPaymentLog.setEcPaymentTypeParames(parameter);
				orderPaymentLog.setEcPaymentType(ecPaymentType);
				
			} else {
				doSaveErrResultByLog(orderPaymentLog);
			}
			resHandler.sendToCFT("success");
			//返回处理完成
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>"); 
			response.getWriter().flush();
			response.getWriter().close();
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 订单退款请求
	 * @param orderPaymentLog
	 * @return PaymentResult
	 */
	@Override
	public PaymentResult refundByOrder(OrderPaymentLog orderPaymentLog) throws Exception{
		//获取微信商户支付参数
		Map<String, String> parameter =  orderPaymentLog.getEcPaymentTypeParames();
		String appId = parameter.get("appId"); //商户号
		String mch_id = parameter.get("mch_id"); //商户号
		String nonce_str = TenpayUtil.getNonceStr(); //随机字符串
		//String out_trade_no = orderPaymentLog.getOrderPaymentLog().getPaymentNo();//商户订单号
		String out_trade_no = orderPaymentLog.getBusinessId().toString(); //商户订单号
		String transaction_id = orderPaymentLog.getOrderPaymentLog().getBackNo(); //财付通订单号
		String out_refund_no = orderPaymentLog.getBusinessId().toString(); //商户退款单号
		String total_fee = (int) (Math.round(Double.parseDouble(orderPaymentLog.getOrderPaymentLog()
				.getPaymentFee().toString()) * 100)) + ""; //总金额 分
		String refund_fee = (int) (Math.round(Double.parseDouble(orderPaymentLog.getPaymentFee()
				.toString()) * 100)) + "";//退款金额 分
		String op_user_id = mch_id;//操作员
		Map mapPara = new LinkedHashMap();
		mapPara.put("appid", appId);
		mapPara.put("mch_id", mch_id);
		mapPara.put("nonce_str", nonce_str);
		mapPara.put("op_user_id", op_user_id);
		mapPara.put("out_refund_no", out_refund_no);
		mapPara.put("out_trade_no", out_trade_no);
		//可用余额退款
		mapPara.put("refund_account", "REFUND_SOURCE_RECHARGE_FUNDS");
		mapPara.put("refund_fee", refund_fee);
		mapPara.put("total_fee", total_fee);
		mapPara.put("transaction_id", transaction_id);

		
		//-----------------------------
		//加密参数
		//-----------------------------
		String key = parameter.get("appKey");//密钥
		String cacert = parameter.get("cacert");//ca证书路径
		String pfx = parameter.get("pfx");//个人(商户)证书
		/*加密*/
		String paySign = WXUtil.createSign(mapPara, key);
		mapPara.put("sign", paySign);


		//-----------------------------
		//设置请求参数
		//-----------------------------
		String weiXinXml = XMLUtil.getXmlForMap(mapPara);
		paymentMessageLog(orderPaymentLog, "request","init", weiXinXml);
		Map<String,String> map = HttpsClientUtils.doPost("https://api.mch.weixin.qq.com/secapi/pay/refund", pfx, mch_id, weiXinXml);
		paymentMessageLog(orderPaymentLog, "response","initResponse", XMLUtil.getXmlForMap(map));
//		
//		Map<String,String> map = XMLUtil.doXMLParse(content);
		
		//-----------------------------
		//封装返回信息
		//-----------------------------
		PaymentResult paymentResult = new PaymentResult();
		if ("SUCCESS".equals(map.get("return_code")) && map.get("refund_fee") != null) {//成功
			paymentResult.setTradeNo(map.get("refund_id"));//退款流水号
			paymentResult.setRefundFee(MathUtil.changeF2Y(new BigDecimal(map.get("refund_fee").toString()).setScale(2,BigDecimal.ROUND_HALF_UP)).toString());//退款金额
			paymentResult.setRefundStatus(ResponseCodeConstants.REFUND_APPLIED_STATE);//退款状态
			paymentResult.setIsDone(true);
		}else{//失败
			String msg = map.get("err_code_des");
			paymentResult.setRefundStatus(msg);//退款状态
			paymentResult.setRefundFee("0");//退款金额
			paymentResult.setIsDone(false);
		}
		return paymentResult;
	}

	@Override
	public Object refundNotify(HttpServletRequest request, HttpServletResponse response, String paymentTypeNo) throws Exception {
		return null;
	}

	/**
	 * 订单退款查询
	 * @param orderPaymentLog
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map refundQuery(OrderPaymentLog orderPaymentLog) throws Exception {
		Map<String, Object> orderInfoMap = new HashMap<String, Object>();
		JSONObject jsonObject = new JSONObject();
		try {
			Map<String, String> parameter = myPayService
					.findPaymentTypeListInfo(orderPaymentLog.getPaymentTypeId());
			EcPaymentType ecPaymentType = myPayService.
					findPaymentInfoById(orderPaymentLog.getPaymentTypeId().toString());
			orderPaymentLog.setEcPaymentTypeParames(parameter);
			orderPaymentLog.setEcPaymentType(ecPaymentType);
			PaymentResult paymentResult = (PaymentResult)refundQueryByOrder(orderPaymentLog);
			if (paymentResult == null) {
				jsonObject.put("code","0010");
				orderInfoMap.put("result",jsonObject.toString());
				return orderInfoMap;
			}
			//退款已申请状态
			if(ResponseCodeConstants.REFUND_APPLIED_STATE.equals(paymentResult.getRefundStatus())){
				jsonObject.put("code","0000");
				orderInfoMap.put("result",jsonObject.toString());
			} else {
				jsonObject.put("code","0020");
				orderInfoMap.put("result",jsonObject.toString());
			}
			return orderInfoMap;
		} catch (Exception e) {
			logger.error("=== An exception occurred while weiXinQueryRefunds,and the businessId is " + orderPaymentLog.getBusinessId());
			e.printStackTrace();
			orderInfoMap.put("result","0030");
			orderInfoMap.put("refundAmt","0");
		}

		return orderInfoMap;
	}


	/**
	 * 订单退款查询请求
	 * @param orderPaymentLog
	 * @return PaymentResult
	 */
	public PaymentResult refundQueryByOrder(OrderPaymentLog orderPaymentLog) throws Exception{
		//获取微信商户支付参数
		Map<String, String> parameter =  orderPaymentLog.getEcPaymentTypeParames();
		String appId = parameter.get("appId"); //商户号
		String mch_id = parameter.get("mch_id"); //商户号
		String nonce_str = TenpayUtil.getNonceStr(); //随机字符串
		String out_refund_no = orderPaymentLog.getBusinessId().toString(); //商户退款单号
		Map mapPara = new LinkedHashMap();
		mapPara.put("appid", appId);
		mapPara.put("mch_id", mch_id);
		mapPara.put("nonce_str", nonce_str);
		//下面4个选一个即可
		mapPara.put("transaction_id", ""); //微信订单号
		mapPara.put("out_trade_no", ""); //商户订单号
		mapPara.put("out_refund_no", out_refund_no); //商户退款单号
		mapPara.put("refund_id", "");  //微信退款单号
		//-----------------------------
		//加密参数
		//-----------------------------
		String key = parameter.get("appKey");//密钥
		/*加密*/
		String paySign = WXUtil.createSign(mapPara, key);
		mapPara.put("sign", paySign);


		//-----------------------------
		//设置请求参数
		//-----------------------------
		String weiXinXml = XMLUtil.getXmlForMap(mapPara);
		paymentMessageLog(orderPaymentLog, "request","init", weiXinXml);
		String resData1 = HttpClientUtils.do_post("https://api.mch.weixin.qq.com/pay/refundquery", weiXinXml);
		String resData =  new String(resData1 .getBytes("GB2312"),"utf-8"); //挺奇怪的是： 用单元测试查询返回正常的中文
		paymentMessageLog(orderPaymentLog, "response","initResponse", resData);

		//-----------------------------
		//封装返回信息
		//-----------------------------
		PaymentResult paymentResult = new PaymentResult();
		Map xmlMap =XMLUtil.doXMLParse(resData);
		if ("SUCCESS".equals(xmlMap.get("return_code").toString())) { //成功
			if("SUCCESS".equals(xmlMap.get("refund_status_0").toString())){
				paymentResult.setRefundStatus(ResponseCodeConstants.REFUND_APPLIED_STATE);//退款状态
				paymentResult.setIsDone(true);
			}
		}else{//失败
			String msg = xmlMap.get("return_code").toString();
			logger.info("weiXin refundQuery error,orderNo" + orderPaymentLog.getPaymentNo());
			paymentResult.setIsDone(false);
		}
		return paymentResult;
	}
}
