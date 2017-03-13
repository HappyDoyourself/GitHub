
package cn.com.dubbo.service.payment.constant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 类 <code>ReturnMessage</code>
 * @author suqun
 * @version 2015-3-18
 */
public class ReturnMessage implements Serializable {
	/** 平安健康卡返回状态 **/
	public static final String PINGAN_WP = "WP";//WP-等待付款
	public static final String PINGAN_PD = "PD";//PD-支付完成
	public static final String PINGAN_CZ = "CZ";//CZ-订单关闭
	public static final String PINGAN_EX = "EX";//EX-订单过期
	public static final String PINGAN_CA = "CA";//CA-交易取消
	public static final String PINGAN_RE = "RE";//RE-订单退款

	private static Map<String, String> map; 
	static{
		map = new HashMap<String, String>();
		//平安健康卡查询返回信息
		map.put(PINGAN_WP, "等待付款");
		map.put(PINGAN_PD, "支付完成");
		map.put(PINGAN_CZ, "订单关闭");
		map.put(PINGAN_EX, "订单过期");
		map.put(PINGAN_CA, "交易取消");
		map.put(PINGAN_RE, "订单退款");
	}

	public static Map<String, String> getTarget() {

		return map;
	}

}
