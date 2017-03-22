package com.ninedrug.search.api.web.controller;

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
	
	
	
//	/**
//	 * 简单判断服务是否启动成功
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/ping/abc*", produces = "application/json")
//	@ResponseBody
//	public String ping3() {
//		
//		System.out.println("=======");
//		return "ping success  abc*";
//	}
//	
//	/**
//	 * 简单判断服务是否启动成功
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/ping/abc?", produces = "application/json")
//	@ResponseBody
//	public String ping4() {
//		
//		System.out.println("=======");
//		return "ping success abc?";
//	}
//	
//	
//	
//	/**
//	 * 简单判断服务是否启动成功
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/ping/ddd", produces = "application/json")
//	@ResponseBody
//	public String ping5() {
//		
//		return "ping success  ddd";
//	}
//	
//	/**
//	 * 简单判断服务是否启动成功
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/ping/**", produces = "application/json")
//	@ResponseBody
//	public String ping6() {
//		return "ping success /ping/**";
//	}
//	
//	/**
//	 * 简单判断服务是否启动成功
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "/ping/", produces = "application/json")
//	@ResponseBody
//	public String ping7() {
//		
//		return "ping success /ping/";
//	}
	
	
	public static void main(String[] args) {
		System.out.println("==PingController===");
	}
	
	
}
