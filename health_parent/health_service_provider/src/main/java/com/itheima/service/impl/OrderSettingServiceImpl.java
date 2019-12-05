package com.itheima.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class,retries = -1 )
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Override
    public void add(List<OrderSetting> orderSettingList) {
        for (OrderSetting orderSetting : orderSettingList) {
            int number = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            if(number>0){
               orderSettingDao.edit(orderSetting);
            }else {
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String begin=date+"-1";
        String end=date+"-31";
        Map<String ,String> map=new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        List<OrderSetting> list= orderSettingDao.getOrderSettingByMonth(map);
        List<Map> mapList=new ArrayList<>();
        if(list!=null){
            for (OrderSetting orderSetting : list) {
                Map<String ,Object> map1=new HashMap<>();
                int date1 = orderSetting.getOrderDate().getDate();
                map1.put("date",date1);
                map1.put("number",orderSetting.getNumber());
                map1.put("reservations",orderSetting.getReservations());
                mapList.add(map1);
            }
        }
        return mapList;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        int number = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(number>0){
            orderSettingDao.edit(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }

    }
}
