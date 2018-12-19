package com.simple.manage.system.dao;

import com.simple.manage.system.entity.Tree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 部门数据操作
 * Author chen
 * CreateTime 2018-12-18 11:03
 **/
@Mapper
public interface DeptDao {
    /**
     * 添加或更新部门
     *
     * @param dept
     * @return
     */
    int addOrUpdDept(Map<String, Object> dept);

    /**
     * 查询部门列表
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> queryDepts(Map<String, Object> param);

    /**
     * 查询链接
     *
     * @param deptId
     * @return
     */
    List<Tree> queryDeptRecursion(int deptId);
}
