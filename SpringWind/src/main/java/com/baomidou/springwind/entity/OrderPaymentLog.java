package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * order_payment_log
 */

@TableName("order_payment_log")
public class OrderPaymentLog extends BaseEntity implements Serializable{
 @TableField(value = "order_payment_id")
 private long orderPaymentId;
 @TableField(value = "order_payment_param")
 private String orderPaymentParam;
 @TableField(value = "message_type")
 private String messageType;
 private String message;
 @TableField(value = "qr_code")
 private String qrCode;  //是否生成过来二维码
 @TableField(value = "payment_fee")
 private BigDecimal paymentFee;//支付金额（应付）
 @TableField(value = "return_url")
 private String returnUrl; //支付成功后同步返回地址

 private String backState;//返回状态 SUCCESS / FAIL

 @TableField(value = "pay_behavior")
 private int payBehavior; //支付行为 1. 付款 2 退款

 @TableField(value = "return_order_no")
 private String returnOrderNo; //微信支付成功后返回的流水号
 @TableField(value = "pay_state")
 private int payState; //支付状态  0 未支付 1  支付成功 2 支付失败 3 支付中',

 private Map<String,String> payParam; //支付参数


 private Pay pay;




 public void setReturnOrderNo(String returnOrderNo) {
  this.returnOrderNo = returnOrderNo;
 }

 public String getReturnOrderNo() {

  return returnOrderNo;
 }



 public void setOrderPaymentId(long orderPaymentId) {
  this.orderPaymentId = orderPaymentId;
 }

 public void setOrderPaymentParam(String orderPaymentParam) {
  this.orderPaymentParam = orderPaymentParam;
 }



 public void setMessageType(String messageType) {
  this.messageType = messageType;
 }

 public void setMessage(String message) {
  this.message = message;
 }

 public long getOrderPaymentId() {
  return orderPaymentId;
 }

 public String getOrderPaymentParam() {
  return orderPaymentParam;
 }


 public String getMessageType() {
  return messageType;
 }

 public String getMessage() {
  return message;
 }

 public void setQrCode(String qrCode) {
  this.qrCode = qrCode;
 }

 public String getQrCode() {

  return qrCode;
 }

 public void setPayParam(Map<String, String> payParam) {
  this.payParam = payParam;
 }


 @Transient
 public Map<String, String> getPayParam() {

  return payParam;
 }

 public void setPay(Pay pay) {
  this.pay = pay;
 }

@Transient
 public Pay getPay() {

  return pay;
 }

 public void setPaymentFee(BigDecimal paymentFee) {
  this.paymentFee = paymentFee;
 }

 public BigDecimal getPaymentFee() {

  return paymentFee;
 }

 public void setReturnUrl(String returnUrl) {
  this.returnUrl = returnUrl;
 }

 public String getReturnUrl() {

  return returnUrl;
 }

 public void setBackState(String backState) {
  this.backState = backState;
 }

 public String getBackState() {

  return backState;
 }

 public void setPayBehavior(int payBehavior) {
  this.payBehavior = payBehavior;
 }

 public int getPayBehavior() {

  return payBehavior;
 }

 public void setPayState(int payState) {
  this.payState = payState;
 }

 public int getPayState() {

  return payState;
 }
}


