package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;

import java.util.Date;

/**
 * Created by fht on 2017-05-24 21:23.
 */
public class BaseEntity {
    /** 主键 */
    @TableId
    private long id;
    @TableField(value = "add_time")
    private Date addTime;
    @TableField(value = "update_time")
    private Date updateTime;

    public void setId(long id) {
        this.id = id;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getId() {

        return id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }
}
