
package cn.com.jiuyao.util.payments.alipayWap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

public class RSA{
	
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	
	public static String getStringSort(Map<String,String> map){
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));
		return authInfo.toString();
	}
	
	/**
	 * 拼接键值对
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	public static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
	
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @param input_charset 编码格式
	* @return 签名值
	*/
	public static String sign(String content, String privateKey, String input_charset)
	{
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(input_charset) );

            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        
        return null;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param ali_public_key 支付宝公钥
	* @param input_charset 编码格式
	* @return 布尔值
	*/
	public static boolean verify(String content, String sign, String ali_public_key, String input_charset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(ali_public_key);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(input_charset) );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	* 解密
	* @param content 密文
	* @param private_key 商户私钥
	* @param input_charset 编码格式
	* @return 解密后的字符串
	*/
	public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

	
	/**
	* 得到私钥
	* @param key 密钥字符串（经过base64编码）
	* @throws Exception
	*/
	public static PrivateKey getPrivateKey(String key) throws Exception {

		byte[] keyBytes;
		
		keyBytes = Base64.decode(key);
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		
		return privateKey;
	}
	
	public static void main(String[] args) {
		String str = "app_id=2016081901771657&biz_content={\"timeout_express\":\"720m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"14.90\",\"subject\":\"9drug-14792765422511400\",\"body\":\"商品\",\"out_trade_no\":\"14792765422511400\",\"seller_id\":\"2088421692694571\"}&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA&timestamp=2016-11-16 02:09:07&version=1.0";
//		String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKcZaUkZmFVhi9gclmqpOMwYphx/5kaTz02KU21a8qtcnoV90coRGj7pxmNmlWwkDhGNjc1NJ1iXh+M9ib0EqEDpQKNLRzF7rnenZOOiTMWLz7G9jvwUavRAtxlLGFbUD09xBAid6FjLcIljFpqU1rWE5TArqngkACfZ8dLWA6u5AgMBAAECgYBrq8cY0EJFOe63WWxe3B15/aQDlzJVZebdKvWo8EKPwsVudO1aS/zibxZjdHVx0iWyj8jAcieZQULRXRTE1BxpdIvZxoa6hILe6gRKlJUyH8ozctg9U/ChnBQysRVSOX3eCDVJ7gabUIDsN5PN04mulAzeqlGQ7A74XBtfdWE8gQJBANLGOGb9moE4ZZNRW6xM1jDX4R5rmsJR7LB3h52jwiwQ59Tct8xa2VmpasH217sKnTMpruP3a/gW6cMuvUKe7xECQQDK9CHT+Je3bFgjyDwyTsaFQw0fONCjtpUXzlW7pA1CKqx2vF8yN5IDIqwl52qvtFSdiY91WKQGhSvob7tcHUIpAkAaf17x26oD2sIyTgUXWSWISGt9totjKId3+97pu3+aqJlskj7tCvLOKdVJAy5FzmTK3M+myyEgtEi5B/Q1HuPxAkEAl/8CnrfdGDgCQrohqyOoNgGjgDCSwSlqu6uCNceFTnqbM0mcmgVYSxTajICmO/T4Mgvl7uJ15ZQy8B9N2IzpEQJAWARPWrCDqe7HUhhv1KIjaZlBfGTFYPv2SQQRpFi4peB8nLFWP9wKpr6yuX3YCJ5Wp13FHr6Nw70nVAWA+Bu0bg=="
		System.out.println(str);
		try {
			System.out.println(decrypt(str, "", "utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
