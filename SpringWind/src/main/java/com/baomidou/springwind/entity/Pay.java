package com.baomidou.springwind.entity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
/**
 * pay 瀹炰綋绫籠r
 * Wed May 24 21:20:16 CST 2017  Ze Ming
 */

@TableName(value = "pay")
public class Pay  extends BaseEntity implements Serializable{
 private String channel;
 @TableField(value = "return_url")
 private String returnUrl;
 @TableField(value = "callback_url")
 private String callbackUrl;
 private int state;

 public void setChannel(String channel) {
  this.channel = channel;
 }

 public void setReturnUrl(String returnUrl) {
  this.returnUrl = returnUrl;
 }

 public void setCallbackUrl(String callbackUrl) {
  this.callbackUrl = callbackUrl;
 }

 public void setState(int state) {
  this.state = state;
 }

 public String getChannel() {

  return channel;
 }

 public String getReturnUrl() {
  return returnUrl;
 }

 public String getCallbackUrl() {
  return callbackUrl;
 }

 public int getState() {
  return state;
 }
}

