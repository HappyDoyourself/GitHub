package com.baomidou.springwind.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.springwind.common.Constant;
import com.baomidou.springwind.entity.Activity;
import com.baomidou.springwind.entity.Order;
import com.baomidou.springwind.entity.Sms;
import com.baomidou.springwind.service.IActivityService;
import com.baomidou.springwind.service.IOrderService;
import com.baomidou.springwind.service.ISmsService;
import com.baomidou.springwind.util.yuntongxin.CCPRestSDK;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fht on 2017-07-17 22:24.
 * 报名成功向管理员发送短信提醒
 */
@Component
public class SendSmsTask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISmsService smsService;
    @Autowired
    private IActivityService activityService;

    public void sendSmsTask(){
        logger.info("定时查询招募报名成功或者预定支付成功发送给管理员");
        Map<String,Object> map = new HashedMap();
        map.put("order_state", Constant.PAY_SUCCESS);
        map.put("sms_state",Constant.SMS_UNSEND);
        List<Order> orderList = orderService.selectByMap(map);
        for(Order order : orderList){
            Activity activity = activityService.selectById(order.getActivityId());
           this.sendSms(order,"13552448009",activity.getTitle());
        }
    }


    public void sendSms(Order order,String superPhone,String title){
        String vcode = "";
        if (StringUtils.isNotBlank(order.getPhone()) && StringUtils.isNotBlank(order.getUserName())){
            HashMap<String, Object> result = null;
            CCPRestSDK restAPI = new CCPRestSDK();
            restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
            restAPI.setAccount("aaf98f894526e89a01452d4806bb058f", "5c323c9cc05c4915af9a1ae75ad5aa09");// 初始化主帐号和主帐号TOKEN
            restAPI.setAppId("8a48b5514694514d014699031d720231");// 初始化应用ID
            result = restAPI.sendTemplateSMS(superPhone,"191302" ,new String[]{order.getUserName(),title,order.getPhone()});
            System.out.println("SDKTestSendTemplateSMS result=" + result);
            Sms sms = new Sms();
            sms.setPhone(superPhone);
            //sms.setSendName(order.getUserName());
           // sms.setMessage(result);
            if("000000".equals(result.get("statusCode"))){
                //正常返回输出data包体信息（map）
                HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
                Set<String> keySet = data.keySet();
                sms.setSmsState(Constant.SMS_SUCCESS);
                order.setSmsState(Constant.SMS_SUCCESS);
                /*for(String key:keySet){
                    Object object = data.get(key);
                    System.out.println(key +" = "+object);
                }*/
            }else{
                //异常返回输出错误码和错误信息
                sms.setSmsState(Constant.SMS_FAILED);;
                order.setSmsState(Constant.SMS_UNSEND); //此处应该改为未发送 因sql我无法查询or暂时这样处理
                System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            }
            smsService.insert(sms);
            orderService.updateById(order);
        }
    }
}
