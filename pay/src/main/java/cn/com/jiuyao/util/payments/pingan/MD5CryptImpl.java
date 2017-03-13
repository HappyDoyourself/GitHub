package cn.com.jiuyao.util.payments.pingan;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays; 

public class MD5CryptImpl {
	public String cryptMd5(String source) {
		String s = null;
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(source.getBytes("gbk"));
			byte[] tmp = md.digest();

			char[] str = new char[32];

			int k = 0;
			for (int i = 0; i < 16; ++i) {
				byte byte0 = tmp[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];

				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public String cryptMd5(String source, String key) {
		byte[] k_ipad = new byte[64];
		byte[] k_opad = new byte[64];
		byte[] keyb;
		byte[] value;
		try {
			keyb = key.getBytes("UTF-8");
			value = source.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			keyb = key.getBytes();
			value = source.getBytes();
		}
		Arrays.fill(k_ipad, keyb.length, 64, new Integer(54).byteValue());
		Arrays.fill(k_opad, keyb.length, 64, new Integer(92).byteValue());
		for (int i = 0; i < keyb.length; ++i) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5C);
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte[] dg = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	public static String toHex(byte[] input) {
		if (input == null) {
			return null;
		}
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; ++i) {
			int current = input[i] & 0xFF;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	public static void main(String[] args) {
		String source = "00http://172.31.90.79:8080/ec-pay/receivePayReturn/pingan.htmlhttp://172.31.90.79:8080/ec-pay/receivePayNotify/pingan.htmlHCardDirectPayment1.0MD5172.31.90.7920140917800021000050002www.j1.com20140917141094131630910032491409171608312200[{SortCode:D01,SortAmount:12},{SortCode:D06,SortAmount:10}][{code:D01,name:樱桃提取物复合软胶囊,price:12,number:1}]031";
		String key = "ONLn2Iubd1Czm3yD3qVY7vUYMx52RBQ0hzHEca4j9iJx2UXINBDkze7LRDR88fHe";
		MD5CryptImpl impl = new MD5CryptImpl();
		String md5 = impl.cryptMd5(source+key);
		System.out.println(md5);
		System.out.println(System.getProperty("file.encoding"));
	}
}