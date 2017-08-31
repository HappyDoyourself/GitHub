package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * order_payment 瀹炰綋绫籠r
 * Wed May 24 21:08:49 CST 2017  Ze Ming
 */

@TableName(value = "order_payment")
public class OrderPayment extends BaseEntity implements Serializable{

 @TableField(value = "order_id")
 private long orderId;
 @TableField(value = "send_order_id")
 private long sendOrderId;
 @TableField(value = "pay_amount")
 private BigDecimal payAmount; //应付金额
 @TableField(value = "paidAmount")
 private BigDecimal paid_amount; //实付金额
 @TableField(value = "pay_channel")
 private int payChannel;
 @TableField(value = "pay_state")
 private int payState;
 @TableField(value = "pay_type")
 private int payType;
 @TableField(value = "pay_ip")
 private String payIp;
 @TableField(value = "pay_trade_type")
 private String payTradeType;
 @TableField(value = "success_trade_no")
 private String successTradeNo;
 @TableField(value = "pay_part")
 private int payPart;


 public void setOrderId(long orderId) {
  this.orderId = orderId;
 }

 public void setSendOrderId(long sendOrderId) {
  this.sendOrderId = sendOrderId;
 }

 public void setPayChannel(int payChannel) {
  this.payChannel = payChannel;
 }

 public void setPayState(int payState) {
  this.payState = payState;
 }

 public void setPayType(int payType) {
  this.payType = payType;
 }

 public void setPayIp(String payIp) {
  this.payIp = payIp;
 }

 public void setPayTradeType(String payTradeType) {
  this.payTradeType = payTradeType;
 }

 public void setSuccessTradeNo(String successTradeNo) {
  this.successTradeNo = successTradeNo;
 }

 public void setPayPart(int payPart) {
  this.payPart = payPart;
 }


 public long getOrderId() {
  return orderId;
 }

 public long getSendOrderId() {
  return sendOrderId;
 }

 public int getPayChannel() {
  return payChannel;
 }

 public int getPayState() {
  return payState;
 }

 public int getPayType() {
  return payType;
 }

 public String getPayIp() {
  return payIp;
 }

 public String getPayTradeType() {
  return payTradeType;
 }

 public String getSuccessTradeNo() {
  return successTradeNo;
 }

 public int getPayPart() {
  return payPart;
 }

 public void setPayAmount(BigDecimal payAmount) {
  this.payAmount = payAmount;
 }

 public BigDecimal getPayAmount() {

  return payAmount;
 }

 public void setPaid_amount(BigDecimal paid_amount) {
  this.paid_amount = paid_amount;
 }

 public BigDecimal getPaid_amount() {

  return paid_amount;
 }
}

