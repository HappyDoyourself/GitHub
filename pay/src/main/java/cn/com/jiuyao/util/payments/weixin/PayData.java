package cn.com.jiuyao.util.payments.weixin;

public class PayData {
	public String appId;// 微信开发平台应用id
	public String appSecret;// 应用对应的凭证
	public String appKey;
	public String partner;// 财付通商户号
	public String partnerKey;// //商户号对应的密钥
	public String tokenUrl;// 获取access_token对应的url
	public String grantType;// 常量固定值
	public String expireErrCode;//access_token失效后请求返回的errcode
	public String failErrCode;//重复获取导致上一次获取的access_token失效,返回错误码
	public String gateUrl;// 获取预支付id的接口url
	public String accessToken;// access_token常量值
	public String signMethod;// 签名算法常量值
	public String packageValue ;
	public String traceid ;//用户id
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getGateUrl() {
		return gateUrl;
	}

	public void setGateUrl(String gateUrl) {
		this.gateUrl = gateUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	public String getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

	public String getTraceid() {
		return traceid;
	}

	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}

	public String getExpireErrCode() {
		return expireErrCode;
	}

	public void setExpireErrCode(String expireErrCode) {
		this.expireErrCode = expireErrCode;
	}

	public String getFailErrCode() {
		return failErrCode;
	}

	public void setFailErrCode(String failErrCode) {
		this.failErrCode = failErrCode;
	}

}
