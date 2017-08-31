package com.baomidou.springwind.entity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * activity �
 * Fri May 05 21:14:48 CST 2017  Ze Ming
 */

@TableName(value = "activity")
public class Activity implements Serializable{
 @TableId
 private Long id;
 private String title;
 private int tag;
 private String content;
 @TableField(value = "begin_time")
 private Date beginTime;
 @TableField(value = "end_time")
 private Date endTime;
 private int state; // 1  进行中  2 已结束,默认1
 private String city;
 @TableField(value = "add_time")
 private Date addTime;
 @TableField(value = "update_time")
 private Date updateTime;
 private int places; //名额
 private BigDecimal deposit; //订金 单位元
 @TableField(value = "full_price")
 private BigDecimal fullPrice;
@TableField(value = "detail_address")
 private String detailAddress;
private  String pic; //活动图片

 public void setId(Long id) {
  this.id = id;
 }

 public Long getId() {

  return id;
 }

 public void setTitle(String title){
 this.title=title;
 }
 public String getTitle(){
     return title;
 }
 public void setTag(int tag){
 this.tag=tag;
 }
 public int getTag(){
     return tag;
 }
 public void setContent(String content){
 this.content=content;
 }
 public String getContent(){
     return content;
 }

 public void setState(int state){
 this.state=state;
 }
 public int getState(){
     return state;
 }
 public void setCity(String city){
 this.city=city;
 }
 public String getCity(){
     return city;
 }
 public void setPlaces(int places){
 this.places=places;
 }
 public int getPlaces(){
     return places;
 }

 public void setDeposit(BigDecimal deposit) {
  this.deposit = deposit;
 }

 public void setFullPrice(BigDecimal fullPrice) {
  this.fullPrice = fullPrice;
 }

 public BigDecimal getDeposit() {

  return deposit;
 }

 public BigDecimal getFullPrice() {
  return fullPrice;
 }

 public Date getBeginTime() {
  return beginTime;
 }

 public Date getEndTime() {
  return endTime;
 }

 public Date getAddTime() {
  return addTime;
 }

 public Date getUpdateTime() {
  return updateTime;
 }

 public void setBeginTime(Date beginTime) {
  this.beginTime = beginTime;
 }

 public void setEndTime(Date endTime) {
  this.endTime = endTime;
 }

 public void setAddTime(Date addTime) {
  this.addTime = addTime;
 }

 public void setUpdateTime(Date updateTime) {
  this.updateTime = updateTime;
 }

 public void setDetailAddress(String detailAddress) {
  this.detailAddress = detailAddress;
 }

 public String getDetailAddress() {

  return detailAddress;
 }

 public void setPic(String pic) {
  this.pic = pic;
 }

 public String getPic() {

  return pic;
 }
}

