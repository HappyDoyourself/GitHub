package cn.com.jiuyao.pay.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Larry on 2015/6/9.
 * 支付工具类
 */
public class PaymentUtil {
    private final static int MAX_NUM = 999999;//递增最大值
    private final static int ZERO_NUM = 6;//补零后长度
    private static AtomicInteger value = new AtomicInteger();

    public static int increment() {
        if(value.get()>MAX_NUM-1){
            value.set(0);
        }
        return value.incrementAndGet();
    }

    /**
     * 支付流水号生成
     * @return paymentNo 20位
     */
    public static String getPaymentNo(String memberId){
        String paymentNo;
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmm");// 12位日期
        String currTime = outFormat.format(now);
        paymentNo = currTime + RandomUtils.toFixdLengthString(Long.parseLong(increment()+""),ZERO_NUM)
                + memberId.substring(memberId.length()-2);
        return paymentNo;
    }

    public static void main (String args[]){
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println(getPaymentNo("30989"));
                }
            }).start();
        }
        long end = System.currentTimeMillis();
        System.out.println("==========================总耗时：" + (end - start));
    }
}
