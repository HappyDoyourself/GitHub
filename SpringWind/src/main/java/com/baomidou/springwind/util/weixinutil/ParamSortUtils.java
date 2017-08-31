package com.baomidou.springwind.util.weixinutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by fht on 2017-06-18 15:28.
 */
public class ParamSortUtils {
    public static String getContentByParameterMap(Map params) {
        // key从a到z的顺序排序
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);

        String prestr = "";
        boolean first = true;
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = ((String[]) params.get(key))[0];
            // 空值参数不拼接
            if (value == null || value.trim().length() == 0) {
                continue;
            }
            if (first) {
                prestr = prestr + key + "=" + value;
                first = false;
            } else {
                prestr = prestr + "|" + key + "=" + value;
            }
        }
        return prestr;
    }
}
