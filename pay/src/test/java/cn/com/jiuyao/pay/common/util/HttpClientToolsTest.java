package cn.com.jiuyao.pay.common.util; 

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/** 
* HttpClientTools Tester. 
* 
* @author <fanhongtao> 
* @since <pre>02/23/2017</pre> 
* @version 1.0 
*/ 
public class HttpClientToolsTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getTest(String url) 
* 
*/ 
@Test
public void testGetTest() throws Exception { 
//TODO: Test goes here...
    HttpClient client = new HttpClient();
    StringBuilder sb = new StringBuilder();
    InputStream ins = null;
    // Create a method instance.
    //https://mapi.alipay.com/gateway.do?sign=pa8X9gSqS2lN%2FB5Sg2Lk546EXev5Y9%2Fue9MV9eGa%2BxJULkSeAj%2FqPpA97qfPQYlYXpR8MumgKMzaeupE4EUj0K8JJrRadiXwbrgrOpbyLbYeVFN7urHbpbtPpk9nFSiROZrLU9NPZ3mvFJnZ8DUmYFjUOtqRn3h1XHg%2B1ScaafU%3D&biz_content=%7B%22trade_no%22%3A%222017011321001004130247259279%22%2C%22refund_amount%22%3A%220.01%22%7D&timestamp=2017-02-23 16:22:35&sign_type=RSA&charset=utf-8&method=alipay.trade.refund&app_id=2016081901771657&version=1.0
    System.out.println("timestamp="+"2017-02-23 00:00:00");
    System.out.println("timestamp="+URLEncoder.encode("2017-02-23 00:00:00","utf-8"));
    GetMethod method = new GetMethod("https://mapi.alipay.com/gateway.do?sign=pa8X9gSqS2lN%2FB5Sg2Lk546EXev5Y9%2Fue9MV9eGa%2BxJULkSeAj%2FqPpA97qfPQYlYXpR8MumgKMzaeupE4EUj0K8JJrRadiXwbrgrOpbyLbYeVFN7urHbpbtPpk9nFSiROZrLU9NPZ3mvFJnZ8DUmYFjUOtqRn3h1XHg%2B1ScaafU%3D&biz_content=%7B%22trade_no%22%3A%222017011321001004130247259279%22%2C%22refund_amount%22%3A%220.01%22%7D&sign_type=RSA&charset=utf-8&method=alipay.trade.refund&app_id=2016081901771657&version=1.0&timestamp="+ URLEncoder.encode("2017-02-23 00:00:00","utf-8"));
    // Provide custom retry handler is necessary
    method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
            new DefaultHttpMethodRetryHandler(3, false));
    try {
        // Execute the method.
        int statusCode = client.executeMethod(method);
        System.out.println(statusCode);
        if (statusCode == HttpStatus.SC_OK) {
            ins = method.getResponseBodyAsStream();
            byte[] b = new byte[1024];
            int r_len = 0;
            while ((r_len = ins.read(b)) > 0) {
                sb.append(new String(b, 0, r_len, method
                        .getResponseCharSet()));
            }
        } else {
            System.err.println("Response Code: " + statusCode);
        }
    } catch (HttpException e) {
        System.err.println("Fatal protocol violation: " + e.getMessage());
    } catch (IOException e) {
        System.err.println("Fatal transport error: " + e.getMessage());
    } finally {
        method.releaseConnection();
        if (ins != null) {
            ins.close();
        }
    }
    System.out.println(sb.toString());
} 


} 
