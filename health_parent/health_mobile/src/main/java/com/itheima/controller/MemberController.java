package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.utils.SMSUtils;
import com.qiniu.util.Json;
import itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/login")
    public Result login(HttpServletResponse response,@RequestBody Map map) {
        String telephone=(String)map.get("telephone");
        String validateCode=(String)map.get("validateCode");
        String loginCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (validateCode == null || !validateCode.equals(loginCode) || loginCode == null) {
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        } else {
            Member member = memberService.findMemberByTelephone(telephone);
            if (member == null) {
                member=new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setMaxAge(60 * 60 * 24 * 30);
            cookie.setPath("/");
            response.addCookie(cookie);
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone,30*60,json);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }
    }

}
