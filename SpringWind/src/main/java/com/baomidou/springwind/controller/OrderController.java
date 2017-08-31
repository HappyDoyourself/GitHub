package com.baomidou.springwind.controller;

import com.baomidou.framework.annotations.Log;
import com.baomidou.framework.controller.SuperController;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.common.Constant;
import com.baomidou.springwind.entity.Activity;
import com.baomidou.springwind.entity.Order;
import com.baomidou.springwind.entity.Role;
import com.baomidou.springwind.service.IActivityService;
import com.baomidou.springwind.service.IOrderService;
import com.baomidou.springwind.util.ChsetTransUtils;
import com.baomidou.springwind.util.DateUtils;
import com.baomidou.springwind.util.yuntongxin.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.baomidou.kisso.annotation.Action.Skip;

/**
 * Created by fht on 2017-05-22 23:55.
 */
@Controller
@RequestMapping(value = "/order/")
public class OrderController extends SuperController{
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IActivityService activityService;

    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping(value = "/create")
    public String createOrder(HttpServletRequest request, Model model){
        String openId = request.getParameter("openId");
        String tag = request.getParameter("tag");
        String activityId = request.getParameter("activityId");

        String phone = request.getParameter("phone");
        String userName = ChsetTransUtils.strIsoToUtf(request.getParameter("userName"));
        String amount =request.getParameter("amount");
        String orderType = request.getParameter("orderType");
        Activity activity = activityService.selectById(activityId);
        BigDecimal amountFen = new BigDecimal(amount).multiply(new BigDecimal(100));

        Map<String,Object> map = new HashMap();
        map.put("open_id",openId);
        map.put("order_state",Constant.PAY_WAITING);
        map.put("activity_id",activityId);
        List<Order> orderList = orderService.selectByMap(map);
        if(CollectionUtils.isNotEmpty(orderList)){ //如果已有则直接返回
            Order order = orderList.get(0);
            this.getModel(model,order,activity,userName,tag);
        }else if (CollectionUtils.isEmpty(orderList) && activity != null  && activity.getPlaces() > 0){
             synchronized (this){
                Order order = new Order();
                order.setAddTime(DateUtils.getCurrentDate());
                order.setUpdateTime(DateUtils.getCurrentDate());
                order.setActivityId(Long.valueOf(activityId));
                order.setOrderAmount(amountFen);
                if(Constant.YUDING == Integer.valueOf(tag)){
                    order.setOrderState(Constant.PAY_WAITING);
                }else {
                    //如果是招募 默认支付成功 方便job来调用
                    activity.setPlaces(activity.getPlaces()-1);
                    order.setOrderState(Constant.PAY_SUCCESS);
                }
                //注意活动的金额为元
                order.setOrderType(new BigDecimal(amount).subtract(activity.getFullPrice()).compareTo(BigDecimal.ZERO) == 0?Constant.DEPOSITPAY:Constant.FULLINPAY  );
                order.setPhone(phone);
                order.setId(System.currentTimeMillis());
                order.setOpenId(openId);
                order.setTag(Integer.valueOf(tag));
                orderService.insert(order);
                //修改活动人数减一
                 if (activity.getPlaces() <= 0)
                     activity.setState(Constant.ACTIVITY_OFF);
                 activityService.updateById(activity);

                this.getModel(model,order,activity,userName,tag);
            }
        }
        if (Constant.YUDING == Integer.valueOf(tag)){
                return "/order/order_details";
        }else {
               return "/order/zhaomu_success";
        }
    }

    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping(value = "/orderDetailById")
    private String orderDetailById(Model model,String id){
        Order order = orderService.selectById(id);
        if(order!=null){
            model.addAttribute("order",order);
           Activity activity = activityService.selectById(order.getActivityId());
           model.addAttribute("title",activity.getTitle());
        }

        return "/order/order_details_zhaomu";
    }


    /**
     * 页面需要显示
     * @param model
     * @param order
     * @param activity
     * @param userName
     * @param tag
     */
    private void getModel(Model model,Order order,Activity activity,String userName,String tag){
        model.addAttribute("endTime",DateUtils.getAfterByMinuties(order.getAddTime(),20).getTime()); //获取20分钟后的时间
        order.setOrderAmount(order.getOrderAmount().divide(new BigDecimal(100))); //展示时候展示的仍然是元为单位
        model.addAttribute("order",order);
        model.addAttribute("userName",userName);
        model.addAttribute("pic",activity.getPic());
        model.addAttribute("activityTitle",activity.getTitle());
        model.addAttribute("activityId",activity.getId());
        model.addAttribute("tag",tag);
    }

}
