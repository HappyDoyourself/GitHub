package cn.com.jiuyao.pay.common.util;

import java.util.Date;
import java.util.TreeMap;

import net.sf.json.JSONObject;

/**
 * 参数和签名验证
 * 
 * @author 常胜
 * @email 271500238@qq.com
 * @date 2016-7-14 下午2:47:00
 */
public class CheckParamUtil {

	public static boolean check(String str) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		JSONObject obj = JSONObject.fromObject(str);
		obj = JSONObject.fromObject(obj.get("root").toString());
		String dbhost = obj.get("dbhost").toString();
		String appkey = obj.get("appkey").toString();
		String timestamp = obj.get("timestamp").toString();
		String venderId = obj.getString("venderId");
		String v = obj.get("v").toString();
		String sign = obj.get("sign").toString();
		if (StringUtil.isStringNotBlank(dbhost) && StringUtil.isStringNotBlank(appkey) && StringUtil.isStringNotBlank(timestamp) && StringUtil.isStringNotBlank(sign) && StringUtil.isStringNotBlank(venderId) && StringUtil.isStringNotBlank(v)) {
			map.put("dbhost", dbhost);
			map.put("appkey", appkey);
			map.put("timestamp", timestamp);
			map.put("venderId", venderId);
			map.put("v", v);
			if (checkMd5(map, sign)) {
				long time = Long.parseLong(DateUtil.getDate2UTC(new Date()));
				long n = 60 * 3;
				long tamp = Long.parseLong(timestamp);
				if ((time - tamp) > n) {
					return false;
				}
				return true;
			}
		}
		return false;
	}

	public static boolean checkMd5(TreeMap<String, String> map, String sign) {
		String str = Md5Util.edbTradeGet(map);
		if (str.equals(sign.toUpperCase())) {
			return true;
		}
		return false;
	}
}
