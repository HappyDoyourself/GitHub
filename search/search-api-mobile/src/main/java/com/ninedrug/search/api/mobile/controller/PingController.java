package com.ninedrug.search.api.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ping
 */
@Controller
@RequestMapping("/base")
public class PingController {

	/**
	 * 简单判断服务是否启动成功
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ping", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	String ping() {
		return "ping success";
	}
	
//	
//	/**
//	 * 简单判断服务是否启动成功
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/ping2", produces = "application/json")
//	@ResponseBody
//	public String ping2(String[] array) {
//		
//		System.out.println("=======");
//		return "ping success"+array.length;
//	}
//	
//	public static void main(String[] args) {
//		System.out.println("==PingController===");
//	}
	
	
}
