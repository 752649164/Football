package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;

import itheima.service.CheckGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/checkgroup")
@CrossOrigin(origins = {"http://localhost:80"})//设置信任源，解决跨域问题
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    public Result add(@RequestBody CheckGroup checkGroup ,Integer[] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult result=  checkGroupService.pageQuery(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return result;
    }
    @RequestMapping("/findCheckItemIdsById")
    public Result findCheckItemIdsById(Integer id){
        List<Integer> list=null;
        try {
             list= checkGroupService.findCheckItemIdsById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }
    @RequestMapping("/edit")
    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    public Result edit(@RequestBody CheckGroup checkGroup ,Integer[] checkitemIds){
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){

        try {
            CheckGroup checkGroup=  checkGroupService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
    @RequestMapping("/findAll")
    public Result findAll(){

        try {
           List<CheckGroup> list=  checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
    @RequestMapping("/del")
    @PreAuthorize("hasAuthority('CHECKGROUP_DELETE')")
    public Result del(Integer id){

        try {
              checkGroupService.del(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }

    }

}
