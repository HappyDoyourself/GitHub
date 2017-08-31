package com.baomidou.springwind.util;

import java.io.UnsupportedEncodingException;

/**
 * Created by fht on 2017-06-29 19:06.
 */
public class ChsetTransUtils {
    /**
     * ISO转码utf-8
     * @param Iso
     * @return
     */
    public static String strIsoToUtf(String Iso){
         String strUtf = "";
        try {
            strUtf = new String(Iso.getBytes("iso-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strUtf;
    }
}
