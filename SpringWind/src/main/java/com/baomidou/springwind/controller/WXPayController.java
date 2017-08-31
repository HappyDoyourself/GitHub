package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springwind.common.Constant;
import com.baomidou.springwind.entity.Activity;
import com.baomidou.springwind.entity.Order;
import com.baomidou.springwind.entity.OrderPaymentLog;
import com.baomidou.springwind.entity.PayParam;
import com.baomidou.springwind.service.IActivityService;
import com.baomidou.springwind.service.IOrderPaymentLogService;
import com.baomidou.springwind.service.IOrderService;
import com.baomidou.springwind.service.IPayParamService;
import com.baomidou.springwind.util.DateUtils;
import com.baomidou.springwind.util.weixinutil.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fht on 2017-06-08 20:30.
 */
@Controller
@RequestMapping("/wxpay/")
public class WXPayController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IOrderPaymentLogService orderPaymentLogService;

    @Autowired
    private IPayParamService payParamService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IActivityService activityService;

    /**
     * 支付完成后获取支付平台返回信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping(value = "/wxpay")
    public String WXPay(HttpServletRequest request, Model model) throws Exception {
        String returnUrl = request.getParameter("returnUrl");
        String content = request.getParameter("content");
        String paymentNo = request.getParameter("paymentNo");
        String paymentFee = request.getParameter("paymentFee");
        String orderId = request.getParameter("orderId");
        request.setAttribute("returnUrl", returnUrl);
        request.setAttribute("content", content);
        request.setAttribute("paymentNo", paymentNo);
        request.setAttribute("paymentFee", paymentFee);
        request.setAttribute("orderId", orderId);
       // String orderId = request.getParameter("orderId");
        Order order = orderService.selectById(Long.valueOf(orderId));
        order.setOrderAmount(order.getOrderAmount().divide(new BigDecimal(100)));
        model.addAttribute("order",order);
        return "weixinpay/wxpayImage";
    }

    /**
     * 循环获取订单支付状态
     *
     * @param request
     * @param response
     * @return
     */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping(value = "/wxpay/poll")
    @ResponseBody
    public String poll(HttpServletRequest request,
                       HttpServletResponse response) {
        String result = null;
        try {
            String paymentNo = request.getParameter("paymentNo");
            OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
            orderPaymentLog.setOrderPaymentId(Long.valueOf(paymentNo));
            orderPaymentLog = orderPaymentLogService.selectOne(new EntityWrapper<OrderPaymentLog>(orderPaymentLog));
            if (null != orderPaymentLog && StringUtils.isNotEmpty(orderPaymentLog.getBackState())) {
                if ("SUCCESS".equals(orderPaymentLog.getBackState())) {
                    result = "SUCCESS";
                }
            }
//			String key = "WX"+paymentNo;
//			if((Boolean)cache.get(key)){
//				result = "SUCCESS";
//			}
        } catch (Exception e) {
            logger.error("微信轮询获取支付结果信息异常！");
        }

        return result;
    }


    /**
     * 银行或第三方同步返回信息接收
     *
     * @param request       request
     * @param response      response
     * @param paymentTypeNo 支付方式编码
     * @throws Exception 说明：根据paymentTypeNo跳转到具体的返回信息处理类
     */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping(value = "/receivePayReturn/{paymentTypeNo}")
    public String receivePayReturn(HttpServletRequest request,
                                 HttpServletResponse response, @PathVariable String paymentTypeNo)
            throws Exception {
      return   this.baseNotifyMessage(request, response, paymentTypeNo);

    }

    /**
     * 银行或第三方异步通知信息接收
     *
     * @param request       request
     * @param response      response
     * @param paymentTypeNo 支付方式编码
     * @throws Exception 说明：根据paymentTypeNo跳转到具体的返回信息处理类
     */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping(value = "/receivePayNotify/{paymentTypeNo}")
    public void receivePayNotify(HttpServletRequest request,
                                 HttpServletResponse response, @PathVariable String paymentTypeNo)
            throws Exception {
        returnMessageHandle(request,response,paymentTypeNo);
        // 根据paymentTypeNo跳转到具体的返回信息处理类
        //Platform platform = Factory.createPlatform(paymentTypeNo);
      //  String url = platform.notifyMessageHandle(request, response, paymentTypeNo);
        //部分支付平台直接异步通知后，然后跳转到同步返回的页面进行信息提示，url为同步返回链接
        /*if (!StringUtil.isEmpty(url)) {
            response.sendRedirect(url);
        }*/
    }


    /**
     * 微信支付异步通知接口
     * @param request
     * @param response
     * @param paymentTypeNo
     * @return
     */
    public String baseNotifyMessage(HttpServletRequest request,
                                    HttpServletResponse response,String paymentTypeNo){
        try {
            // 取得支付参数
            Map<String,Object> mapPay = new HashMap<>();
            mapPay.put("pay_id","1");
            List<PayParam> payParamList = new ArrayList();
            payParamList = payParamService.selectByMap(mapPay);
            Map<String,String> payParamMap = new HashMap<>();
            for(PayParam param : payParamList){
                payParamMap.put(param.getPayParamKey(),param.getPayParamValue());
            }

            // 获取收到的报文
            String xmlContent = HttpClientUtil.InputStreamTOString(request.getInputStream(), TenpayUtil.getCharacterEncoding(request, response));
            //解析xml,得到map
            Map<String,String> map = XMLUtil.doXMLParse(xmlContent);
            String AppSignature = map.get("sign").toString();//参数的加密签名
            //财付通支付应答（处理回调）示例，商户按照此文档进行开发即可
            ResponseHandler resHandler = new ResponseHandler(request, response);
            String partnerKey = payParamMap.get("appKey");
            String appKey = payParamMap.get("appKey");
            resHandler.setKey(partnerKey);
            /** GET 接收财付通的数据*/
           // Map requestParameterMap = request.getParameterMap();
            //判断签名
           // String enc = TenpayUtil.getCharacterEncoding(request, response);//算出摘要
            if(!(WXUtil.isWeixinSign(map, AppSignature, appKey))) {
                logger.error("=====weixin====通知签名验证失败");
                resHandler.sendToCFT("fail");
                return null;
            }
            //支付返回值
            String trade_state = map.get("return_code");//交易状态 SUCCESS/FAIL
            String trade_mode = map.get("result_code");//业务结果
            String total_fee = map.get("total_fee");//通知的 total_fee+ discount = 请求的total_fee
            String out_trade_no =map.get("out_trade_no");//商户系统的订单号
            String transaction_id = map.get("transaction_id");//交易号， 28 位长的数值， 其中前 10 位为商户号， 之后 8 位为订单产生的日 期，如 20090415，最后10 位是流水号。
            logger.info("支付返回值:return_code=%s,result_code=%s,total_fee=%s,out_trade_no=%s,out_trade_no=%s,transaction_id=%s",
                    String.format(trade_state,trade_mode,total_fee,out_trade_no,transaction_id));
            BigDecimal totalFee = new BigDecimal(total_fee);
            // 获取订单日志信息和订单信息
            OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
            orderPaymentLog.setOrderPaymentId(Long.valueOf(out_trade_no));
           // orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
           // orderPaymentLog = myPayService.findOrderPaymentLog(orderPaymentLog,false);
            Map<String,Object> mapQueryOrderLog = new HashMap<>();
            mapQueryOrderLog.put("order_payment_id",Long.valueOf(out_trade_no));
            orderPaymentLog = (OrderPaymentLog) orderPaymentLogService.selectByMap(mapQueryOrderLog).get(0);
            // 拼接记录order_payment_message_log
           // String message =ParamSortUtils.getContentByParameterMap(request.getParameterMap());
          //  paymentMessageLog(orderPaymentLog, "response","notify", message);
            // 订单有效性校验
            String be = checkPamentBackBeforeSign(request, orderPaymentLog);
            if (!be.equals("")) {
                resHandler.sendToCFT("success");
                return "";
            }
            //判断签名及结果
            //BigDecimal realAmt = totalFee.divide(new BigDecimal(100));
            orderPaymentLog.setBackState(trade_state);
            orderPaymentLog.setReturnOrderNo(transaction_id);
            orderPaymentLog.setPaymentFee(totalFee);
            if ("SUCCESS".equals(trade_state) && "SUCCESS".equals(trade_mode)) {
                //doSaveResultByLog(orderPaymentLog);
                orderPaymentLog.setPayState(Constant.PAY_SUCCESS);
                orderPaymentLogService.insert(orderPaymentLog);
                Order order = orderService.selectById(orderPaymentLog.getOrderPaymentId());
                order.setOrderState(Constant.PAY_SUCCESS);
                order.setUpdateTime(DateUtils.getCurrentDate());
                orderService.updateById(order);
                //人数减一
                long activityId = order.getActivityId();
                Activity activity = activityService.selectById(activityId);
                activity.setPlaces(activity.getPlaces()-1);
                activity.setUpdateTime(DateUtils.getCurrentDate());
                activityService.insertOrUpdate(activity);
            } else {
                orderPaymentLog.setPayState(Constant.PAY_SUCCESS);
                orderPaymentLogService.insert(orderPaymentLog);
                Order order = orderService.selectById(orderPaymentLog.getOrderPaymentId());
                order.setOrderState(Constant.PAY_SUCCESS);
                order.setUpdateTime(DateUtils.getCurrentDate());
                orderService.updateById(order);
            }
            resHandler.sendToCFT("success");
            //返回处理完成
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
            response.getWriter().flush();
            response.getWriter().close();
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/order/yuding_success";
    }

    /**
     * 签名前校验订单有效性
     *
     * @param request
     * @param orderPaymentLog
     * @return
     */
    public String checkPamentBackBeforeSign(HttpServletRequest request,OrderPaymentLog orderPaymentLog) {
        String result = "";
        if (orderPaymentLog == null) {
            request.setAttribute("paidSuccess", "抱歉没有此支付订单号对应的记录！");
            request.setAttribute("orderId", "无此单号");
            request.setAttribute("paymentNo", orderPaymentLog.getOrderPaymentId()); // 支付订单号
            request.setAttribute("payFlag", "failure");
            result = "/error/404.html";
        }
        if (orderPaymentLog.getPayBehavior() == Constant.PAY) {
            Order orderInfo = orderService.selectById(orderPaymentLog.getOrderPaymentId());
            if (orderInfo == null) {
                request.setAttribute("paidSuccess", "抱歉没有支付订单号对应的订单！");
                request.setAttribute("orderId", "无此单号");
                request.setAttribute("paymentNo", orderPaymentLog.getOrderPaymentId()); // 支付订单号
                request.setAttribute("payFlag", "failure");
                result = "/error/404.html";
            }
            //如果订单已经支付成功则不需要再次更改
            else if (orderInfo.getOrderState() == Constant.PAY_SUCCESS){
                logger.info("订单已支付,订单Id= " + orderInfo.getId());
                result = "/error/404.html";
            }
        }
        return result;
    }


    /**
     *  支付回调商户后台获取package
     */
    public String returnMessageHandle(HttpServletRequest request,
                                      HttpServletResponse response, String paymentTypeNo) {
        try {
            // 取得支付参数
            Map<String,Object> mapPay = new HashMap<>();
            mapPay.put("pay_id","1");
            List<PayParam> payParamList = new ArrayList();
            payParamList = payParamService.selectByMap(mapPay);
            Map<String,String> payParamMap = new HashMap<>();
            for(PayParam param : payParamList){
                payParamMap.put(param.getPayParamKey(),param.getPayParamValue());
            }

            String partnerKey = payParamMap.get("appKey");
            String appKey = payParamMap.get("appKey");

            // 获取收到的报文
            String xmlContent = HttpClientUtil.InputStreamTOString(request.getInputStream(), TenpayUtil.getCharacterEncoding(request, response));

            //解析xml,得到map
            Map<String, String> map = XMLUtil.doXMLParse(xmlContent);
            String AppSignature = map.get("sign").toString();//参数的加密签名

            //财付通支付应答（处理回调）示例，商户按照此文档进行开发即可
            ResponseHandler resHandler = new ResponseHandler(request, response);
            resHandler.setKey(partnerKey);

            /** GET 接收财付通的数据*/
            Map requestParameterMap = request.getParameterMap();

            //判断签名
            String enc = TenpayUtil.getCharacterEncoding(request, response);//算出摘要
            if (!(WXUtil.isWeixinSign(map, AppSignature, appKey))) {
                logger.error("=====weixin====通知签名验证失败");
                resHandler.sendToCFT("fail");
                return null;
            }
            //支付返回值
            String trade_state = map.get("return_code");//交易状态 SUCCESS/FAIL
            String trade_mode = map.get("result_code");//业务结果
            String total_fee = map.get("total_fee");//通知的 total_fee+ discount = 请求的total_fee
            String out_trade_no = map.get("out_trade_no");//商户系统的订单号
            String transaction_id = map.get("transaction_id");//交易号， 28 位长的数值， 其中前 10 位为商户号， 之后 8 位为订单产生的日 期，如 20090415，最后10 位是流水号。
            BigDecimal totalFee = new BigDecimal(total_fee);

            // 获取订单日志信息和订单信息
            OrderPaymentLog orderPaymentLog = new OrderPaymentLog();
            orderPaymentLog.setOrderPaymentId(Long.valueOf(out_trade_no));
           // orderPaymentLog.setPaymentTypeId(ecPaymentType.getPaymentTypeId());
            Map<String,Object> mapQueryOrderLog = new HashMap<>();
            mapQueryOrderLog.put("order_payment_id",Long.valueOf(out_trade_no));
            orderPaymentLog = (OrderPaymentLog) orderPaymentLogService.selectByMap(mapQueryOrderLog).get(0);
            // 拼接记录order_payment_message_log
         /*   String message = getContentByParameterMap(request.getParameterMap());
            paymentMessageLog(orderPaymentLog, "response", "notify", message);*/
            // 订单有效性校验
            String be = checkPamentBackBeforeSign(request, orderPaymentLog);
            if (!be.equals("")) {
                resHandler.sendToCFT("success");
                return "";
            }

            //判断签名及结果
            orderPaymentLog.setBackState(trade_state);
            orderPaymentLog.setReturnOrderNo(transaction_id);
            orderPaymentLog.setPaymentFee(totalFee);
            if ("SUCCESS".equals(trade_state) && "SUCCESS".equals(trade_mode)) {
                //doSaveResultByLog(orderPaymentLog);
                orderPaymentLog.setPayState(Constant.PAY_SUCCESS);
                orderPaymentLogService.insert(orderPaymentLog);
                Order order = orderService.selectById(orderPaymentLog.getOrderPaymentId());
                order.setOrderState(Constant.PAY_SUCCESS);
                order.setUpdateTime(DateUtils.getCurrentDate());
                orderService.updateById(order);
                //人数减一
                long activityId = order.getActivityId();
                Activity activity = activityService.selectById(activityId);
                activity.setPlaces(activity.getPlaces()-1);
                activity.setUpdateTime(DateUtils.getCurrentDate());
                activityService.insertOrUpdate(activity);
            } else {
                orderPaymentLog.setPayState(Constant.PAY_SUCCESS);
                orderPaymentLogService.insert(orderPaymentLog);
                Order order = orderService.selectById(orderPaymentLog.getOrderPaymentId());
                order.setOrderState(Constant.PAY_SUCCESS);
                order.setUpdateTime(DateUtils.getCurrentDate());
                orderService.updateById(order);
            }
            resHandler.sendToCFT("success");
            //返回处理完成
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
            response.getWriter().flush();
            response.getWriter().close();
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
