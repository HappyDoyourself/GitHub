package com.baomidou.springwind.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.OrderPayment;
import com.baomidou.springwind.entity.OrderPaymentLog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fht on 2017-05-25 21:55.
 */
public interface IOrderPayMentService extends IService<OrderPayment>{
    /**
     * 微信支付 唤出二维码
     * @param orderPayment
     * @return
     */
    String wxPay(HttpServletRequest request,
                  OrderPaymentLog orderPaymentLog);
}
