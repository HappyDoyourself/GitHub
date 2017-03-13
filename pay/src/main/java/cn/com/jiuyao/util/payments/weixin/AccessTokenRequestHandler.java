package cn.com.jiuyao.util.payments.weixin;

import cn.com.jiuyao.util.payments.weixin.client.TenpayHttpClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessTokenRequestHandler extends RequestHandler {

	public AccessTokenRequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}

	private static String access_token = "";

	/**
	 * 获取凭证access_token
	 * @return
	 */
	public static String getAccessToken(PayData data) {
		if ("".equals(access_token)) {// 如果为空直接获取
			return getTokenReal(data);
		}

		if (tokenIsExpire(access_token,data)) {// 如果过期重新获取
			return getTokenReal(data);
		}
		return access_token;
	}

	/**
	 * 获取凭证access_token
	 * @return
	 */
	public static String getWxPayAccessToken(PayData data) {
		if ("".equals(data.getAccessToken())) {// 如果为空直接获取
			return getTokenReal(data);
		}

		if (tokenIsExpire(data.getAccessToken(),data)) {// 如果过期重新获取
			return getTokenReal(data);
		}
		return data.getAccessToken();
	}

	/**
	 * 实际获取access_token的方法
	 * @return
	 */
	protected static String getTokenReal(PayData data) {
		String requestUrl = data.getTokenUrl() + "?grant_type=" + data.getGrantType() + "&appid="
				+ data.getAppId() + "&secret=" + data.getAppSecret();
		String resContent = "";
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setMethod("GET");
		httpClient.setReqContent(requestUrl);
		if (httpClient.call()) {
			resContent = httpClient.getResContent();
			if (resContent.indexOf("access_token") > 0) {
				access_token = JsonUtil.getJsonValue(resContent, "access_token");
			} else {
				System.out.println("获取access_token值返回错误！！！");
			}
		} else {
			System.out.println("后台调用通信失败");
			System.out.println(httpClient.getResponseCode());
			System.out.println(httpClient.getErrInfo());
			// 有可能因为网络原因，请求已经处理，但未收到应答。
		}

		return access_token;
	}

	/**
	 * 判断传递过来的参数access_token是否过期
	 * @param access_token
	 * @return
	 */
	private static boolean tokenIsExpire(String access_token,PayData data) {
		boolean flag = false;
		PrepayIdRequestHandler wxReqHandler = new PrepayIdRequestHandler(null, null);
		wxReqHandler.setParameter("appid", data.getAppId());
		wxReqHandler.setParameter("appkey",data.getAppKey());
		wxReqHandler.setParameter("noncestr", WXUtil.getNonceStr());
		wxReqHandler.setParameter("package", data.getPackageValue());
		wxReqHandler.setParameter("timestamp", WXUtil.getTimeStamp());
		wxReqHandler.setParameter("traceid", data.getTraceid());

		// 生成支付签名
		String sign = wxReqHandler.createSHA1Sign();
		wxReqHandler.setParameter("app_signature", sign);
		wxReqHandler.setParameter("sign_method","sha1");
		String gateUrl = data.getGateUrl() + access_token;
		wxReqHandler.setGateUrl(gateUrl);

		// 发送请求
		String accesstoken = wxReqHandler.sendAccessToken();
		if (!data.getExpireErrCode().equals(accesstoken) && !data.getFailErrCode().equals(accesstoken))
			flag = true;
		return flag;
	}

}
