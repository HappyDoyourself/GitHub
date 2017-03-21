package cn.com.dubbo.task;

import cn.com.dubbo.redis.CacheUtil;
import cn.com.dubbo.service.MyPayService;
import cn.com.jiuyao.pay.common.util.HttpClientTools;
import cn.com.jiuyao.pay.common.util.StringUtil;
import cn.com.jiuyao.util.payments.weixin.WXJSSDKConstant;
import cn.com.jiuyao.util.payments.weixin.WXURLConstant;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FHT
 * Date 2017-03-16 09:34
 */
@Component
public class AccessTokenTask {

    private final Logger logger = LoggerFactory.getLogger(AccessTokenTask.class);
    @Autowired
    public CacheUtil cacheUtil;

    private static final Integer PAYMENT_TYPE_ID = 7;
    @Autowired
    public MyPayService myPayService;

    @Scheduled(cron = "0 0 */1 * * ?")
    public final void getAccessTokenTask(){
        logger.info("=================微信获取token任务 每一个小时执行一次");
        try {
            Map<String,String> mapPaymentType = myPayService.findPaymentTypeListInfo(PAYMENT_TYPE_ID);
            Map<String,String> getTokenMap = new HashMap<String, String>();
            getTokenMap.put("grant_type","client_credential"); //获取access_token填写client_credential
            getTokenMap.put("appid", mapPaymentType.get("appId")); //第三方用户唯一凭证
            getTokenMap.put("secret", mapPaymentType.get("appKey")); //第三方用户唯一凭证密钥，
            String resTokenData = HttpClientTools.doGet(WXURLConstant.ACCESSTOKENURL, getTokenMap);
            if (StringUtil.isEmpty(resTokenData)){
                logger.error("WXJs Token is null");
                throw new RuntimeException();
            }
            JSONObject jsonObject = JSONObject.parseObject(resTokenData);
            logger.info("WeixinJs resData" + jsonObject);
            if (jsonObject.get("access_token") !=null){
                cacheUtil.setCache("access_token",jsonObject.get("access_token").toString(),Integer.valueOf(jsonObject.get("expires_in").toString()));
            }else {
                logger.error("get WXJs token error,errorCode: " + jsonObject.get("errcode"));
            }

            //缓存jsapiTicket
            Map<String, String> map = new HashMap<String, String>(); //请求获取JS-SDK使用权限签名算法
            map.put("access_token", jsonObject.get("access_token").toString()); //请求参数access_token
            map.put("type", "jsapi"); //请求参数type 固定
            String ticketResData = HttpClientTools.doGet(WXURLConstant.JSAPITICKET, map);
            if (StringUtil.isNotEmpty(ticketResData)) {
                JSONObject jsonTicket = JSONObject.parseObject(ticketResData);
                if ("0".equals(jsonTicket.get("errcode").toString())) {
                    cacheUtil.setCache("jsapiTicket", jsonTicket.get("ticket").toString(), WXJSSDKConstant.EXPIRETIME);
                } else {
                    logger.error("WX get jsapiTicket error" + jsonTicket.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
