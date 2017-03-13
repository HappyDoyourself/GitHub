package cn.com.dubbo.service.payment.platform;

import cn.com.jiuyao.util.payments.pingan.httpClient.HttpProtocolHandler;
import cn.com.jiuyao.util.payments.pingan.httpClient.HttpRequest;
import cn.com.jiuyao.util.payments.pingan.httpClient.HttpResponse;
import cn.com.jiuyao.util.payments.pingan.httpClient.HttpResultType;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 平安 壹钱包 公用类
 * 
 * @author qun.su
 * @version 2014-5-21
 * 
 */
@Service
public class PinganBase extends PlatformPayment implements Platform {

	public static final String success = "S";// 成功常量
	public static final String fail = "N";// 失败常量


	// 发送方法
	/*public static String post(NameValuePair[] nameValuePair, String url)
			throws IOException {
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        request.setCharset("UTF-8");
        request.setParameters(nameValuePair);
        request.setUrl(url);

        HttpResponse response = httpProtocolHandler.execute(request,"","");
        if (response == null) {
            return null;
        }else{
            String strResult = response.getStringResult();
            return strResult;
        }
	}*/

    /**
     * pingan 参数拼接，用于存放数据库中
     * @param params
     * @return
     */
    public  String pinganMToString(Map<String, String[]> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String[] values = params.get(key);
            StringBuffer value = new StringBuffer();
            if (values[0] != null && values[0].indexOf("commodityNo=")!=-1) {//判断字段值是产品信息，yfj
                value.append("[");
                for (int j = 0; j < values.length; j++) {
                    String val = values[j];
                    value.append("{");
                    value.append(val);
                    if (j == values.length - 1) {
                        value.append("}");
                    } else {
                        value.append("},");
                    }
                }
                value.append("]");
            } else {
                value.append(values[0]);
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key.replace("[]", "") + "=" + value.toString();
            } else {
                prestr = prestr + key.replace("[]", "") + "=" + value.toString() + "|";
            }
        }

        return prestr;
    }
}
