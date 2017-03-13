package cn.com.jiuyao.pay.common.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by fanhongtao
 * Date 2017-02-06 09:20
 */
public class Test {
    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            Thread thread=new MyThread();
            thread.start();
            Thread thread1 = new MyThread();
         /*   thread1.join();*/
        }

    }
}
