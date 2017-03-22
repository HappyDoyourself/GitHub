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

public class ProductCategoryViewBaseControllerTest extends TestCase {
	
	//测试环境
//	String host = "http://192.168.90.209:8077/";
//	String host = "http://192.168.90.209:8077/search-api-web";
	String host = "http://192.168.90.207:8080/search-api-web";
	
	@Test
	public void testOneSave() {
		try {
			
			String host = "http://localhost:8080/search-api-web";
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
//			formparams.add(new BasicNameValuePair("productIdsStr", "10,11,12,13"));  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
			
			HttpPost httppost = new HttpPost(host+"/productViewCategory/saveOrUpdate/1008");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	
	@Test
	public void testBatchSave() {
		try {
			String host = "http://localhost:8080/search-api-web";
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("viewCateIdsStr", "36"));  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/productViewCategory/saveOrUpdate/batch");  
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
//			String host = "http://192.168.90.209:8077/search-api-web";
			//预发环境
			String host = "http://192.168.1.214:8085/search-api-web";
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
//			formparams.add(new BasicNameValuePair("productIdsStr", "10,11,12,13"));  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/productViewCategory/saveOrUpdate/all");  
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
//			String host = "http://192.168.90.209:8077/search-api-web";
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
//			formparams.add(new BasicNameValuePair("productIdsStr", "10,11,12,13"));  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/productViewCategory/delete/36");  
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
			formparams.add(new BasicNameValuePair("productIdsStr", "10,11,12,13"));  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/productViewCategory/delete/batch");  
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
			//get 
			String host = "http://localhost:8080/search-api-web";
			
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
//			formparams.add(new BasicNameValuePair("productIdsStr", "10,11,12,13"));  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/productViewCategory/delete/all");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));	
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	@Test
	public void testSearchByProductName() {
		try {
			//get 
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URI uri = new URIBuilder()  
			        .setScheme("http")  
			        .setHost("localhost:8080/search-api-web")  
			        .setPath("/product/searchByProductName")  
			        .setParameter("productName", "勿动勿买")  
			        .setParameter("btnG", "Google Search")  
			        .setParameter("aq", "f")  
			        .setParameter("oq", "")  
			        .build();  
			HttpGet httpget = new HttpGet(uri); 
			CloseableHttpResponse response = httpclient.execute(httpget);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	
	
	@Test
	public void testPostSearchByProductName() {
		try {
			
			String host = "http://localhost:8080/search-api-web";
			
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("productName", "欧姆龙"));  
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/product/searchByProductName");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	@Test
	public void testSearchPageByProductName() {
		try {
			String host = "http://localhost:8080/search-api-web";
			
			System.out.println("=========");
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("page", "1"));  
			formparams.add(new BasicNameValuePair("size", "20"));
			formparams.add(new BasicNameValuePair("productName", "电子"));
			formparams.add(new BasicNameValuePair("orderNum", "1"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost(host+"/product/searchPageByProductName");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	
	@Test
	public void testPcsearch() {
		try {
			System.out.println("======get===");
			
			
//			String host = "localhost:8080/search-api-web";
			String host = "192.168.90.209:8077";
			
			String productName = URLEncoder.encode("感冒", "UTF-8");
			//get 
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URI uri = new URIBuilder()  
			        .setScheme("http")  
			        .setHost(host)  
			        .setPath("/search/pcsearch")  
			        .setParameter("pageNum", "1")  
			        .setParameter("pageSize", "20")  
			        .setParameter("productName", productName)
			        .setParameter("orderBy", "2")  
			        .build();  
			HttpGet httpget = new HttpGet(uri); 
			CloseableHttpResponse response = httpclient.execute(httpget);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	@Test
	public void testwapsearch() {
		try {
			
			String host = "localhost:8080/search-api-web";
//			String host = "192.168.90.209:8077";
			
			System.out.println("======get===");
			String productName = URLEncoder.encode("感冒", "UTF-8");
			//get 
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URI uri = new URIBuilder() 
					.setScheme("http") 
			        .setHost(host)  
			        .setPath("/search/wapsearch")  
			        .setParameter("pageNum", "1")  
			        .setParameter("pageSize", "5")  
			        .setParameter("k", productName)
//			        .setParameter("b", "18")
//			        .setParameter("c3", "11")
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
	
	@Test
	public void testSearchPageByProductName2() {
		try {
			System.out.println("=========");
			String host = "http://localhost:8080/search-api-web";
//			String host = "localhost:8080/search-api-web";
//			String host = "192.168.90.209:8077";
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("page", "1"));  
			formparams.add(new BasicNameValuePair("size", "20"));
			formparams.add(new BasicNameValuePair("productName", "感冒灵"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost("http://localhost:8080/search-api-web/search/pc/1/2");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	@Test
	public void testSearchPageByProductName3() {
		try {
			System.out.println("=========");
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("page", "1"));  
			formparams.add(new BasicNameValuePair("size", "40"));
			formparams.add(new BasicNameValuePair("productName", "清热通窍"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
//			HttpPost httppost = new HttpPost("http://localhost:8080/search-api-web/search/wap/1/2");  
			
			HttpPost httppost = new HttpPost("http://192.168.90.209:8077/search/wap/1/2");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	 
	
	
	@Test
	public void testSearchPageByProductId() {
		try {
			
			System.out.println("=========");
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			formparams.add(new BasicNameValuePair("productId", "10"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
			HttpPost httppost = new HttpPost("http://localhost:8080/search-api-web/product/searchPageByProductId");  
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
	
	
	@Test
	public void testshopcartdelete() {
		try {
			
			System.out.println("=========");
			
			CloseableHttpClient httpclient = HttpClients.createDefault();
			
			
			List<Integer> array = new ArrayList<Integer>(); 
			array.add(1);
			array.add(2);
			
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
//			formparams.add(new BasicNameValuePair("memberId", "5"));
			formparams.add(new BasicNameValuePair("array", array.toString()));
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8); 
//			mobile.shopCar.soa.9yao.test
//			HttpPost httppost = new HttpPost("http://192.168.100.88:8080/shopping-api-mobile/shopping/removeProductCart.html");  
			
			HttpPost httppost = new HttpPost("http://localhost:8080/search-api-web/base/ping2");
			httppost.setEntity(entity); 
			
			CloseableHttpResponse response = httpclient.execute(httppost);  
			
			System.out.println("getStatusLine==="+response.getStatusLine().toString());
			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
//	@Test
//	public void testSearchPageByProductName4() {
//		try {
//			System.out.println("=========");
//			
//			CloseableHttpClient httpclient = HttpClients.createDefault();
//			
//			List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
//			formparams.add(new BasicNameValuePair("pageSize", "1"));  
//			formparams.add(new BasicNameValuePair("pageNum", "40"));
//			formparams.add(new BasicNameValuePair("memberId", "1111"));
//			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);  
////			HttpPost httppost = new HttpPost("http://localhost:8080/search-api-web/search/wap/1/2");  
//			
//			HttpPost httppost = new HttpPost("http://192.168.100.88:8080/mobileShopping_provider/shopping/getCart.html");  
//			httppost.setEntity(entity); 
//			
//			CloseableHttpResponse response = httpclient.execute(httppost);  
//			
//			System.out.println("getStatusLine==="+response.getStatusLine().toString());
//			System.out.println("getResponseBodyString==="+getResponseBodyString(response));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}  
//		
//	}
	
	
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
