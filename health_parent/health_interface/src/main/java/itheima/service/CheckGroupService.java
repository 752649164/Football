package itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(CheckGroup checkGroup , Integer[] checkitemIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    List<Integer> findCheckItemIdsById(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    CheckGroup findById(Integer id);

    List<CheckGroup> findAll();

    void del(Integer id);
}
