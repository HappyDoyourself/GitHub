package cn.com.jiuyao.pay.common.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by fanhongtao
 * Date 2017-02-23 16:15
 */
public class HttpClientTools {
    public static String doGet(String url, Map<String, String> map) throws IOException {

        if(!map.isEmpty()){
            url += url.endsWith("?") ? "" : "?";
            for(String key : map.keySet()){
                url += key + "=" + map.get(key) + "&";
            }
            url = url.substring(0,url.lastIndexOf("&"));
        }

        HttpClient client = new HttpClient();
        StringBuilder sb = new StringBuilder();
        InputStream ins = null;
        System.out.println(url);
        GetMethod method = new GetMethod(url);
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
       return sb.toString();
    }



    public static String doPost(String url, Map<String, String> map) throws IOException {

        if(!map.isEmpty()){
            url += url.endsWith("?") ? "" : "?";
            for(String key : map.keySet()){
                url += key + "=" + map.get(key) + "&";
            }
            url = url.substring(0,url.lastIndexOf("&"));
        }

        HttpClient client = new HttpClient();
        StringBuilder sb = new StringBuilder();
        InputStream ins = null;
        System.out.println(url);
        PostMethod method = new PostMethod(url);
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
        return sb.toString();
    }
}
