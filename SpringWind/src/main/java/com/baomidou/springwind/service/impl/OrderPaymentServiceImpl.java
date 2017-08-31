package com.baomidou.springwind.service.impl;

import com.baomidou.springwind.common.Constant;
import com.baomidou.springwind.entity.OrderPayment;
import com.baomidou.springwind.entity.OrderPaymentLog;
import com.baomidou.springwind.entity.WeixinPay;
import com.baomidou.springwind.mapper.OrderPaymentMapper;
import com.baomidou.springwind.service.IOrderPayMentService;
import com.baomidou.springwind.service.IOrderPaymentLogService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import com.baomidou.springwind.util.DateUtils;
import com.baomidou.springwind.util.weixinutil.HexUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by fht on 2017-05-27 06:13.
 */
@Service
public class OrderPaymentServiceImpl extends BaseServiceImpl<OrderPaymentMapper,OrderPayment> implements IOrderPayMentService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IOrderPaymentLogService logService;

    @Override
    public String wxPay(HttpServletRequest request,
                         OrderPaymentLog orderPaymentLog) {
        //查询出 支付需要的 参数
        //调用微信的api接口
        String message = "";
        try {
            request.setCharacterEncoding("utf-8");
            //判断是否获取过微信二维码链接
            if (StringUtils.isNotEmpty(orderPaymentLog.getQrCode())) {
                request.setAttribute("content", HexUtil.bytes2Hex(orderPaymentLog.getQrCode().getBytes()));
                message = orderPaymentLog.getQrCode();
            }else{
                /*******************************************************获取支付参数*********************************************/
                WeixinPay pay = getParas(request, orderPaymentLog,"NATIVE");

                /*******************************************************创建微信统一下单接口的订单*********************************************/
                Map<String,String> map =createWeiXinOrder(orderPaymentLog, pay.getMapPara(), pay.getAppKey());
                if (map == null || map.size() == 0) {

                    return "";
                }

                //将url传到jsp中，异步生产二维码
                if ("SUCCESS".equals(map.get("return_code"))) {
                    request.setAttribute("content", HexUtil.bytes2Hex(map.get("code_url").getBytes()));
                  //  OrderPaymentLog log = new OrderPaymentLog();
                 //   log.setId(orderPaymentLog.getId());
                    orderPaymentLog.setQrCode(map.get("code_url"));
                    orderPaymentLog.setMessageType(Constant.MESSAGETYPEREQUEST);
                    orderPaymentLog.setAddTime(DateUtils.getCurrentDate());
                    orderPaymentLog.setUpdateTime(DateUtils.getCurrentDate());
                    orderPaymentLog.setPayParam(null);
                    orderPaymentLog.setPay(null);
                    logService.insert(orderPaymentLog);
                }else {
                    return "";
                }
                message = map.toString();
            }

            request.setAttribute("paymentNo", orderPaymentLog.getOrderPaymentId());
            request.setAttribute("returnUrl", orderPaymentLog.getReturnUrl());
            request.setAttribute("paymentFee", orderPaymentLog.getPaymentFee().setScale(2).toString());
            request.setAttribute("orderId", orderPaymentLog.getOrderPaymentId());
            //供二维码页面轮询用，当key值为true时，页面跳转到支付成功页面
//			String key = "WX"+orderPaymentLog.getPaymentNo();
//			boolean b = cache.put(key,false,60);
            /**拼接请求信息*/

        }catch(Exception jdm){
            //jdm.printStackTrace();
            request.setAttribute("paidSuccess", "原因，商户订单号重复");
        }
        return message;
    }
}
