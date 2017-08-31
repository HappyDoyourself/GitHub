package com.baomidou.springwind.entity;

import java.util.Map;

/**
 * 微信支付参数初始化对象
 *
 */
public class WeixinPay {
	
	private String appId;
	
	private String appKey;
	
	private String mch_id;
	
	private String partnerId;
	
	private String body;
	
	private String noncestr;
	
	private String total_fee;
	
	private String ip;
	
	private Map<String,String> mapPara;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public Map<String, String> getMapPara() {
		return mapPara;
	}

	public void setMapPara(Map<String, String> mapPara) {
		this.mapPara = mapPara;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
