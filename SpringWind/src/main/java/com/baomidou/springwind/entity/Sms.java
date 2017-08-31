package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * sms
 * Thu May 25 20:39:53 CST 2017  Ze Ming
 */

@TableName(value = "sms")
public class Sms extends BaseEntity implements Serializable{
 private String phone;
 private String message;
 @TableField(value = "verify_code")
 private String verifyCode;
 @TableField(value = "sms_state")
 private int smsState;
 @TableField(value = "send_name")
 private String sendName;



 public void setMessage(String message) {
  this.message = message;
 }

 public void setVerifyCode(String verifyCode) {
  this.verifyCode = verifyCode;
 }

 public void setSmsState(int smsState) {
  this.smsState = smsState;
 }

 public void setPhone(String phone) {
  this.phone = phone;
 }

 public String getPhone() {

  return phone;
 }

 public String getMessage() {
  return message;
 }

 public String getVerifyCode() {
  return verifyCode;
 }

 public int getSmsState() {
  return smsState;
 }

 public String getSendName() {
  return sendName;
 }

 public void setSendName(String sendName) {
  this.sendName = sendName;
 }
}

