package cn.com.dubbo.service.payment.factory;

import cn.com.dubbo.service.payment.constant.ClassNameTarget;
import cn.com.dubbo.service.payment.platform.Platform;
import cn.com.dubbo.service.payment.promote.Strategy;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 类 <code>Factory</code>简单工厂，创建支付平台和策略
 * 
 * @author suqun
 * @version 2014-3-7
 */
public class Factory {
//	private static final String PLATEFORM_PATH = "com.founder.ec.web.service.payment.platform."; 
	private static final String PROMOTE_PATH = "com.founder.ec.web.service.payment.promote."; 
	
	/**
	 * 创建支付平台对象
	 * 
	 * @param paymentTypeNo
	 * @return
	 */
	public static Platform createPlatform(String paymentTypeNo){
		//根据paymentTypeNo获取支付平台类名
		Map<String, String> target = ClassNameTarget.getTarget();
		String className = (String) target.get(paymentTypeNo);
	    //Platform platform =  (Platform) BeanFactoryUtil.getBeanFactory().getBean(className);
	    Platform platform =  (Platform) SpringContextUtil.getBean(className);

		return platform;
	}
	
	/**
	 * 创建优惠策略对象
	 * 
	 * @param discountPlan
	 * @return
	 */
	public static Strategy createPromoteStrategy(String discountPlan){
		//根据discountPlan获取优惠计划
		Map<String, String> target = ClassNameTarget.getTarget();
		String className = (String) target.get(discountPlan);
		
		//通过反射方式获得对应优惠处理类
		Strategy strategy = null;
		try {
			strategy = (Strategy) Class.forName(PROMOTE_PATH+className).newInstance();
    	} catch (InstantiationException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IllegalAccessException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
		return strategy;
		
	}
	 
}
