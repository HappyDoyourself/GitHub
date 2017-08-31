package com.baomidou.springwind.util.weixinutil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;


public class HttpClientUtils {

	public static String doPost(String url,Map<String,String> map) throws Exception{
		HttpClient client = new HttpClient();
		//client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);  
		//HttpMethod method = new GetMethod("http://10.1.14.20:8088/workflowController/service/todo/addTask");
		if(!map.isEmpty()){
			url += url.endsWith("?") ? "" : "?";
			for(String key : map.keySet()){
				url += key + "=" + map.get(key) + "&";
			}
			url = url.substring(0,url.lastIndexOf("&"));
		}
            System.out.println("url:"+url);
            PostMethod method = new PostMethod(url);
		
//		GetMethod getMethod = new GetMethod(url); 

		HttpMethodParams param = method.getParams();  
		param.setContentCharset("UTF-8");  

		client.executeMethod(method);  
		
		InputStream stream = method.getResponseBodyAsStream();  

		BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));  
		StringBuffer buf = new StringBuffer();  
		String line;  
		while (null != (line = br.readLine())) {  
			buf.append(line).append("\n");  
		}  
		method.releaseConnection(); 
		return buf.toString();
	}
	
	/**
	 * dopost
	 * @param url_address
	 * @param request_body
	 * @return
	 * @throws Exception 
	 */
	public static String do_post(String url_address, String request_body) throws Exception{
		String body ="";
        // Configure and open a connection to the site you will send the request  
        // java.net.URL  java.net.URLConnection
        URL url = new URL(url_address);  
        URLConnection urlConnection = url.openConnection();  
        // 设置doOutput属性为true表示将使用此urlConnection写入数据  
        urlConnection.setDoOutput(true);  
        //定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型  
        urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // 得到请求的输出流对象  
        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());  
        // 把数据写入请求的Body  
        out.write(request_body);  
        out.flush();  
        out.close();  
        // 从服务器读取响应  
        InputStream inputStream = urlConnection.getInputStream(); 
        body = ConvertStream2Json(inputStream);
        System.out.println("body==================" +body);
        return new String(body.getBytes("GBK"),"UTF-8");
	}
	
	private static String ConvertStream2Json(InputStream inputStream){
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try{
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1){
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        }catch (IOException e){
        	e.printStackTrace();
//            log.error(e.getMessage(),e);
        }
        return jsonStr;
    }


    public static String doGet(String url,Map<String,String> map) throws Exception{
        HttpClient client = new HttpClient();
        //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        //HttpMethod method = new GetMethod("http://10.1.14.20:8088/workflowController/service/todo/addTask");
        if(!map.isEmpty()){
            url += url.endsWith("?") ? "" : "?";
            for(String key : map.keySet()){
                url += key + "=" + map.get(key) + "&";
            }
            url = url.substring(0,url.lastIndexOf("&"));
        }
        System.out.println("url:"+url.replace("https","http"));
        GetMethod method = new GetMethod(URLEncoder.encode(url.replace("https","http"), "UTF-8"));

//		GetMethod getMethod = new GetMethod(url);

        HttpMethodParams param = method.getParams();
        param.setContentCharset("UTF-8");

        client.executeMethod(method);

        InputStream stream = method.getResponseBodyAsStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuffer buf = new StringBuffer();
        String line;
        while (null != (line = br.readLine())) {
            buf.append(line).append("\n");
        }
        method.releaseConnection();
        return buf.toString();
    }

}
