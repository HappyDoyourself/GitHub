package cn.com.jiuyao.pay.common.util;

import java.io.Serializable;

/**
 * java后台接口
 */
public class BaseObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 200：返回正确
	 * 
	 */
	private int code;
	private Object obj;//接口返回的具体内容
	private String msg;//成功或错误的描述
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
