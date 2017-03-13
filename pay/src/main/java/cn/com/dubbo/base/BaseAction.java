package cn.com.dubbo.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 基础action
 * @author hhr
 */
public class BaseAction {

	protected   HttpServletRequest request;
	
	protected   HttpServletResponse response;
	
	protected   HttpSession session;
	
	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.session = request.getSession(); //注意session 和下面的 resonse的位置否则tomcat日志会报： Cannot create a session after the response has been committed fanhongtao 2017-2-17
		this.response = response;

	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	protected  String   getObjectToJsonStr(Object object){
		if(null == object){
			return  null;
		}
		return  JSON.toJSONString(object);
	}
	
	protected  String  getArrayToJsonStr(Object object){
		if(null == object){
			return  null;
		}
		return  JSONArray.toJSONString(object);
	}
	
	protected  Object  getJSONStringToObject(String jsonString,Class<?> clazz){
		if(null == jsonString){
			return  null;
		}
		return  JSON.parseObject(jsonString, clazz);
	}
	 
	protected  Object  getJSONStringToArrayObject(String jsonString,Class<?> clazz){
		if(null == jsonString){
			return  null;
		}
		return  JSONArray.parseObject(jsonString, clazz);
	}
	
 
	
}
