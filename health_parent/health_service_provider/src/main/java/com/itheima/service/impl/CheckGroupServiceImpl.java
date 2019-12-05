package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class,retries = -1)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    public void add(@RequestBody  CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        System.out.println(checkGroupId);
        this.setGroupAndItem(checkGroupId,checkitemIds);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckGroup> rows = page.getResult();
        PageResult pageResult=new PageResult(total,rows);
        return pageResult;
    }

    @Override
    public List<Integer> findCheckItemIdsById(Integer id) {
        return  checkGroupDao.findCheckItemIdsById(id);

    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.updateCheckGroup(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        System.out.println(checkGroupId);
        checkGroupDao.deleteByCheckGroupId(checkGroupId);
        this.setGroupAndItem(checkGroupId,checkitemIds);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    @Override
    public void del(Integer id) {
        checkGroupDao.deleteByCheckGroupId(id);
        checkGroupDao.deleteMealBycheckGroupId(id);
        checkGroupDao.del(id);
    }

    public void setGroupAndItem(Integer checkGroupId,Integer[] checkitemIds){
        if(checkitemIds!=null&&checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map=new HashMap<String,Integer>();
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.setGroupAndItem(map);
            }
        }
    }
}
