package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.Order;
import com.baomidou.springwind.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fht on 2017-06-27 21:42.
 * 后台用的订单列表
 */
@Controller
@RequestMapping("/orderlist/")
public class OrdersController extends BaseController{
    @Autowired
    private IOrderService orderService;

    @Permission("6001")
    @RequestMapping("/list")
    public String list( Model model ) {
        return "/orders/list";
    }


    @ResponseBody
    @Permission("6001")
    @RequestMapping("/getOrderList")
    public String getOrderList() {
        Page<Order> page = getPage();
        return jsonPage(orderService.selectPage(page, null));
    }

}
