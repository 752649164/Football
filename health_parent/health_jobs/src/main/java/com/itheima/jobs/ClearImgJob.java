package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import redis.clients.jedis.JedisPool;


import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    @Scheduled(cron = "0/10 * * * * ?")
    public void clearImg(){
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(set!=null&&set.size()>0){
            for (String s : set) {
                QiniuUtils.deleteFileFromQiniu(s);
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
            }
        }
    }
}
