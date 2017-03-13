package cn.com.dubbo.service.payment.platform;

import cn.com.jiuyao.util.IPUtil;
import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.WeixinPay;
import cn.com.jiuyao.util.payments.weixin.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * weixin 微信APP支付
 * @author jinjin
 * @version 2016年11月21日
 */
@Service
public class Weixin extends WeixinBase implements Platform { 
 
	@Override
	public String requestMessagePackage(HttpServletRequest request,
			HttpServletResponse response,  OrderPaymentLog orderPaymentLog) {
		String message = "";
		try {
			/*******************************************************获取支付参数*********************************************/
		    //如果是微信小程序 需要传trade_type 值如下：JSAPI

			WeixinPay pay = getParas(request, orderPaymentLog,"APP");
			
			/*******************************************************创建微信统一下单接口的订单*********************************************/
			Map<String,String> map =createWeiXinOrder(orderPaymentLog, pay.getMapPara(), pay.getAppKey());
			if (map == null || map.size() == 0) {
				return "";
			}
			
			//生成时间戳
			String timeStamp = TenpayUtil.getTimeStamp();
			//返回给app端
			JSONObject jb = new JSONObject();
			JSONObject data = new JSONObject();
			
			//将url传到jsp中，异步生产二维码
			if ("SUCCESS".equals(map.get("return_code"))) {
				String prepay_id = map.get("prepay_id");
				jb.element("status", 0);
				jb.element("msg", "OK");
				data.element("appid", pay.getAppId());
				data.element("partnerid", pay.getPartnerId());
				data.element("prepayid", prepay_id);
				data.element("package", "Sign=WXPay");
				data.element("timestamp", timeStamp);
				data.element("noncestr", pay.getNoncestr());
				data.element("package", "Sign=WXPay");
				//增加签名
				Map<String,String> reMap = new HashMap<String,String>();
				reMap.put("appid", pay.getAppId());
				reMap.put("partnerid", pay.getPartnerId());
				reMap.put("prepayid", prepay_id);
				reMap.put("timestamp", timeStamp);
				reMap.put("noncestr", pay.getNoncestr());
				reMap.put("package", "Sign=WXPay");
				String paySign = WXUtil.createSign(reMap, "00ae0ff5b32f35b1aa17a6713f25bf03");
				data.element("sign", paySign);
				jb.element("data", data);
			}else {
				return "";
			}
			StringBuilder str = new StringBuilder();
			String callback = request.getParameter("callback");
			if (callback != null && !"".equals(callback)) {
				str.append(callback);
			}
			str.append(jb.toString());
			
			message = jb.toString();
			response.resetBuffer();
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(str); 
			response.getWriter().flush();
			response.getWriter().close();
			response.flushBuffer();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return message;
	}

	/**
	 *  同步通知
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
		baseNotifyMessage(request, response, paymentTypeNo);
		return null;
	}
 
}
