package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
     CheckGroup findById(Integer id);

    void add(CheckGroup checkGroup);

    void setGroupAndItem(Map map);

    Page<CheckGroup> selectByCondition(String queryString);

    List<Integer> findCheckItemIdsById(Integer id);

    void updateCheckGroup(CheckGroup checkGroup);

    void deleteByCheckGroupId(Integer checkGroupId);

    List<CheckGroup> findAll();

    void del(Integer id);

    void deleteMealBycheckGroupId(Integer id);

    List<CheckGroup> findCheckGroupById(int id);
}
