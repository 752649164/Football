package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    void add(OrderSetting orderSetting);
    void edit(OrderSetting orderSetting);
    int findCountByOrderDate(Date orderDate);
    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);
    OrderSetting findOrderSettingByDate(Date date);
}
