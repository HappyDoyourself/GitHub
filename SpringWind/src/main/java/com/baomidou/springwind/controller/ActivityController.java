package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.framework.controller.SuperController;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.common.Constant;
import com.baomidou.springwind.entity.Activity;
import com.baomidou.springwind.entity.User;
import com.baomidou.springwind.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fht on 2017-05-03 19:29.
 */
@Controller
@RequestMapping("activity/")
public class ActivityController extends SuperController{

    @Autowired
    private IActivityService activityService;

   /* @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/list")
      public String activityList(){
        System.out.println("successful");
        return "/activity/list";
      }*/


    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/list")
    public String activityList(Model model){
        System.out.println("successful");
        Page<Activity> page = getPage();
        Page pageActivity = activityService.selectPage(page, null);
        model.addAttribute("pageActivity",pageActivity);
        model.addAttribute("jsonPage",jsonPage(pageActivity));
        return "/activity/index_list1";
    }

    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/activityDetail")
    public String activityDetail(Model model,Long id,int tag){
        Activity activity = new Activity();
        if ( id != null ) {
            activity = activityService.selectById(id);
            model.addAttribute("activity",activity );
            model.addAttribute("endTime",activity.getEndTime().getTime());
        }
        if(tag == Constant.ZHAOMU){
            return "/activity/active_zhaomu_details";
        }
        return "/activity/active_baoming_details";
    }

   /* @Permission("5001")
    @RequestMapping("/list")
    public String list(Model model) {
        return "/activity/list";
    }*/


    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/detail")
    public String detail(){
        System.out.println("请求成功");
        return "/activity/detail";
    }

    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/yuding")
    public String yuding(){
        System.out.println("请求成功");
        return "/activity/yuding";
    }

   /* @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/signup")
    public String signup(){
        System.out.println("请求成功");
        return "/activity/signup";
    }*/

   // @ResponseBody
    @Login(action = Action.Skip)
    @Permission(action = Action.Skip)
    @RequestMapping("/activityList")
    public String getActivityList(Model model) {
        Page<Activity> page = getPage();
        Page pageActivity = activityService.selectPage(page, null);
        model.addAttribute("pageActivity",pageActivity);
        return jsonPage(pageActivity);
    }

    /**
     * <p>
     * 转换为 bootstrap-table 需要的分页格式 JSON
     * </p>
     *
     * @param page
     *            分页对象
     * @return
     */
    private String jsonPage(Page<?> page) {
        JSONObject jo = new JSONObject();
        jo.put("total", page.getTotal());
        jo.put("rows", page.getRecords());
        return toJson(jo);
    }
}
