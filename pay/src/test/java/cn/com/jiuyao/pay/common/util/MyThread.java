package cn.com.jiuyao.pay.common.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by fanhongtao
 * Date 2017-02-06 09:24
 */
public class MyThread  extends Thread{
    @Override
    public void run() {
        System.out.println(""+ThreadLocalRandom.current().nextDouble());
        super.run();
    }
}
