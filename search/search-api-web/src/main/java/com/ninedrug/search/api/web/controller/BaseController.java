package com.ninedrug.search.api.web.controller;

import com.ninedrug.search.business.base.MsgStatus;
import com.ninedrug.search.business.base.ServiceMessages;
import com.ninedrug.search.business.base.ServiceMessagesImpl;

public class BaseController<T> {
	
	
	public ServiceMessages<T> returnSuc(String message, T result){
		ServiceMessages<T> smg = new ServiceMessagesImpl<T>(MsgStatus.SUC.getCode(),  message,  result);
		return smg;
	}
	
	public ServiceMessages<T> returnSuc(T result){
		ServiceMessages<T> smg = new ServiceMessagesImpl<T>(MsgStatus.SUC.getCode(),  MsgStatus.SUC.getName(),  result);
		return smg;
	}
	
	public ServiceMessages<T> returnError(String message, T result){
		ServiceMessages<T> smg = new ServiceMessagesImpl<T>(MsgStatus.ERROR.getCode(),  message,  result);
		return smg;
	}
	
	public ServiceMessages<T> returnError(T result){
		ServiceMessages<T> smg = new ServiceMessagesImpl<T>(MsgStatus.ERROR.getCode(),  MsgStatus.ERROR.getName(),  result);
		return smg;
	}
	
	public ServiceMessages<T> returnErrorParam(String message, T result){
		ServiceMessages<T> smg = new ServiceMessagesImpl<T>(MsgStatus.ERROR_PARAMS.getCode(),  message,  result);
		return smg;
	}
	
	public ServiceMessages<T> returnErrorParam(T result){
		ServiceMessages<T> smg = new ServiceMessagesImpl<T>(MsgStatus.ERROR_PARAMS.getCode(),  MsgStatus.ERROR_PARAMS.getName(),  result);
		return smg;
	}
	
	
}
