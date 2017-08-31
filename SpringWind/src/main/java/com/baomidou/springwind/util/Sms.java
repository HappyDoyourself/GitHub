package com.baomidou.springwind.util;
import java.util.Date;
   /**
    * sms 瀹炰綋绫籠r
    * Mon Jun 05 20:53:11 CST 2017  Ze Ming
    */ 


public class Sms{
	private long id;
	private int phone;
	private String message;
	private String verify_code;
	private Date add_time;
	private double update_time;
	private int sms_state;
	private String send_name;
	public void setId(long id){
	this.id=id;
	}
	public long getId(){
		return id;
	}
	public void setPhone(int phone){
	this.phone=phone;
	}
	public int getPhone(){
		return phone;
	}
	public void setMessage(String message){
	this.message=message;
	}
	public String getMessage(){
		return message;
	}
	public void setVerify_code(String verify_code){
	this.verify_code=verify_code;
	}
	public String getVerify_code(){
		return verify_code;
	}
	public void setAdd_time(Date add_time){
	this.add_time=add_time;
	}
	public Date getAdd_time(){
		return add_time;
	}
	public void setUpdate_time(double update_time){
	this.update_time=update_time;
	}
	public double getUpdate_time(){
		return update_time;
	}
	public void setSms_state(int sms_state){
	this.sms_state=sms_state;
	}
	public int getSms_state(){
		return sms_state;
	}
	public void setSend_name(String send_name){
	this.send_name=send_name;
	}
	public String getSend_name(){
		return send_name;
	}
}

