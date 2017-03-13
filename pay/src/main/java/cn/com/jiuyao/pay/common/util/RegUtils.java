package cn.com.jiuyao.pay.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 * 获得request请求数据
 *
 */
public class RegUtils {

	public static String reqString(HttpServletRequest request) throws UnsupportedEncodingException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(ServletInputStream) request.getInputStream(), "UTF-8"));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null)
		{
			sb.append(line);
		}
		return sb.toString();

	}
	
}
