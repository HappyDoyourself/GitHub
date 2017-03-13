package cn.com.jiuyao.util.payments.weixin;

import org.jdom.JDOMException;

import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.jiuyao.util.payments.alipay.Payment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class WXUtil {
	
	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	/**
	 * http 发送方法
	 * @param strUrl 请求URL
	 * @param content 请求内容
	 * @return 响应内容
	 * @throws org.apache.commons.httpclient.HttpException
	 * @throws java.io.IOException
	 */
	public static String post(String strUrl,String content)
			throws IOException {
		URL url = new URL(strUrl);
		HttpURLConnection connection = (HttpURLConnection) url
				.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type",
				"text/plain;charset=UTF-8");
		connection.connect();
		// POST请求
		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());
		out.write(content.getBytes("utf-8"));
		out.flush();
		out.close();
		// 读取响应
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));
		String lines;
		StringBuffer sb = new StringBuffer("");
		while ((lines = reader.readLine()) != null) {
			lines = new String(lines.getBytes(), "utf-8");
			sb.append(lines);
		}
		reader.close();
		// 断开连接
		connection.disconnect();

		return sb.toString();
	}

	/**
	 * 按参数首字母排序获取签名
	 * @param m
	 * @param appKey
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static String createSign(Map m,String appKey)throws IOException, JDOMException{
		List keys = new ArrayList(m.keySet());
        Collections.sort(keys);

        StringBuffer prestr = new StringBuffer();

		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) m.get(key);
			if (value == null || value.trim().length() == 0 || "sign".equals(key)) {
				continue;
			}
			if (first) {
				prestr.append(key + "=" + value) ;
				first = false;
			} else {
				prestr.append("&" + key + "=" + value) ;
			}
		}
		if(appKey != null){
			prestr.append("&key="+appKey);
		}
		String sign = MD5Util.MD5Encode(prestr.toString(), "utf-8").toUpperCase();
		return sign;
	}

	/**
	 * 是否微信签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * @return boolean
	 * @throws IOException
	 * @throws org.jdom.JDOMException
	 */
	public static boolean isWeixinSign(Map m,String weixinSign,String appKey) throws IOException, JDOMException {
//		SortedMap wxparameters = new TreeMap();
//		//设置参数
//		Iterator it = m.keySet().iterator();
//		while(it.hasNext()) {
//			String k = (String) it.next();
//			String v = (String) m.get(k);
//			wxparameters.put(k.toLowerCase(), v);
//		}
//
//		StringBuffer sb = new StringBuffer();
//		Set es = wxparameters.entrySet();
//		wxparameters.put("appkey", appKey);
//		Iterator it2 = es.iterator();
//		while(it2.hasNext()) {
//			Map.Entry entry = (Map.Entry)it2.next();
//			String k = (String)entry.getKey();
//			String v = (String)entry.getValue();
//			if(!"appsignature".equals(k) && !"signmethod".equals(k)){
//				sb.append(k.toLowerCase() + "=" + v + "&");
//			}
//		}
//
//		//去掉最后一个&
//		String signPars = sb.substring(0, sb.lastIndexOf("&"));
//		String sign = Sha1Util.getSha1(signPars);
		
		String mysign = createSign(m, appKey);

		return weixinSign.equals(mysign);
	}

	/**
	 * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * @return boolean
	 */
	public static boolean isTenpaySign(Map m,String appKey,String enc) {
		SortedMap parameters = new TreeMap();
		Iterator itt = m.keySet().iterator();
		while (itt.hasNext()) {
			String k = (String) itt.next();
			String v = ((String[]) m.get(k))[0];
			parameters.put(k, v);
		}

		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		sb.append("key=" + appKey);

		String sign = MD5Util.MD5Encode(sb.toString(), enc).toUpperCase();
		String tenpaySign = parameters.get("sign").toString().toUpperCase();
		return tenpaySign.equals(sign);
	}
	


}
