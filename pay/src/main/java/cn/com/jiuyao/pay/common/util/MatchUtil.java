package cn.com.jiuyao.pay.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Larry on 2015/9/16.
 * 正则表达式校验
 */
public class MatchUtil {
    /**
     * 正则表达式校验方法
     *
     * @param regex 正则表达式
     * @param value 需校验的数据
     * @return boolean true 校验通过
     */
    public static boolean matche(String regex, String value){
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(value.trim());
        return matcher.matches();
    }
}
