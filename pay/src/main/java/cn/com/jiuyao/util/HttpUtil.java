package cn.com.jiuyao.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Larry on 2015/3/17.
 * Http工具类
 */
public class HttpUtil {

    private static final String URL_PARAM_CONNECT_FLAG = "&";
    private static final String URL_PARAM_KEY_VALUE_FLAG = "=";
    private static Log log = LogFactory.getLog(HttpUtil.class);

    private HttpUtil() {
    }

    /**
     * GET METHOD
     * @param strUrl 请求URL
     * @param map 参数map
     * @param charSet 编码格式
     * @throws java.io.IOException
     * @return resMap Map
     */
    public static Map<String,String> URLGet(String strUrl, Map map, String charSet) throws IOException {
        String strtTotalURL = "";
        Map<String,String> resMap = new HashMap<String, String>();
        if(strtTotalURL.indexOf("?") == -1) {
            strtTotalURL = strUrl + "?" + getUrl(map);
        } else {
            strtTotalURL = strUrl + "&" + getUrl(map);
        }
        log.debug("strtTotalURL:" + strtTotalURL);
        URL url = new URL(strtTotalURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setFollowRedirects(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream(),charSet));
        while (true) {
            String line = in.readLine();
            System.out.println(line);
            if (line == null) {
                break;
            } else {//key,value分开放入map中
                String[] arr = line.split(URL_PARAM_CONNECT_FLAG);
                for(int i=0; i< arr.length; i++){
                    String[] arr2 = arr[i].split(URL_PARAM_KEY_VALUE_FLAG);
                    if(arr2.length>1){
                        resMap.put(arr2[0],arr2[1]);
                    }else{
                        resMap.put(arr2[0],"");
                    }

                }
            }
        }
        in.close();
        return resMap;
    }

    /**
     * GET METHOD
     * @param strUrl 请求URL
     * @param map 参数map
     * @param charSet 编码格式
     * @throws java.io.IOException
     * @return resMap Map
     */
    public static String getByMap(String strUrl, Map map, String charSet) throws IOException {
        String strtTotalURL = "";
        Map<String,String> resMap = new HashMap<String, String>();
        if(strtTotalURL.indexOf("?") == -1) {
            strtTotalURL = strUrl + "?" + getUrl(map);
        } else {
            strtTotalURL = strUrl + "&" + getUrl(map);
        }
        log.debug("strtTotalURL:" + strtTotalURL);
        URL url = new URL(strtTotalURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(false);
        con.setFollowRedirects(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream(),charSet));
        String line;
        String result = null;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        in.close();
        return result;
    }

    /**
     * POST METHOD
     * @param strUrl 请求URL
     * @param map 参数map
     * @param charSet 编码格式
     * @throws java.io.IOException
     * @return resMap Map
     */
    public static Map<String,String> URLPost(String strUrl, Map map, String charSet) throws IOException {

        String content = "";
        content = getUrl(map);
        String totalURL = null;
        if(strUrl.indexOf("?") == -1) {
            totalURL = strUrl + "?" + content;
        } else {
            totalURL = strUrl + "&" + content;
        }
        URL url = new URL(strUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setAllowUserInteraction(false);
        con.setUseCaches(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
        BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.
                getOutputStream()));
        bout.write(content);
        bout.flush();
        bout.close();
        /*BufferedReader bin = new BufferedReader(new InputStreamReader(con.
                getInputStream()),SIZE);*/
        BufferedReader bin = new BufferedReader(new InputStreamReader(
                con.getInputStream(),charSet));
        Map<String,String> resMap = new HashMap<String, String>();
        while (true) {
            String line = bin.readLine();
            if (line == null) {
                break;
            } else {
                String[] arr = line.split(URL_PARAM_CONNECT_FLAG);
                for(int i=0; i< arr.length; i++){
                    String[] arr2 = arr[i].split(URL_PARAM_KEY_VALUE_FLAG);
                    if(arr2.length>1){
                        resMap.put(arr2[0],arr2[1]);
                    }else{
                        resMap.put(arr2[0],"");
                    }
                }
            }
        }
        return resMap;
    }

    /**
     * 发送HTTP请求
     *
     * @param url 地址
     * @param propsMap 内容
     * @param charset 编码
     */
    public static String postMap(String url, Map<String, Object> propsMap,String charset) {
        String responseMsg = "";

        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);// POST请求
        // 参数设置
        Set<String> keySet = propsMap.keySet();
        NameValuePair[] postData = new NameValuePair[keySet.size()];
        int index = 0;
        for (String key : keySet) {
            postData[index++] = new NameValuePair(key, propsMap.get(key).toString());
        }
        postMethod.addParameters(postData);
        try {
            httpClient.executeMethod(postMethod);// 发送请求
            // 处理返回的内容
            if("".equals(charset)){
                responseMsg = postMethod.getResponseBodyAsString();
            }else{
                responseMsg = new String(postMethod.getResponseBodyAsString().getBytes(charset));
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();// 关闭连接
        }
        return responseMsg;
    }

    /**
     * 拼接URL
     * @param map Map
     * @return String
     */
    private static String getUrl(Map map) {
        if (null == map || map.keySet().size() == 0) {
            return ("");
        }
        StringBuffer url = new StringBuffer();
        Set keys = map.keySet();
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            String key = String.valueOf(i.next());
            if (map.containsKey(key)) {
                Object val = map.get(key);
                String str = val!=null?val.toString():"";
                try {
                    str = URLEncoder.encode(str, "GBK");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url.append(key).append("=").append(str).
                        append(URL_PARAM_CONNECT_FLAG);
            }
        }
        String strURL = "";
        strURL = url.toString();
        if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
            strURL = strURL.substring(0, strURL.length() - 1);
        }
        return (strURL);
    }

    /**
     * http 发送方法
     * @param strUrl 请求URL
     * @param content 请求内容
     * @return 响应内容 String
     * @throws org.apache.commons.httpclient.HttpException
     * @throws java.io.IOException
     */
    public static String post(String strUrl,String content)
            throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type",
                "text/plain;charset=UTF-8");
        connection.connect();
        // POST请求
        DataOutputStream out = new DataOutputStream(
                connection.getOutputStream());
        out.write(content.getBytes("utf-8"));
        out.flush();
        out.close();
        // 读取响应
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "UTF-8"));
        String lines;
        StringBuffer sb = new StringBuffer("");
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
        reader.close();
        // 断开连接
        connection.disconnect();

        return sb.toString();
    }

}
