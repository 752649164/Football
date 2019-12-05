package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.pojo.Menu;
import itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Override
    public List<Map> findByUsername(String username) {
        List<Map> mapList=new ArrayList<>();
        List<Menu> list=  menuDao.findByUsername(username);
        for (Menu menu : list) {
            if(menu.getParentMenuId()==null){
                Map<String,Object> map=new HashMap<>();
                map.put("path",menu.getPath());
                map.put("title",menu.getName());
                map.put("linkUrl",menu.getLinkUrl());
                map.put("children",getChildMenu(list,menu));
                mapList.add(map);
            }
        }
        return mapList;
    }
    public List<Map> getChildMenu(List<Menu> list,Menu menu){
        List<Map> mapList=new ArrayList<>();
        for (Menu m : list) {
            if(m.getParentMenuId()!=null&&m.getParentMenuId()==menu.getId()){
                Map<String,Object> map=new HashMap<>();
                map.put("path",m.getPath());
                map.put("title",m.getName());
                map.put("linkUrl",m.getLinkUrl());
                map.put("children",getChildMenu(list,m));
                mapList.add(map);
            }
        }
        return mapList;
    }
}
