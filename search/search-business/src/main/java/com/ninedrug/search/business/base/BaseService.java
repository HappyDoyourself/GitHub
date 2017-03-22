package com.ninedrug.search.business.base;

public interface BaseService<T> {
	
	public ServiceMessages<T> returnSuc(String message, T result);
	
	public ServiceMessages<T> returnSuc(T result);
	
	public ServiceMessages<T> returnError(String message, T result);
	
	public ServiceMessages<T> returnError(T result);
	
	public ServiceMessages<T> returnErrorParam(String message, T result);
	
	public ServiceMessages<T> returnErrorParam(T result);
	
}
