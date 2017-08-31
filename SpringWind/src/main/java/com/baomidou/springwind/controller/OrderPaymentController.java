package com.baomidou.springwind.controller;

import com.baomidou.framework.annotations.Log;
import com.baomidou.framework.controller.SuperController;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.common.Constant;
import com.baomidou.springwind.entity.Order;
import com.baomidou.springwind.entity.OrderPayment;
import com.baomidou.springwind.entity.OrderPaymentLog;
import com.baomidou.springwind.entity.PayParam;
import com.baomidou.springwind.service.IOrderPayMentService;
import com.baomidou.springwind.service.IOrderPaymentLogService;
import com.baomidou.springwind.service.IOrderService;
import com.baomidou.springwind.service.IPayParamService;
//import com.baomidou.springwind.service.impl.OrderPaymentServiceImpl;
import com.baomidou.springwind.service.impl.OrderPaymentServiceLogImpl;
import com.baomidou.springwind.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fht on 2017-05-25 21:52.
 */

@Controller
@RequestMapping(value = "/orderPayment/")
public class OrderPaymentController extends SuperController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IOrderPayMentService orderPayMentService;
    @Autowired
    private IOrderPaymentLogService orderPaymentLogService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IPayParamService payParamService;


    @Login(action = Action.Skip)
    @Permission(action =  Action.Skip)
    @RequestMapping(value = "/wxPay")
    public String pay(HttpServletRequest request, HttpServletResponse response,Model model){
       //获取提交的参数
        System.out.println("=======================");
        String orderId = request.getParameter("orderId");
        String paymentTypeNo = "wexin";
       /* String businessType = request.getParameter("businessType");
        String paymentFee = request.getParameter("paymentFee");
        //String channel = request.getParameter("channel");
        String returnUrl = request.getParameter("returnUrl");
        String paymentModeCode=request.getParameter("paymentModeCode");*/
        OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
        Order order = orderService.selectById(orderId);

        //OrderPayment orderPayment = orderPayMentService.selectById(orderId);
        try {
            String veryfyOrder = verifyOrder(request,response,order);
            if(StringUtils.isNotBlank(veryfyOrder)){
               // request.setAttribute("returnUrl",returnUrl);
                return veryfyOrder;
            }
            //orderPaymentLog.setStartTime(orderInfo.getCommitTime());
           // orderPaymentLog.setPaymentFee(order.getOrderAmount());
            // 订单支付方式优惠计算(不支持多支付方式支付：如果订单已经使用其他支付方式进行过支付，则不进行订单支付方式优惠计算)
          /*  Double sumPaid = myPayService.getPaidFeeSumByLog(orderPaymentLog);
            if (null == sumPaid) {
                BigDecimal promotePayFee = paymentPormote(orderPaymentLog);
                //orderPaymentLog.setPaymentFee(promotePayFee); 摄者paidFee ，paidFee和paymentFee 的关系
                orderPaymentLog.setPaidFee(promotePayFee);//实付金额
            }*/
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        orderPaymentLog.setOrderPaymentId(Long.valueOf(orderId));
        orderPaymentLog.setPaymentFee(order.getOrderAmount());
        orderPaymentLog.setAddTime(DateUtils.getCurrentDate());
        orderPaymentLog.setUpdateTime(DateUtils.getCurrentDate());

        List<PayParam> payParamList = new ArrayList();
        Map<String,Object> map = new HashMap<>();
        map.put("pay_id","1");
        payParamList = payParamService.selectByMap(map);
        Map<String,String> payParamMap = new HashMap<>();
        for(PayParam param : payParamList){
            payParamMap.put(param.getPayParamKey(),param.getPayParamValue());
        }
        orderPaymentLog.setPayParam(payParamMap);
       String qrPage =  orderPayMentService.wxPay(request,orderPaymentLog);
       if (StringUtils.isBlank(qrPage)){
           //返回错误提示页面
           logger.error("=================" + paymentTypeNo + " orderId:" + orderId + ": platform.requestMessagePackage error");
           request.setAttribute("paidSuccess", "对不起，支付初始化失败，请重新支付。" + request.getAttribute("paidSuccess"));
           request.setAttribute("orderId", orderId);
           request.setAttribute("payFlag", "failure");
           //request.setAttribute("returnUrl", returnUrl);
           return "weixinpay/wxpay";
       }
        // 添加请求信息日志
       // this.paymentMessageLog(orderPaymentLog,"","");
        return "/weixinpay/wxpay";
    }



    /**
     * 校验订单有效性
     *
     * @param request
     * @author suqun
     * @throws IOException
     * @throws ServletException
     */
    public String verifyOrder(HttpServletRequest request,
                              HttpServletResponse response,Order order) throws ServletException, IOException {
        String jspUrl = "";
        // 判断支付的是否自己的订单
        /*if (null == orderInfo
                || !orderInfo.getMemberId().toString().equals(
                request.getParameter("memberId"))) {
            logger.error("-----------------订单有效性校验:支付的不是自己的订单"+"orderId:"+request.getParameter("orderId"));
            request.setAttribute("paidSuccess", "对不起，支付初始化失败，请重新支付");
            request.setAttribute("orderId", request.getParameter("orderId"));
            request.setAttribute("payFlag", "failure");
            jspUrl = "jsp/myec/pay_done.jsp";
            return jspUrl;
        }*/
        // 判断是否已支付
        if (Constant.PAY_SUCCESS ==order.getOrderState()) {
            OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
            orderPaymentLog.setOrderPaymentId(order.getId());
           /* orderPaymentLog = myPayService.findOrderMaxTime(orderPaymentLog);
            request.setAttribute("paymentNo", orderPaymentLog.getPaymentNo());
            request.setAttribute("payFlag", "success");*/
            jspUrl = "jsp/myec/pay_done.jsp";
            return jspUrl;
        }
        // 订单是否已经取消
       /* if (Constant.PAY_CANCEL ==
                .equals(OrderBusinessType.ORDER)) {
            if (orderInfo.getCodeNo().equals("cancel")) {
                logger.error("-----------------订单有效性校验:订单已取消"+"orderId:"+request.getParameter("orderId"));
                request.setAttribute("paidSuccess", "对不起，当前订单已经取消，请重新下单");
                request.setAttribute("orderId", orderInfo.getOrderNo());
                request.setAttribute("payFlag", "failure");
                jspUrl = "jsp/myec/pay_done.jsp";
                return jspUrl;
            }
        }*/
        return jspUrl;
    }

    /**
     * 记录向银行等支付平台发送的请求信息
     *
     * @param messageType 信息类型 request向银行发送请求，response银行响应信息
     * @param message	请求或响应信息内容
     */
    public void paymentMessageLog(OrderPaymentLog paymentLog,String messageType,String message) {
        logger.debug("====================添加请求信息日志");
        String curTime =     DateUtils.longToDateAll(System.currentTimeMillis());
       // OrderPaymentMessageLog orderPaymentMessageLog = new OrderPaymentMessageLog();
        paymentLog.setOrderPaymentParam(message);
      /*  orderPaymentMessageLog.setPaymentLogId(paymentLogId);
        orderPaymentMessageLog.setMessageType(messageType);
        orderPaymentMessageLog.setResponseType(responseType);*/
        if(message.length()>3600){
            message = message.substring(0,3600);
        }
        paymentLog.setOrderPaymentParam(message);
        paymentLog.setMessageType(messageType);
       /* orderPaymentMessageLog.setMessage(message);
        orderPaymentMessageLog.setIsDelete("N");
        orderPaymentMessageLog.setAddTime(curTime);
        orderPaymentMessageLog.setAddUserId(Integer.parseInt(memberId));*/
        paymentLog.setAddTime(DateUtils.addDays(System.currentTimeMillis()));
        paymentLog.setUpdateTime(DateUtils.addDays(System.currentTimeMillis()));
        orderPaymentLogService.insertOrUpdate(paymentLog);
    }
}
