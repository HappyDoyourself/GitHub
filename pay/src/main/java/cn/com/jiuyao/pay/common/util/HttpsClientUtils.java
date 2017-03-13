package cn.com.jiuyao.pay.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import cn.com.jiuyao.util.payments.weixin.XMLUtil;

/**
 * https请求
 * @author jinjin
 *
 */
public class HttpsClientUtils {

	/**
	 * 创建https请求
	 * @param url 请求地址
	 * @param p12Path 证书地址
	 * @param passWord 证书密码
	 * @param info 请求主体
	 * @return 返回结果
	 */
	public static Map<String, String> doPost(String url, String p12Path , String passWord ,String info){
		try {
		 KeyStore keyStore = KeyStore.getInstance("PKCS12");  
	        FileInputStream instream = new FileInputStream(new File(p12Path));  
	        try {  
	            keyStore.load(instream,passWord.toCharArray());  
	        }finally {  
	            instream.close();  
	        }  
	        // Trust own CA and all self-signed certs  
	        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,  
	        		passWord.toCharArray()).build();  
	        // Allow TLSv1 protocol only  
	        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
	                sslcontext, new String[] { "TLSv1" }, null,  
	                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
	        CloseableHttpClient httpclient = HttpClients.custom()  
	                .setSSLSocketFactory(sslsf).build();  
	        HttpPost httppost = new HttpPost(url);  
	            try {  
	                StringEntity se = new StringEntity(info);  
	                  
	                httppost.setEntity(se);  

	                System.out.println("executing request" + httppost.getRequestLine());  

	                CloseableHttpResponse responseEntry = httpclient.execute(httppost);  
	                try {  
	                    HttpEntity entity = responseEntry.getEntity();  

	                    System.out.println(responseEntry.getStatusLine());  
	                    if (entity != null) {  
	                        System.out.println("Response content length: "  
	                                + entity.getContentLength());  
//	                        StringBuffer buff = new StringBuffer();
//	                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
	                        Map<String, String> m = new HashMap<String, String>();
	                        InputStream in = entity.getContent();
	                        SAXBuilder builder = new SAXBuilder();
	                		Document doc = builder.build(in);
	                		Element root = doc.getRootElement();
	                		List list = root.getChildren();
	                		Iterator it = list.iterator();
	                		while(it.hasNext()) {
	                			Element e = (Element) it.next();
	                			String k = e.getName();
	                			String v = "";
	                			List children = e.getChildren();
	                			if(children.isEmpty()) {
	                				v = e.getTextNormalize();
	                			} else {
	                				v = XMLUtil.getChildrenText(children);
	                			}
	                			
	                			m.put(k, v);
	                		}
	                		
	                		//关闭流
	                		in.close();
//	                        while (bufferedReader.readLine() != null && !"null".equals(bufferedReader.readLine())) {
//	                        	 buff.append(bufferedReader.readLine());  
//	                        	 System.out.println(bufferedReader.readLine());
//	                        }
//	                        return buff.toString().replace("null", "");  
	                        return m;
	                    }  
	                    EntityUtils.consume(entity);  
	                }  
	                finally {  
	                    responseEntry.close();  
	                }  
	            }  
	            finally {  
	                httpclient.close();  
	            }  
	            return null;  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}       
		return null;
	}
	
}
