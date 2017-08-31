package com.baomidou.springwind.service.support;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.springwind.entity.OrderPaymentLog;
import com.baomidou.springwind.entity.WeixinPay;
import com.baomidou.springwind.util.weixinutil.*;
import org.jdom.JDOMException;
import org.jdom.input.JDOMParseException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {
    /**
     * 获取微信支付基本参数
     * @param request
     * @param orderPaymentLog
     * @param trade_type 微信支付固定参数，视支付方式而定
     * @return
     * @throws UnsupportedEncodingException
     */
    public WeixinPay getParas(HttpServletRequest request,OrderPaymentLog orderPaymentLog, String trade_type) throws UnsupportedEncodingException{
        WeixinPay pay = new WeixinPay();
        Map<String, String> parameter = orderPaymentLog.getPayParam();
        /*******************************************************获取支付参数*********************************************/
       String out_trade_no = "ZM" + String.valueOf(orderPaymentLog.getOrderPaymentId());
        //  String out_trade_no = String.valueOf(orderPaymentLog.getOrderPaymentId());
        String appId = parameter.get("appId");// 微信开发平台应用id
        pay.setAppId(appId);
        String appKey = parameter.get("appKey");
        pay.setAppKey(appKey);
        String mch_id = parameter.get("mch_id");
        pay.setMch_id(mch_id);
        String partnerId = parameter.get("partnerId");
        //pay.setPartnerId(partnerId);
       // String notify_url = "http://localhost:8080";
        String notify_url = parameter.get("notify_url");
       // String notify_url = orderPaymentLog.getPay().getReturnUrl();//异步通知url
        orderPaymentLog.setReturnUrl(notify_url);
        //body内容
        String body =  out_trade_no;
//		body= new String(body.getBytes("utf-8"),"iso8859-1");
        pay.setBody(body);
        //随机字符串
        String nonce_str = TenpayUtil.getNonceStr();
        pay.setNoncestr(nonce_str);
        // 订单总价（单位：分）
        String total_fee = orderPaymentLog.getPaymentFee().setScale(0).toString();
        pay.setTotal_fee(total_fee);
        //支付机器IP
        String ip = IPUtil.getIpAddr(request);
        pay.setIp(ip);
        //生成支付请求sign值
        Map<String, String> mapPara = new LinkedHashMap<String, String>();
        mapPara.put("appid", appId);
        mapPara.put("body", body);//商品描述
        mapPara.put("mch_id", mch_id);
        mapPara.put("nonce_str", nonce_str);
        mapPara.put("notify_url", notify_url);
        if (trade_type != null && "JSAPI".equals(trade_type)){
            mapPara.put("openid",request.getParameter("openid"));
        }
        mapPara.put("out_trade_no", out_trade_no);
        mapPara.put("spbill_create_ip", ip);//支付机器IP
        mapPara.put("total_fee", total_fee);//商品总金额,以分为单位
        mapPara.put("trade_type",trade_type);
        pay.setMapPara(mapPara);
        return pay;
    }

    /**
     * 创建微信支付订单（统一下单接口）,方法内按微信规范签名，无需传签名参数
     * @param orderPaymentLog 支付对象
     * @param mapPara 微信接口参数（参考对应微信文档）
     * @param appKey 应用key
     * @return 解析XML后转为map获取返回结果
     * @throws JDOMParseException 返回结果解析错误
     */
    public Map<String,String> createWeiXinOrder(OrderPaymentLog orderPaymentLog,Map<String,String> mapPara,String appKey) throws JDOMParseException {
        Map<String,String> map = new HashMap<String, String>();
        try {
            String paySign = WXUtil.createSign(mapPara, appKey);
            mapPara.put("sign", paySign);
            //响应post数据给微信
            String weiXinXml = XMLUtil.getXmlForMap(mapPara);
            //记录请求日志
          //  paymentMessageLog(orderPaymentLog, "request","init", weiXinXml);
            String content="";
            try {
                content = HttpClientUtils.do_post("https://api.mch.weixin.qq.com/pay/unifiedorder", weiXinXml);
                //content = HttpClientUtils.doPost("https://api.mch.weixin.qq.com/pay/unifiedorder", mapPara);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //记录响应日志
           // paymentMessageLog(orderPaymentLog, "response","initResponse", content);

            map = XMLUtil.doXMLParse(content);
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        catch(JDOMException jdom){
            jdom.printStackTrace();
        }
        return map;
    }
}
