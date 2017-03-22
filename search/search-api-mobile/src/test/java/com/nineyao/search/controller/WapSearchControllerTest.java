package com.nineyao.search.controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import junit.framework.TestCase;

public class WapSearchControllerTest extends TestCase {
	
	//测试环境
	String host = "192.168.90.209:8077/search-api-mobile";
//	String host = "192.168.90.208:8088/search-api-mobile";
//	String host = "192.168.90.207:8080/search-api-mobile";
	
//	String host = "192.168.1.215:8093/search-api-mobile";
	
	@Test
	public void testwapsearch() {
//		String host = "localhost:8080/search-api-mobile";
		try {
			
			System.out.println("======get===");
//			String productName = URLEncoder.encode("#", "UTF-8");
//			String productName = URLEncoder.encode("胃药", "UTF-8");
			String productName = URLEncoder.encode("创可贴", "UTF-8");
//			String productName = URLEncoder.encode("aabb", "UTF-8");
//			String productName = "%E7%BB%B4%E7%94%9F%E7%B4%A0";
//			String productName = URLEncoder.encode("流行性感冒", "UTF-8");
			//get 
			//胃药  commonTermsQuery("productDesc", productModel.getK()  能搜索到结果
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URI uri = new URIBuilder() 
					.setScheme("http") 
			        .setHost(host)
			        .setPath("/search/wapsearch")
			        .setParameter("pageNum", "1")
			        .setParameter("pageSize", "20")
			        .setParameter("k", productName)
//			        .setParameter("b", "13")
//			        .setParameter("c3", "34")
//			        .setParameter("c3", "1476")
//			        .setParameter("couponId", "0")
//			        .setParameter("minPrice", "699")
//			        .setParameter("maxPrice", "1000")
			        .setParameter("orderBy", "1")  
			        .build();
			
			HttpGet httpget = new HttpGet(uri); 
			CloseableHttpResponse response = httpclient.execute(httpget);  
			
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
