package com.simple.manage.system.service;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.entity.Org;
import com.simple.manage.system.entity.Tree;

import java.util.List;
import java.util.Map;

/**
 * Description 组织数据服务接口
 * Author chen
 * CreateTime 2018-12-18 11:07
 **/

public interface OrgService {
    /**
     * 添加或更新组织
     *
     * @param org
     * @return
     */
    int addOrUpdOrg(Map<String, Object> org);

    /**
     * 查询组织信息
     *
     * @param orgId
     * @return
     */
    Map<String, Object> queryOrg(int orgId);

    /**
     * 查询组织对象
     *
     * @param orgId
     * @return
     */
    Org queryOrgEntity(int orgId);

    /**
     * 查询组织列表
     *
     * @param param
     * @param page
     * @param size
     * @return
     */
    PageInfo queryOrgList(Map<String, Object> param, int page, int size);

    /**
     * 递归查询组织树
     *
     * @param param
     * @return
     */
    List<Tree> queryOrgRecursion(Map<String, Object> param);

    /**
     * 查询组织树根主键
     *
     * @param param
     * @return
     */
    int queryRootOrgId(Map<String, Object> param);

    /**
     * 根据登录用户查询组织列表
     *
     * @param loginName
     * @return
     */
    List<Map<String, Object>> queryOrgListByLoginName(String loginName);

    /**
     * 查询已选择的组织
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> querySelectedOrg(Map<String, Object> param);
}
