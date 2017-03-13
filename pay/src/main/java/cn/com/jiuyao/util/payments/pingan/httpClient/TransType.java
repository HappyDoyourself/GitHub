package cn.com.jiuyao.util.payments.pingan.httpClient;

/**
 * Created by fanhongtao
 * Date 2017-01-18 09:37
 */
public class TransType {
    public static String CONSUMPTION="001";  //平安下单 001 消费
    public static String REFUND="002";       //002 退货
    public static String TIMESTAMP="003";    //003 获取时间
    public static String VERIFY="004";       //004 通知有效性校验
    public static String QUERY="005";        // 005 信息查询


    public static String YAOPIN = "D01"; //D01-药品
    public static String BAOJIANPIN = "D02"; //D02-保健品
    public static String YLQX = "D03"; //医疗器械
    public static String BJFW = "D04"; //D04-保健服务
    public static String JSFW = "D05"; //D05-健身服务
    public static String QT = "D06"; //D06其它 D06不在健康卡支付范围内
    public static String TJFW = "D07"; //D07-体检服务

    public static String CHANNEL = "pc"; //请求方式

    public static String YUNFEI = "运费"; //运费
    public static String NUMBER = "1";  //运费数量
}
