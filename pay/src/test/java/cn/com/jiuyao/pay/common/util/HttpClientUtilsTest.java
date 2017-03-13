package cn.com.jiuyao.pay.common.util; 

import cn.com.dubbo.action.JunitSuper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/** 
* HttpClientUtils Tester. 
* 
* @author <fanhongtao> 
* @since <pre>02/20/2017</pre> 
* @version 1.0 
*/ 
public class HttpClientUtilsTest extends JunitSuper{

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: doPost(String url, Map<String,String> map) 
* 
*/ 
@Test
public void testDoPost()   throws Exception

   {
//TODO: Test goes here...
   HttpClient client = new HttpClient();
   Map<String,String> map = new HashMap<String,String>();
   map.put("memberId","2");
   map.put("memberSource", "pingan");
   String url = "http://mall.soa.9drug.cn/mall-api-mobile/member/memberSource.html";
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
   System.out.println(buf.toString());
} 

/** 
* 
* Method: do_post(String url_address, String request_body) 
* 
*/ 
@Test
public void testDo_post() throws Exception {
   String body ="";
   // Configure and open a connection to the site you will send the request
   // java.net.URL  java.net.URLConnection
   URL url = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");
   URLConnection urlConnection = url.openConnection();
   // 设置doOutput属性为true表示将使用此urlConnection写入数据
   urlConnection.setDoOutput(true);
   //定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
   urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=utf-8");
   // 得到请求的输出流对象
   OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
   // 把数据写入请求的Body
   out.write("");
   out.flush();
   out.close();
   // 从服务器读取响应
   InputStream inputStream = urlConnection.getInputStream();
   body = ConvertStream2Json(inputStream);
   System.out.println("body==================" +body);
   System.out.println(new String(body.getBytes("GBK"),"UTF-8"));


//TODO: Test goes here... 
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
/** 
* 
* Method: ConvertStream2Json(InputStream inputStream) 
* 
*/ 
@Test
public void testConvertStream2Json() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = HttpClientUtils.getClass().getMethod("ConvertStream2Json", InputStream.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
