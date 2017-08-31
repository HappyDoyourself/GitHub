package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
    * pay_param 瀹炰綋绫籠r
    * Wed May 24 21:32:13 CST 2017  Ze Ming
    */ 

@TableName(value = "pay_param")
public class PayParam extends BaseEntity implements Serializable{
	@TableField(value = "pay_id")
	private long payId;
	@TableField(value = "pay_param_key")
	private String payParamKey;
	@TableField(value = "pay_param_value")
	private String payParamValue;

	public void setPayId(long payId) {
		this.payId = payId;
	}

	public void setPayParamKey(String payParamKey) {
		this.payParamKey = payParamKey;
	}

	public void setPayParamValue(String payParamValue) {
		this.payParamValue = payParamValue;
	}

	public long getPayId() {
		return payId;
	}

	public String getPayParamKey() {
		return payParamKey;
	}

	public String getPayParamValue() {
		return payParamValue;
	}
}

