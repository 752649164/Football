package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    List<Order> findByCondition(Order order);

    void add(Order order);

    Map findById4Detail(Integer id);

    int findCountIndate(String today);

    int findCountAfterDate(String monday);

    int findOrderStatusCountIndate(String today);

    int findOrderStatusCountAfterDate(String monday);

    List<Map<String, Object>> findHotSetmeal();
}
