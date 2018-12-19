package com.simple.manage.system.service;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.entity.Tree;

import java.util.List;
import java.util.Map;

/**
 * Description 部门数据服务接口
 * Author chen
 * CreateTime 2018-12-18 11:07
 **/

public interface DeptService {
    /**
     * 添加或更新部门
     *
     * @param dept
     * @return
     */
    int addOrUpdDept(Map<String, Object> dept);

    /**
     * 查询部门信息
     *
     * @param deptId
     * @return
     */
    Map<String, Object> queryDept(int deptId);

    /**
     * 查询部门列表
     *
     * @param param
     * @param page
     * @param size
     * @return
     */
    PageInfo queryDeptList(Map<String, Object> param, int page, int size);

    /**
     * 查询链接
     *
     * @param deptId
     * @return
     */
    List<Tree> queryDeptRecursion(int deptId);
}
