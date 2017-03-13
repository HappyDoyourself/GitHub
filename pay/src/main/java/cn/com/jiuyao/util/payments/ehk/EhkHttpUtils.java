package cn.com.jiuyao.util.payments.ehk;

import sun.net.www.protocol.https.Handler;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fanhongtao
 * Date 2017-01-13 10:02
 */
public class EhkHttpUtils {
    public static String post(String urlStr, String data) {
        HttpURLConnection con = null;
        StringBuilder sb = new StringBuilder();

        try {
            URL e = new URL((URL)null, urlStr, new Handler());
            con = (HttpURLConnection)e.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setInstanceFollowRedirects(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/vnd.ehking-v1.0+json");
            con.setRequestProperty("charset", "UTF-8");
            con.setUseCaches(false);
            Integer connectTimeout = 30000;
            if(connectTimeout != null) {
                con.setConnectTimeout(connectTimeout.intValue());
                // log.info("set connectTimeout:[" + connectTimeout + "]");
            } else {
                //LOGGER.info("not set connectTimeout:[" + connectTimeout + "]");
            }

            Integer readTimeout = 50000;
            if(readTimeout != null) {
                con.setReadTimeout(readTimeout.intValue());
                //  LOGGER.info("set readTimeout:[" + readTimeout + "]");
            } else {
                // LOGGER.info("not set readTimeout:[" + connectTimeout + "]");
            }

            if(con instanceof HttpsURLConnection) {
                HttpsURLConnection wr = (HttpsURLConnection)con;
                                /*wr.setHostnameVerifier(sslHostnameVerifier);
                                wr.setSSLSocketFactory(sslSocketFactory);*/
            }

            DataOutputStream wr1 = new DataOutputStream(con.getOutputStream());
            wr1.write(data.getBytes("UTF-8"));
            wr1.flush();
            wr1.close();
            int HttpResult = con.getResponseCode();
            if(HttpResult == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String line = null;

                while((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }

                br.close();
            } else {
                // LOGGER.info(con.getResponseMessage());
            }
        } catch (Exception var14) {
            //LOGGER.info("post", var14);
        } finally {
            if(con != null) {
                con.disconnect();
            }

        }

        return sb.toString();
    }
}
