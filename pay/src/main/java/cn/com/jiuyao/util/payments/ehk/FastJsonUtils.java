package cn.com.jiuyao.util.payments.ehk;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by fanhongtao
 * Date 2017-01-10 14:25
 */
public abstract class FastJsonUtils {
    static final Logger LOGGER = LoggerFactory.getLogger(FastJsonUtils.class);

    public FastJsonUtils() {
    }

    public static JSONObject convert(InputStream inputStream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        boolean len = false;

        try {
            int len1;
            while((len1 = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len1);
            }

            String e = new String(out.toByteArray(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(e);
            out.close();
            return jsonObject;
        } catch (IOException var6) {
            LOGGER.info("", var6);
            return null;
        }
    }
}
