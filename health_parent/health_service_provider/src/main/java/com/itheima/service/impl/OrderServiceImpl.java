package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.utils.DateUtils;
import com.itheima.utils.SMSUtils;
import itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class, retries = -1)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao  orderDao;
    @Override
    public Result order(Map map) throws Exception {
        String orderDate = (String) map.get("orderDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(orderDate);
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByDate(date);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if (reservations >= number) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        String telephone = (String) map.get("telephone");
        String idCard = (String) map.get("idCard");
        String setmealId = (String) map.get("setmealId");
        Member member= memberDao.findByTelephone(telephone);
        if(member!=null){
            Integer id = member.getId();
            Order order=new Order();
            order.setSetmealId(Integer.parseInt(setmealId));
            order.setMemberId(id);
            order.setOrderDate(date);
            List<Order> list = orderDao.findByCondition(order);
            if(list!=null&&list.size()>0){
               return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
        if(member==null){
            member=new Member();
            member.setIdCard(idCard);
            member.setPhoneNumber(telephone);
            member.setSex((String)map.get("sex"));
            member.setName((String)map.get("name"));
            member.setRegTime(date);
            try {
                memberDao.save(member);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Integer id = member.getId();
        Order order=new Order(id,date,Order.ORDERTYPE_WEIXIN,Order.ORDERSTATUS_NO,Integer.parseInt(setmealId));
        orderDao.add(order);
        SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,orderDate);
        orderSetting.setReservations(reservations+1);
        orderSettingDao.edit(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    @Override
    public Map findById(Integer id) throws Exception {
     Map map= orderDao.findById4Detail(id);
     if(map!=null){
         Date orderDate =(Date)map.get("orderDate");
         map.put("orderDate",DateUtils.parseDate2String(orderDate));
     }
        return map;
    }
}
