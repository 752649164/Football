package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    void add(CheckItem checkItem);
    Page<CheckItem> selectByCondition(String queryString);
    int findCountByCheckItemId(Integer id);
    void delById(int id);
    void eidt(CheckItem checkItem);
    CheckItem findById(Integer id);
    List<CheckItem> findCheckItemsByCheckGroupId(int id);
    List<CheckItem> findAll();
}
