package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import itheima.service.MenuService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private MenuService menuService;
    @RequestMapping("/getUsername")
    public Result getUsername(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user!=null){
            System.out.println(user.toString());
            String username = user.getUsername();
            List<Map> mapList=  menuService.findByUsername(username);
            Map map=new HashMap();
            map.put("username",username);
            map.put("menus",mapList);
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,map);
        }
        return new Result(false,MessageConstant.GET_USERNAME_FAIL);
    }
}
