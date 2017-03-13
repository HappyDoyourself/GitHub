package cn.com.jiuyao.pay.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanhongtao
 * Date 2017-02-06 10:35
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String,String[]> map=new HashMap<String, String[]>();
        map.put("1",new String[]{"test"});
        map.put("2",new String[]{"aaa"});
        String sss=cn.com.jiuyao.util.payments.pingan.StringUtil.mapArrayToString(map);
        System.out.println(sss);
        System.out.println(sss.indexOf("2=test"));

    }
}
