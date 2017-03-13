package cn.com.jiuyao.util.payments.ehk;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by fanhongtao
 * Date 2017-01-12 18:28
 */
public class EhkMd5 {
    /**
     * 易汇金md5加密方式
     * @param source
     * @param key
     * @return
     */
    public static String signMd5(String source, String key) {
        byte[] k_ipad = new byte[64];
        byte[] k_opad = new byte[64];

        byte[] keyb;
        byte[] value;
        try {
            keyb = key.getBytes("UTF-8");
            value = source.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var9) {
            keyb = key.getBytes();
            value = source.getBytes();
        }

        Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
        Arrays.fill(k_opad, keyb.length, 64, (byte)92);

        for(int md = 0; md < keyb.length; ++md) {
            k_ipad[md] = (byte)(keyb[md] ^ 54);
            k_opad[md] = (byte)(keyb[md] ^ 92);
        }

        MessageDigest var10 = null;

        try {
            var10 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var8) {
            return null;
        }

        var10.update(k_ipad);
        var10.update(value);
        byte[] dg = var10.digest();
        var10.reset();
        var10.update(k_opad);
        var10.update(dg, 0, 16);
        dg = var10.digest();
        return Hex.encodeHexString(dg);
    }
}
