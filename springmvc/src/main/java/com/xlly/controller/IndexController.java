package com.xlly.controller;

import com.xlly.model.UserEntity;
import com.xlly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by fanhongtao
 * Date 2017-03-22 09:50
 */
@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }


    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String getUsers(ModelMap modelMap){
        List<UserEntity> userEntityList = userRepository.findAll();
        modelMap.addAttribute("userList",userEntityList);
        return "admin/users";
    }


    @RequestMapping(value = "/admin/addUser",method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") UserEntity userEntity){
        // 注意此处，post请求传递过来的是一个UserEntity对象，里面包含了该用户的信息
        // 通过@ModelAttribute()注解可以获取传递过来的'user'，并创建这个对象

        // 数据库中添加一个用户，该步暂时不会刷新缓存
        //userRepository.save(userEntity);

        // 数据库中添加一个用户，并立即刷新缓存
        userRepository.saveAndFlush(userEntity);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/admin/updateUser",method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user")UserEntity userEntity){
        userRepository.updateUser(userEntity.getNickname(),userEntity.getFirstName(),userEntity.getLastName(),userEntity.getPassword(),userEntity.getId());
        userRepository.flush();
        return "redirect:/admin/users";
    }
}
