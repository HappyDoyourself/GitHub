package cn.com.jiuyao.pay.common.util;

import java.util.TreeMap;

public class Md5Util {
	// 调用API地址 http://vip528.edb07.net/rest/index.aspx?
	protected static String testUrl = "http://vip528.edb07.net/rest/index.aspx";
	// 申请的appkey
	public static final String appkey = "4d47f04c";
	// 申请的secret
	public static final String secret = "41ed64b8885c477496eccd28b48112d4";
	// 申请的token
	public static final String token = "0aab8a9f960044a9979d6c8252291285";
	// 主帐号
	public static final String dbhost = "edb_a85111";
	// 返回格式
	public static final String format = "json";

	// 获取订单信息
	public static String edbTradeGet(TreeMap<String, String> map) {
		// TreeMap<String, String> apiparamsMap = new TreeMap<String, String>();
		//
		// 获取数字签名
		String sign = Util.md5Signature(map, secret, token);// appkey secret
		return sign;
	}

}
