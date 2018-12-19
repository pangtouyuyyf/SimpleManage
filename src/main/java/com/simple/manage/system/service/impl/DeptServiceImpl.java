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
     * 查询部门信息
     *
     * @param deptId
     * @return
     */
    public Map<String, Object> queryDept(int deptId) {
        return this.deptDao.queryDept(deptId);
    }

    /**
     * 查询部门列表
     *
     * @param param
     * @param page
     * @param size
     * @return
     */
    public PageInfo queryDeptList(Map<String, Object> param, int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> deptDao.queryDeptList(param));
    }

    /**
     * 查询链接
     *
     * @param deptId
     * @return
     */
    public List<Tree> queryDeptRecursion(int deptId) {
        List<Tree> tree = this.deptDao.queryDeptRecursion(deptId);
        if (tree != null && !tree.isEmpty()) {
            for (Tree node : tree) {
                setLeaf(node);
            }
        }

        return tree;
    }

    /**
     * 递归设置是否叶子节点
     *
     * @param treeNode 树节点
     */
    private void setLeaf(Tree treeNode) {
        if (treeNode.getChildren() == null || treeNode.getChildren().isEmpty()) {
            treeNode.setIsLeaf(true);
        } else {
            treeNode.setIsLeaf(false);
            for (Tree temp : treeNode.getChildren()) {
                setLeaf(temp);
            }
        }
    }
}
