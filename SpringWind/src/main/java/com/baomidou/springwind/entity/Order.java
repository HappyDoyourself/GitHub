package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by fht on 2017-05-24 21:27.
 */
@TableName(value = "order_info")
public class Order extends BaseEntity implements Serializable{
    @TableField(value = "user_name")
    private String userName;
    @TableField(value = "activity_id")
    private Long activityId;
    @TableField(value = "order_type")
    private int orderType;//支付方式：1 全额支付 2 订金支付
    private String phone;
    @TableField(value = "order_amount")
    private BigDecimal orderAmount;
    @TableField(value = "order_state")
    private int orderState;
    @TableField(value = "pay_type")
    private int payType;

    @TableField(value = "open_id")
    private  String openId;
    //是否给管理员发送过通知短信 -1 未发送 0 发送失败 1 发送成功
    @TableField(value = "sms_state")
    private int smsState;
    //'活动标签：1 招募、2 预定、3 抢票、4 报名',
    private int tag;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public String getUserName() {

        return userName;
    }

    public Long getActivityId() {
        return activityId;
    }

    public int getOrderType() {
        return orderType;
    }

    public String getPhone() {
        return phone;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getPayType() {

        return payType;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOpenId() {

        return openId;
    }

    public void setSmsState(int smsState) {
        this.smsState = smsState;
    }

    public int getSmsState() {

        return smsState;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
