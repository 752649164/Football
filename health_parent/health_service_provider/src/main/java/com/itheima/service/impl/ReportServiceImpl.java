package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderDao;
import com.itheima.dao.ReportDao;
import com.itheima.utils.DateUtils;
import itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private OrderDao orderDao;

    public List<Integer> getMemberReport(List<String> list) {
        List<Integer> list2 = new ArrayList<>();
        for (String s : list) {
            int count = reportDao.findCountByDate(s);
            list2.add(count);
        }
        return list2;
    }

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        Map<String, Object> map = new HashMap<>();
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        String monday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String firstDayInMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        map.put("reportDate", today);
        int totalMember = reportDao.findCountMembers();
        map.put("totalMember", totalMember);
        int todayNewMember = reportDao.findCountInDate(today);
        map.put("todayNewMember", todayNewMember);
        int thisWeekNewMember = reportDao.findCountAfterDate(monday);
        map.put("thisWeekNewMember", thisWeekNewMember);
        int thisMonthNewMember = reportDao.findCountAfterDate(firstDayInMonth);
        map.put("thisMonthNewMember", thisMonthNewMember);

        int todayOrderNumber = orderDao.findCountIndate(today);
        map.put("todayOrderNumber", todayOrderNumber);
        int thisWeekOrderNumber = orderDao.findCountAfterDate(monday);
        map.put("thisWeekOrderNumber",thisWeekOrderNumber);
        int thisMonthOrderNumber = orderDao.findCountAfterDate(firstDayInMonth);
        map.put("thisMonthOrderNumber",thisMonthOrderNumber);

        int todayVisitsNumber = orderDao.findOrderStatusCountIndate(today);
        map.put("todayVisitsNumber", todayVisitsNumber);
        int thisWeekVisitsNumber = orderDao.findOrderStatusCountAfterDate(monday);
        map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        int thisMonthVisitsNumber = orderDao.findOrderStatusCountAfterDate(firstDayInMonth);
        map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        List<Map<String,Object>> hotSetmeal=orderDao.findHotSetmeal();
        map.put("hotSetmeal",hotSetmeal);
        return map;
    }
}
