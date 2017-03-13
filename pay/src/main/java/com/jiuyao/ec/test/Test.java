package com.jiuyao.ec.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.http.Consts;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;

import cn.com.dubbo.model.PayType;
import cn.com.jiuyao.pay.common.util.DateUtil;
import cn.com.jiuyao.pay.common.util.JsonUtil;
import cn.com.jiuyao.pay.common.util.Md5Encrypt;
import cn.com.jiuyao.util.payments.alipayWap.Base64;
import cn.com.jiuyao.util.payments.weixin.MD5Util;
import cn.com.jiuyao.util.payments.weixin.TenpayUtil;
import cn.com.jiuyao.util.payments.weixin.WXUtil;
import cn.com.jiuyao.util.payments.weixin.XMLUtil;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	

}
