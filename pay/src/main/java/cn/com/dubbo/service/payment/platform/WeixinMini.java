package cn.com.dubbo.service.payment.platform;

import cn.com.dubbo.model.OrderPaymentLog;
import cn.com.dubbo.model.WeixinPay;
import cn.com.jiuyao.util.payments.weixin.TenpayUtil;
import cn.com.jiuyao.util.payments.weixin.WXUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fanhongtao
 * Date 2017-03-08 09:31
 * 微信小程序支付
 */
@Service
public class WeixinMini extends WeixinBase implements Platform{

    @Override
    public String requestMessagePackage(HttpServletRequest request,
                                        HttpServletResponse response,  OrderPaymentLog orderPaymentLog) {
        String message = "";
        try {
            /*******************************************************获取支付参数*********************************************/
            WeixinPay pay = getParas(request, orderPaymentLog, "JSAPI");
            //rade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。
            // openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换


            /*******************************************************创建微信统一下单接口的订单*********************************************/
            Map<String,String> map =createWeiXinOrder(orderPaymentLog, pay.getMapPara(), pay.getAppKey());
            if (map == null || map.size() == 0) {
                return "";
            }

            //生成时间戳
            String timeStamp = TenpayUtil.getTimeStamp();
            //返回给app端
            JSONObject jb = new JSONObject();
            JSONObject data = new JSONObject();

            //将url传到jsp中，异步生产二维码
            if ("SUCCESS".equals(map.get("return_code"))) {
                jb.element("status", 0);
                jb.element("msg", "OK");
                data.element("appId", pay.getAppId());
                data.element("timeStamp", timeStamp);
                data.element("nonceStr", pay.getNoncestr());
                data.element("package", "prepay_id="+map.get("prepay_id"));
                data.element("signType", "MD5");
                //增加签名
                Map<String,String> reMap = new HashMap<String,String>();
                reMap.put("appId", pay.getAppId());
                reMap.put("timeStamp", timeStamp);
                reMap.put("nonceStr", pay.getNoncestr());
                reMap.put("package", "prepay_id="+map.get("prepay_id"));
                reMap.put("signType", "MD5");
                String paySign = WXUtil.createSign(reMap, pay.getAppKey());
                data.element("paySign", paySign);
                jb.element("data", data);
            }else {
                return "";
            }
            StringBuilder str = new StringBuilder();
            /*String callback = request.getParameter("callback");
            str.append(callback);*/
            str.append(jb.toString());

            message = jb.toString();
            response.resetBuffer();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(str);
            response.getWriter().flush();
            response.getWriter().close();
            response.flushBuffer();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return message;
    }



    /**
     *  同步通知
     */
    @Override
    public String returnMessageHandle(HttpServletRequest request,
                                      HttpServletResponse response, String paymentTypeNo){
        return null;
    }

    /**
     * 异步通知
     */
    @Override
    public String notifyMessageHandle(HttpServletRequest request,
                                      HttpServletResponse response, String paymentTypeNo)
            throws Exception {
        baseNotifyMessage(request, response, paymentTypeNo);
        return null;
    }


}
