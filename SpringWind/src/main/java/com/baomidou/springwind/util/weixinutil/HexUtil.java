package com.baomidou.springwind.util.weixinutil;

/**
 * Created by Larry on 2015/1/7.
 */
public class HexUtil {

    private static String hexStr =  "0123456789ABCDEF";

    /**
     * 字节数组转16进制
      * @param b 字节数组
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] b) {
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            returnValue.append(hex.toUpperCase());
        }
        return returnValue.toString();
    }

    /**
     * 十六进制转换为字节数组
     * @param hexString 16进制字符串
     * @return 字节数组
     */
    public static byte[] hexString2Bytes(String hexString){
        //hexString的长度对2取整，作为bytes的长度
        int len = hexString.length()/2;
        byte[] bytes = new byte[len];
        byte high = 0;//字节高四位
        byte low = 0;//字节低四位

        for(int i=0;i<len;i++){
            //右移四位得到高位
            high = (byte)((hexStr.indexOf(hexString.charAt(2*i)))<<4);
            low = (byte)hexStr.indexOf(hexString.charAt(2*i+1));
            bytes[i] = (byte) (high|low);//高地位做或运算
        }
        return bytes;
    }
}
