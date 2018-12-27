package com.simple.manage.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.dao.OrgDao;
import com.simple.manage.system.entity.Tree;
import com.simple.manage.system.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description 组织数据服务接口实现
 * Author chen
 * CreateTime 2018-12-18 11:10
 **/
@Service
public class OrgServiceImpl implements OrgService {
    @Autowired
    private OrgDao orgDao;

    /**
     * 添加或更新组织
     *
     * @param org
     * @return
     */
    public int addOrUpdOrg(Map<String, Object> org) {
        return this.orgDao.addOrUpdOrg(org);
    }


    /**
     * 查询组织信息
     *
     * @param orgId
     * @return
     */
    public Map<String, Object> queryOrg(int orgId) {
        return this.orgDao.queryOrg(orgId);
    }

    /**
     * 查询组织列表
     *
     * @param param
     * @param page
     * @param size
     * @return
     */
    public PageInfo queryOrgList(Map<String, Object> param, int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> orgDao.queryOrgList(param));
    }

    /**
     * 查询链接
     *
     * @param orgId
     * @return
     */
    public List<Tree> queryOrgRecursion(int orgId) {
        List<Tree> tree = this.orgDao.queryOrgRecursion(orgId);
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