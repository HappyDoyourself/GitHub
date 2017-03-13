package cn.com.jiuyao.util.payments.alipay;
/**
 * 名称：支付主类
 * 功能：支付宝外部服务接口控制
 * 接口名称：标准即时到账接口
 * 版本：2.0
 * 日期：2008-12-25
 * 作者：支付宝公司销售部技术支持团队
 * 联系：0571-26888888
 * 版权：支付宝公司
 * */
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Payment {
	/**
	 * 生成url方法
	 * 网关
	 * @param paygateway
	 * 服务参数
	 * @param service
	 * 签名类型
	 * @param sign_type
	 * 外部订单号
	 * @param out_trade_no
	 * 编码机制
	 * @param input_charset
	 * 合作者ID
	 * @param partner
	 * 安全校验码
	 * @param key
	 * 商品展示地址
	 * @param show_url
	 * 商品描述
	 * @param body
	 * 商品价格
	 * @param total_fee
	 * 支付类型
	 * @param payment_type
	 * 卖家账户
	 * @param seller_email
	 * 商品名称
	 * @param subject
	 * 异步返回地址
	 * @param notify_url
	 * 同步返回地址
	 * @param return_url
	 * 支付方式
	 * @param paymethod
	 * 默认银行
	 * @param defaultbank
	 * @return
	 */
				
    public static String CreateUrl(String paygateway, 
    		String service, String sign_type,
    		String out_trade_no,String input_charset,
    		String partner,String key,String show_url, 
    		String body, String total_fee, String payment_type,
    		String seller_email,String subject ,String notify_url, 
    		String return_url,String paymethod,String defaultbank) {

        Map params = new HashMap();
        params.put("service", service);
        params.put("partner", partner);
        params.put("subject", subject);
        params.put("body", body);
        params.put("out_trade_no", out_trade_no);
        params.put("total_fee", total_fee);
        params.put("show_url", show_url);
        params.put("payment_type",payment_type);
        params.put("seller_email", seller_email);
        params.put("return_url", return_url);
        params.put("notify_url", notify_url);
        params.put("_input_charset", input_charset);
        params.put("paymethod", paymethod);
        params.put("defaultbank", defaultbank);

        String prestr = "";

        prestr = prestr + key;
        //System.out.println("prestr=" + prestr);

        String sign = cn.com.jiuyao.util.payments.alipay.Md5Encrypt.md5(getContent(params, key));

        String parameter = "";
        parameter = parameter + paygateway;
        //System.out.println("prestr="  + parameter);
        List keys = new ArrayList(params.keySet());
        for (int i = 0; i < keys.size(); i++) {
          	String value =(String) params.get(keys.get(i));
            if(value == null || value.trim().length() ==0){
            	continue;
            }
            try {
                parameter = parameter + keys.get(i) + "="
                    + URLEncoder.encode(value, input_charset) + "&";
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }

        parameter = parameter + "sign=" + sign + "&sign_type=" + sign_type;

        return parameter;

    }

    /**
     * 功能：将安全校验码和参数排序
     * 参数集合
     * @param params
     * 安全校验码
     * @param privateKey
     * */
    public static String getContent(Map params, String privateKey) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);

        String prestr = "";

		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "&" + key + "=" + value;
			}
		}

        return prestr + privateKey;
    }
}
