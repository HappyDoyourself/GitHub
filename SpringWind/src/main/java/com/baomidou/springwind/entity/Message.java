package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;


/**
 * message
 * Wed May 24 20:55:09 CST 2017  Ze Ming
 */

@TableName("message")
public class Message extends BaseEntity implements Serializable{


    private String phone;
    @TableField(value = "verification_code")
    private String verificationCode;
    private int type;
    @TableField(value = "isblack_phone")
    private int isblackPhone;



    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


    public void setType(int type) {
        this.type = type;
    }

    public void setIsblackPhone(int isblackPhone) {
        this.isblackPhone = isblackPhone;
    }

    public String getPhone() {
        return phone;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public int getType() {
        return type;
    }

    public int getIsblackPhone() {
        return isblackPhone;
    }
}

