package com.nineyao.search.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import junit.framework.TestCase;

public class CardProductControllerTest extends TestCase {
	
	//测试环境
//	String host = "http://192.168.90.209:8077/search-api-web";
//	String host = "http://192.168.90.207:8080/search-api-web";
//	String host = "http://mall.soa.9drug.cn/search-api-web";
	
	@Test
	public void test1() {
		try {
//			String host = "http://localhost:8080/search-api-web";
//			String host = "http://192.168.90.208:8091/search-api-web";
//			String host = "http://192.168.1.214:8085/search-api-web";
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost("/card/saveOrUpdate/8");
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	
	@Test
	public void test2() {
		try {
			String host = "http://localhost:8080/search-api-web";
//			String host = "http://192.168.90.208:8091/search-api-web";
//			String host = "http://192.168.1.214:8085/search-api-web";
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("ProductIdsStr", "10,35"));  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/card/saveOrUpdate/batch");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	@Test
	public void testAllSave() {
		try {
//			String host = "http://localhost:8080/search-api-web";
			String host = "http://192.168.90.209:8077/search-api-web";
			//预发环境
//			String host = "http://192.168.1.210:8085/search-api-web";
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/card/saveOrUpdate/all");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	
	@Test
	public void testOneDelete() {
		try {
			String host = "http://localhost:8080/search-api-web";
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
//			formparams.add(new BasicNameValuePair("productIdsStr", "10,11,12,13"));  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/card/delete/10/1");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	@Test
	public void testBatchDelete() {
		try {
			String host = "http://localhost:8080/search-api-web";
			
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("productIdsStr", "8_1,35_1"));  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/card/delete/batch");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	
	@Test
	public void testAllDelete() {
		try {
			String host = "http://localhost:8080/search-api-web";
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
//			formparams.add(new BasicNameValuePair("productIdsStr", "10,11,12,13"));  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/card/delete/all");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));	
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	
	
	
	private String getResponseBodyString(CloseableHttpResponse response) throws Exception{
		
		BufferedReader in = null;  
		  
        String content = null;
        
        in = new BufferedReader(new InputStreamReader(response.getEntity()  
                .getContent()));  
        StringBuffer sb = new StringBuffer("");  
        String line = "";  
        String NL = System.getProperty("line.separator");  
        while ((line = in.readLine()) != null) {  
            sb.append(line + NL);  
        }  
        in.close();  
        content = sb.toString();  
        
        return  content;
	}
	
	
}
