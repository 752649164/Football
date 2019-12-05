package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;
import itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class,retries = -1)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        PageResult pageResult=new PageResult(total,rows);
        return pageResult;
    }

    @Override
    public void del(Integer id) {
        int count = checkItemDao.findCountByCheckItemId(id);
        if(count>0){
            new RuntimeException();
        }
        checkItemDao.delById(id);
    }

    @Override
    public CheckItem findById(Integer id) {
        CheckItem checkItem=checkItemDao.findById(id);
        return checkItem;
    }

    @Override
    public void eidt(CheckItem checkItem) {
        checkItemDao.eidt(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
