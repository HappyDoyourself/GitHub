package com.baomidou.springwind.common;

import java.io.File;

/**
 * Created by fht on 2017-05-25 20:55.
 */
public class Constant {
    public final static int SMS_SUCCESS = 1; //消息发送成功
    public final static int SMS_FAILED = 0;   //消息发送失败
    public static final int SMS_UNSEND = -1;   //消息未发送

    public static final int ACTIVITY_ON =  1; //活动进行中
    public static final int  ACTIVITY_OFF = 2; //活动已结束

    public final static int PAY_WAITING = 0;  //未支付
    public final static int PAY_SUCCESS = 1;  //已支付
    public final static int PAY_FAILED = 2;  //支付失败
    public final static int PAY_CANCEL = 3;  //取消支付
    public final static int PAY_OVERTIME = 4; //支付超时


    public final static int ZHAOMU = 1; // 招募
    public final static int YUDING = 2; //预定

    public static final int FULLINPAY = 1;//全额支付
    public static final int DEPOSITPAY = 2; //定金支付


    public static final int PAY = 1;// 支付行为：付款
    public static final int REFUNDPAY = 2; //支付行为： 退款



    public static final String MESSAGETYPEREQUEST = "request";  //支付请求
    public static final String MESSAGETYPERESPONSE = "response"; //支付相应

    public static final String JSAPI = "JSAPI"; //微信公众号支付
    public static final  String NATIVE = "NATIVE"; //原生扫码支付
    public static final  String APPPAY = "APP"; //app支付

    public static final String FILE_URL = File.separator + "WEB-INF" +File.separator + "static" + File.separator +"upload" +File.separator +"images" +File.separator; //图片存储路径
    public static final String FILE_URL_SQL =  "/static/upload/images/"; //数据库图片存储路径



}
