package cn.com.dubbo.service.payment.platform;

import cn.com.jiuyao.pay.common.util.HttpClientTools;
import cn.com.jiuyao.pay.common.util.StringUtil;
import cn.com.jiuyao.util.payments.weixin.TenpayUtil;
import cn.com.jiuyao.util.payments.weixin.WXURLConstant;
import cn.com.jiuyao.util.payments.weixin.WXUtil;
import com.alibaba.fastjson.JSONObject;
import org.jdom.JDOMException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FHT
 * Date 2017-03-16 11:13
 */
@Service
public class WXSharingService extends PlatformPayment implements Sharing{

    private static final Integer PAYMENT_TYPE_ID = 7; //微信公众号

    @Override
    public final String sharing(String url, HttpServletResponse response) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String,String> resMap = new HashMap<String, String>();
        //返回给app端
        net.sf.json.JSONObject jb = new net.sf.json.JSONObject();
        net.sf.json.JSONObject data = new net.sf.json.JSONObject();
        try {
            if (url.contains("#")){
                url = url.substring(0,url.indexOf("#")-1); //不需要获取后面的#的内容
            }
            Map<String,String> ecPaymentMap = myPayService.findPaymentTypeListInfo(PAYMENT_TYPE_ID);
            String accessToken = cacheUtil.readCache("access_token"); //获取token
            if (StringUtil.isEmpty(accessToken)){
                Map<String,String> getTokenMap = new HashMap<String, String>();
                getTokenMap.put("grant_type","client_credential"); //获取access_token填写client_credential
                getTokenMap.put("appid",ecPaymentMap.get("appId")); //第三方用户唯一凭证
                getTokenMap.put("secret",ecPaymentMap.get("appKey")); //第三方用户唯一凭证密钥，
                String resTokenData = HttpClientTools.doGet(WXURLConstant.ACCESSTOKENURL, getTokenMap);
                if (StringUtil.isEmpty(resTokenData)){
                    logger.error("WXJs Token is null");
                    throw new RuntimeException();
                }
                JSONObject jsonObject = JSONObject.parseObject(resTokenData);
                logger.info("WXJs resData" + jsonObject);

                if (jsonObject.get("access_token") !=null){
                    cacheUtil.setCache("access_token",jsonObject.get("access_token").toString(),Integer.valueOf(jsonObject.get("expires_in").toString()));
                }else {
                    logger.error("get WXJs token error,errorCode: " + jsonObject.get("errcode"));
                }
                accessToken=cacheUtil.readCache("access_token");
            }
            Map<String,String> map = new HashMap<String, String>(); //请求获取JS-SDK使用权限签名算法
            map.put("access_token",accessToken); //请求参数access_token
            map.put("type","jsapi"); //请求参数type 固定
            String jsapiTicket = HttpClientTools.doGet(WXURLConstant.JSAPITICKET, map);
            if (StringUtil.isEmpty(jsapiTicket)){
                logger.error("requestData is null");
                return null;
            }
            JSONObject jsonObject = JSONObject.parseObject(jsapiTicket);

            if (!"0".equals(jsonObject.get("errcode").toString())){
                logger.error("WX get jsapiTicket error" + jsonObject.toString());
                jb.element("status", 002);
                jb.element("msg", "jsapi error");
                data.element("data",jsonObject);
                jb.put("data",data);
                stringBuilder.append(jb);
                response.resetBuffer();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print(stringBuilder);
                response.getWriter().flush();
                response.getWriter().close();
                response.flushBuffer();
                return stringBuilder.toString();
            }
            jsapiTicket =jsonObject.get("ticket").toString();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String nonceStr = TenpayUtil.getNonceStr();
            Map<String,String> requestSignMap = new HashMap<String, String>(); //后去signature
            requestSignMap.put("noncestr", nonceStr);
            requestSignMap.put("jsapi_ticket",jsapiTicket);
            requestSignMap.put("timestamp",timestamp);
            requestSignMap.put("url",url);
            String signature = "";
            try {
                signature = WXUtil.createSign(requestSignMap, null);
            } catch (JDOMException e) {
                e.printStackTrace();
            }

            //增加签名
            jb.element("status", 0);
            jb.element("msg", "OK");
            resMap.put("appId", ecPaymentMap.get("appId"));
            resMap.put("timeStamp", timestamp);
            resMap.put("nonceStr", nonceStr);
            resMap.put("signature", signature);
            data.element("data",resMap);
            jb.element("data", data);
            stringBuilder.append(jb.toString());
            response.resetBuffer();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(stringBuilder.toString());
            response.getWriter().flush();
            response.getWriter().close();
            response.flushBuffer();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
