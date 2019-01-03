package com.simple.manage.system.dao;

import com.simple.manage.system.entity.Org;
import com.simple.manage.system.entity.Tree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 组织数据操作
 * Author chen
 * CreateTime 2018-12-18 11:03
 **/
@Mapper
public interface OrgDao {
    /**
     * 检查数据是否存在
     *
     * @param corpId
     * @return
     */
    int checkOrg(Integer corpId);

    /**
     * 添加组织
     *
     * @param org
     * @return
     */
    int addOrg(Map<String, Object> org);

    /**
     * 更新组织
     *
     * @param org
     * @return
     */
    int updOrg(Map<String, Object> org);

    /**
     * 查询信息
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
     * @return
     */
    List<Map<String, Object>> queryOrgList(Map<String, Object> param);

    /**
     * 根据登录用户查询组织列表
     *
     * @param loginName
     * @return
     */
    List<Map<String, Object>> queryOrgListByLoginName(String loginName);

    /**
     * 查询链接
     *
     * @param orgId
     * @return
     */
    List<Tree> queryOrgRecursion(int orgId);
}
