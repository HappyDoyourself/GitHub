package cn.com.jiuyao.pay.common.factory;

import cn.com.dubbo.model.PaymentParam;
import com.jiuyao.ec.common.type.OrderBusinessType;

import cn.com.jiuyao.pay.common.constant.PayChannel;
import cn.com.jiuyao.pay.common.framework.util.J1PayAssert;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2015/12/18.
 * 创建支付参数实例
 * 简单工厂方法
 */
public class PaymentParamFactory {

    public static PaymentParam createPaymentParam(HttpServletRequest request) {
        PaymentParam paymentParam = null;
        //获取提交的参数
        String orderId = request.getParameter("orderId");
        String paymentTypeNo = request.getParameter("paymentTypeNo");
        String token = request.getParameter("token");
        String businessType = request.getParameter("businessType");
        String paymentFee = request.getParameter("paymentFee");
        String channel = request.getParameter("channel");
        String returnUrl = request.getParameter("returnUrl");
        String memberId = request.getParameter("memberId");
        String sign = request.getParameter("sign");
        String commitTime =  request.getParameter("commitTime");

        J1PayAssert.businessType(businessType);
        J1PayAssert.channel(channel);

        //渠道，支付类型不同，构造的参数不同
        if (PayChannel.WEB.getChannel().equals(channel)) {
            if(OrderBusinessType.ORDER.equals(businessType)){
                paymentParam = new PaymentParam.Builder(orderId,businessType,channel)
                        .paymentTypeNo(paymentTypeNo)
                        .memberId(memberId)
                        .commitTime(commitTime)
                        .returnUrl(returnUrl)
                        .sign(sign)
                        .build();
            }
        }

        if (PayChannel.WAP.getChannel().equals(channel)) {
            if(OrderBusinessType.ORDER.equals(businessType)){
                paymentParam = new PaymentParam.Builder(orderId,businessType,channel)
                        .paymentTypeNo(paymentTypeNo)
                        .token(token)
                        .returnUrl(returnUrl)
                        .build();
            }
        }

        if (PayChannel.APP.getChannel().equals(channel)) {
            if(OrderBusinessType.ORDER.equals(businessType)){
                paymentParam = new PaymentParam.Builder(orderId,businessType,channel)
                        .paymentTypeNo(paymentTypeNo)
                        .token(token)
                        .build();
            }
        }

        return paymentParam;
    }
}
