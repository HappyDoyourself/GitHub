package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * Created by fht on 2017-05-25 20:44.
 */
@TableName(value = "black_mobile")
public class BlackMobile extends BaseEntity implements Serializable{
    private String mobile;
    @TableField(value = "add_user")
    private String addUser;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getMobile() {

        return mobile;
    }

    public String getAddUser() {
        return addUser;
    }
}
