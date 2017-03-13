package cn.com.jiuyao.pay.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取当前系统时间工具
 * 
 * @author Lichen.Zheng
 * @email 914001269@qq.com
 * @date 2016-7-25 下午12:24:59
 */
public class GetNowTime {
    public static String getCurrentTime() {  
        String returnStr = null;  
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = new Date();  
        returnStr = f.format(date);  
        return returnStr;  
    }  
}
