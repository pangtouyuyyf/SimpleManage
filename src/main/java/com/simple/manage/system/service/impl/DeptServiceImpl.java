package com.simple.manage.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.dao.DeptDao;
import com.simple.manage.system.entity.Tree;
import com.simple.manage.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description 部门数据服务接口实现
 * Author chen
 * CreateTime 2018-12-18 11:10
 **/
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    /**
     * 添加或更新部门
     *
     * @param dept
     * @return
     */
    public int addOrUpdDept(Map<String, Object> dept) {
        return this.deptDao.addOrUpdDept(dept);
    }

    /**
     * 查询部门列表
     *
     * @param param
     * @param page
     * @param size
     * @return
     */
    public PageInfo queryDepts(Map<String, Object> param, int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> deptDao.queryDepts(param));
    }

    /**
     * 查询链接
     *
     * @param deptId
     * @return
     */
    public List<Tree> queryDeptRecursion(int deptId) {
        return this.deptDao.queryDeptRecursion(deptId);
    }
}
