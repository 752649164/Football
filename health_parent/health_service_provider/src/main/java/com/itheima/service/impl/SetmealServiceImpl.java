package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class,retries = -1)
@Transactional
public class SetmealServiceImpl implements SetmealService{
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckgroup(checkgroupIds,setmealId);
    }

    @Override
    public Page<Setmeal> findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> list= setmealDao.selectByCondition(queryString);
        return list;
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        List<Map<String, Object>> list=  setmealDao.findSetmealCount();

        return list;
    }


    public void setSetmealAndCheckgroup(Integer[] checkgroupIds,Integer setmealId){
            if(checkgroupIds!=null&&checkgroupIds.length>0){
                for (Integer checkgroupId : checkgroupIds) {
                    Map<String,Integer> map=new HashMap<>();
                    map.put("setmealId",setmealId);
                    map.put("checkgroupId",checkgroupId);
                    setmealDao.setSetmealAndCheckgroup(map);
                }
            }
    }
}
