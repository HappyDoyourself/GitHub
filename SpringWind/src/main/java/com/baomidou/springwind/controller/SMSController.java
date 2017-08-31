package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.framework.controller.SuperController;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.springwind.common.Constant;
import com.baomidou.springwind.entity.Activity;
import com.baomidou.springwind.entity.Order;
import com.baomidou.springwind.entity.Sms;
import com.baomidou.springwind.service.IActivityService;
import com.baomidou.springwind.service.IOrderService;
import com.baomidou.springwind.service.ISmsService;
import com.baomidou.springwind.service.impl.SmsServiceImpl;
import com.baomidou.springwind.util.DateUtils;
import com.baomidou.springwind.util.yuntongxin.CCPRestSDK;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fht on 2017-05-23 20:10.
 */
@Controller
@RequestMapping("/sms/")
public class SMSController extends SuperController{

    @Autowired
    private ISmsService smsService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IActivityService activityService;

    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/sendSms")
    @ResponseBody
    public String sendSms(Model model, String phone, String userName){
        //Map<String,String> map = new HashMap<>();
      JSONArray jsonArray = new JSONArray();
        String vcode = "";
        if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(userName)){
            HashMap<String, Object> result = null;
            CCPRestSDK restAPI = new CCPRestSDK();
            restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
            restAPI.setAccount("aaf98f894526e89a01452d4806bb058f", "5c323c9cc05c4915af9a1ae75ad5aa09");// 初始化主帐号和主帐号TOKEN
            restAPI.setAppId("8a48b5514694514d014699031d720231");// 初始化应用ID
            for (int i = 0; i < 6; i++) {
                vcode = vcode + (int)(Math.random() * 9);
            }
            result = restAPI.sendTemplateSMS(phone,"11115" ,new String[]{vcode});
            System.out.println("SDKTestSendTemplateSMS result=" + result);
            Sms sms = new Sms();
            sms.setVerifyCode(vcode);
            sms.setPhone(phone);
            sms.setSendName(userName);
            if("000000".equals(result.get("statusCode"))){
                //正常返回输出data包体信息（map）
                HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
                Set<String> keySet = data.keySet();
                sms.setSmsState(Constant.SMS_SUCCESS);
                for(String key:keySet){
                    Object object = data.get(key);
                    System.out.println(key +" = "+object);
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code","SUCCESS");
                jsonObject.put("vcode",vcode);
                jsonArray.add(jsonObject.toJSONString());
            }else{
                //异常返回输出错误码和错误信息
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code","FAIL");
                jsonObject.put("errorMessage",result.get("statusMsg"));
                jsonArray.add(jsonObject.toJSONString());
                sms.setSmsState(Constant.SMS_FAILED);;
                System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            }
            smsService.insert(sms);
        }
        return jsonArray.toJSONString();
    }

    /**
     * 跳转到输入手机号码页面
     * @return
     */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/liucheng")
    public String liucheng(@RequestParam(value = "activityId") Long activityId, @RequestParam(value = "amount")String amount,
                         @RequestParam(value = "tag")String tag, Model model){
        model.addAttribute("amount",amount);
        model.addAttribute("activityId",activityId);
        model.addAttribute("tag",tag);
        if (tag == "1"){
            //招募提示页面
            return "/activity/zhaomu_liucheng";
        }else{
            //预定流程页面
            return "/activity/yuding_liucheng";
        }
    }


    /**
     * 跳转到输入手机号码页面
     * @return
     */
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/signUp")
    public String signUp(@RequestParam(value = "activityId") Long activityId, @RequestParam(value = "amount")String amount,
                         @RequestParam(value = "tag")String tag,@RequestParam(value = "openId")String openId, Model model){
        model.addAttribute("amount",amount);
        model.addAttribute("activityId",activityId);
        model.addAttribute("tag",tag);
        Map<String,Object> map = new HashedMap();
        map.put("open_id",openId);
        map.put("order_state",Constant.PAY_WAITING);
        map.put("activity_id",activityId);
        List<Order> orderList = orderService.selectByMap(map);
        if (CollectionUtils.isNotEmpty(orderList)){ //如果已经下过订单直接显示订单
            Order order = orderList.get(0);
            Activity activity = activityService.selectById(activityId);
            model.addAttribute("endTime", DateUtils.getAfterByMinuties(order.getAddTime(),20).getTime()); //获取20分钟后的时间
            order.setOrderAmount(new BigDecimal(amount)); //展示时候展示的仍然是元为单位
            model.addAttribute("order",order);
            model.addAttribute("userName",order.getUserName());
            model.addAttribute("pic",activity.getPic());
            model.addAttribute("activityTitle",activity.getTitle());
            model.addAttribute("activityId",activity.getId());
            model.addAttribute("tag",tag);
            if (Constant.YUDING == Integer.valueOf(tag)){
                return "/order/order_details";
            }else {
                return "/order/zhaomu_success";
            }
        }
        return "/sms/baoming";
    }

    public String signUp(HttpServletRequest request,Model model){
        String phone = request.getParameter("phone");
        String userName = request.getParameter("userName");
        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.setAccount("aaf98f894526e89a01452d4806bb058f", "5c323c9cc05c4915af9a1ae75ad5aa09");// 初始化主帐号和主帐号TOKEN
        restAPI.setAppId("8a48b5514694514d014699031d720231");// 初始化应用ID
        result = restAPI.sendTemplateSMS(phone,"11115" ,new String[]{phone,userName});
        System.out.println("SDKTestSendTemplateSMS result=" + result);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }
        return "/sms/signup_success";
    }

}
