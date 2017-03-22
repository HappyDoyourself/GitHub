package com.ninedrug.search.business.base;

public class ServiceMessagesImpl<T> implements ServiceMessages<T> {
	
	/**
	 * 状态码
	 */
	public long code;
	
	/**
	 * 详细信息
	 */
	public String msg;
	
	/**
	 * 执行结果 
	 */
	public T result;
	
	
	public ServiceMessagesImpl(long code, String msg, T result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}


	public long getCode() {
		return code;
	}


	public void setCode(long code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}

	public void setMessage(String msg) {
		this.msg = msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
	
	
	
}
