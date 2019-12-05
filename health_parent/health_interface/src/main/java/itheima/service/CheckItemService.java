package itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    void add(CheckItem checkItem);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void del(Integer id);

    CheckItem findById(Integer id);

    void eidt(CheckItem checkItem);

    List<CheckItem> findAll();
}
