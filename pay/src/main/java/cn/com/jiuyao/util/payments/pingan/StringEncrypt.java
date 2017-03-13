/**
 * StringEncrypt.java Created by William.Wang in 2013-7-9.
 * Copyright 99Bill Corporation. All rights reserved.
 */
package cn.com.jiuyao.util.payments.pingan;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Description 字符串加密
 * @CreateDate 2013-7-9
 * @Author William
 * @Version 1.0 
 */

public class StringEncrypt {
    /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
     * @param strSrc 要加密的字符串
     * @param encName 加密类型
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String encryptPwd(String strSrc, String encName, String charset) throws UnsupportedEncodingException {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes(charset);
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static String encryptRequest(String request, String merchantKey) throws UnsupportedEncodingException{
        StringBuffer sb = new StringBuffer(filterRequest(request));
        Map<String,String> map = StringUtil.splitStringToMap(sb.toString());
        
        sb.append(merchantKey);
        System.out.println(sb.toString());
        String signMethod = map.containsKey("signMethod") ? map.get("signMethod") : "SHA-256";
        String charset = map.containsKey("charset") ? map.get("charset") : "UTF-8";
        //返回结果
        String encryStr = encryptPwd(sb.toString(), signMethod, charset);
        System.out.println(encryStr);
        return encryStr;
    }
    
    public static String filterRequest(String request){
        StringBuffer sb = new StringBuffer();//存放最终需要加签的字符串
        String[] fields = StringUtil.splitRequest(request);
        List<String> keyList = new LinkedList<String>();//存放升序的key列表
        Map<String, String> valueMap = new HashMap<String, String>();//存放value的map
        if (fields != null && fields.length > 0) {
            for (String field : fields) {
                String fieldKey = field.split("=")[0];
                String fieldValue = "";
                if ("signMethod".equals(fieldKey)) {
                    continue;
                }
                if ("signature".equals(fieldKey)) {
                    continue;
                }
                if ("transId".equals(fieldKey)) {//transId不参与加密
                    continue;
                }
//                if ("sameOrderFlag".equals(fieldKey)) {//sameOrderFlag不参与加密
//                    continue;
//                }
                if(field.split("=").length == 1){
                    continue;
                }else if(field.split("=").length > 2){
                    fieldValue = field.substring(field.split("=")[0].length() + 1);
                    String tempValue = fieldValue.replace("{", "").replace("}", "").replace("[", "").replace("]", "");
                    String[] tempValues =  tempValue.split(",");
                    StringBuffer tempBuffer = new StringBuffer();
                    tempBuffer.append("[");
                    for(int i = 0;i < tempValues.length;i++){
                        if(i != 0){
                            tempBuffer.append(",");
                        }
                        tempBuffer.append("{");
                        tempBuffer.append(filterRequest(tempValues[i]));
                        tempBuffer.append("}");
                    }
                    tempBuffer.append("]");
                    fieldValue = tempBuffer.toString();
                }else{
                    fieldValue = field.split("=")[1];
                }
                //值为空的字段不参与加签
                if (fieldValue == null || "".equals(fieldValue)) {
                    continue;
                }
                valueMap.put(fieldKey, fieldValue);
                keyList.add(fieldKey);
            }
        }
        //排序
        Collections.sort(keyList);
        //拼装加签字符串
        for (String key : keyList) {
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(key).append("=").append(valueMap.get(key));
        }
        return sb.toString();
    }
    
    public static boolean validateSignature(String request, String merchantKey, String signature) throws IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException {
        //返回结果
        return encryptRequest(request, merchantKey).equals(signature);
    }
    
    //antiPhishingTimeStamp=&backEndUrl=http://114.80.125.116:2191/merchant-demo/notify.do&businessScene=0001&charset=UTF-8&commodityList=&customerIdNo=&customerIdType=&customerIp=&customerMerchantId=&customerName=&customerPAFId=&customerRefer=&distributeInfo=&frontEndUrl=http://114.80.125.116:2191/merchant-demo/success.html&language=&merReserved=&merReserved2=&mercOrderNo=15921484910wjj002&mercRetrunPara=&merchantId=900000000261&merchantKey=eaf5086d501644a7bef631e30aacb898&merchantTransDesc=&orderAmount=1&orderCurrency=CNY&orderTime=&origMercOrderNo=&origOrderTraceNo=&platMerchantId=&sameOrderFlag=N&signMethod=SHA-256&signature=06fdd0c833527632d6f94e39a15e49a52f11b35eb5025392c23e67b3054aff6f&specifiedBankNumber=&specifiedPayType=&token=&transCode=0001&transTimeout=&transType=001&version=1.0.0
    //antiPhishingTimeStamp=&backEndUrl=http://114.80.125.116:2191/merchant-demo/notify.do&businessScene=0001&charset=UTF-8&commodityList=&customerIdNo=&customerIdType=&customerIp=&customerMerchantId=&customerName=&customerPAFId=&customerRefer=&distributeInfo=&frontEndUrl=http://114.80.125.116:2191/merchant-demo/success.html&language=&merReserved=&merReserved2=&mercOrderNo=15921484910wjj002&mercRetrunPara=&merchantId=900000000261&merchantKey=eaf5086d501644a7bef631e30aacb898&merchantTransDesc=&orderAmount=1&orderCurrency=CNY&orderTime=&origMercOrderNo=&origOrderTraceNo=&platMerchantId=&sameOrderFlag=N&specifiedBankNumber=&specifiedPayType=&token=&transCode=0001&transTimeout=&transType=001&version=1.0.0
    //backEndUrl=http://112.95.152.49/MpmWeb/paygateway/iapsWapPayNotifyGathering&charset=UTF-8&frontEndUrl=http://112.95.152.49/mtmc/flight/order_ret.html&mercOrderNo=100014040211T857601&mercRetrunPara=14040211T8576&merchantId=900000000009&orderAmount=52000&orderCurrency=CNY&transCode=0001&transType=001&version=1.0.09286ed7a54e94c5e96820896d02c412d
    //charset=UTF-8&merchantId=900000000011&mercOrderNo=526805375&orderAmount=10000&orderCurrency=CNY&signMethod=SHA-256&signature=5f537499ea25223e798aceda505589a727d6498f3516f9f683e3770b1376a5b0&token=811851695716744824&transCode=0005&transType=001&version=1.0.0
    //charset=UTF-8&merchantId=900000000009&mercOrderNo=000151140514095437&orderAmount=10000&orderCurrency=CNY&token=811697058555580287&transCode=0005&transType=001&version=1.0.0
    //charset=UTF-8&mercOrderNo=000151140514095437&merchantId=900000000009&orderAmount=10000&orderCurrency=CNY&token=811697058555580287&transCode=0005&transType=001&version=1.0.09286ed7a54e94c5e96820896d02c412d
    //charset=UTF-8&mercOrderNo=000151140514095437&merchantId=900000000009&orderAmount=10000&orderCurrency=CNY&token=811697058555580287&transCode=0005&transType=001&version=1.0.0
    public static void main(String args[]) throws UnsupportedEncodingException {
        System.out.println(encryptPwd("charset=UTF-8&mercOrderNo=000151140514095437&merchantId=900000000009&orderAmount=10000&orderCurrency=CNY&token=811697058555580287&transCode=0005&transType=001&version=1.0.09286ed7a54e94c5e96820896d02c412d","SHA-256", "UTF-8"));
        System.out.println(StringEncrypt.encryptRequest("charset=UTF-8&mercOrderNo=000151140514095437&merchantId=900000000009&orderAmount=10000&orderCurrency=CNY&token=811697058555580287&transCode=0005&transType=001&version=1.0.0", "9286ed7a54e94c5e96820896d02c412d"));
    }
}
