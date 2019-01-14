package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 用户公司数据操作
 * Author chen
 * CreateTime 2019-01-10 9:10
 **/
@Mapper
public interface UserOrgDao {
    /**
     * 批量添加用户组织关系
     *
     * @param userOrg
     * @return
     */
    int addUserOrg(List<Map<String, Object>> userOrg);

    /**
     * 查询用户组织关系
     *
     * @param params
     * @return
     */
    List<Map<String, Integer>> queryAll(Map<String, Object> params);

    /**
     * 批量删除用户组织关系
     *
     * @param userOrg
     * @return
     */
    int delUserOrgBatch(List<Map<String, Integer>> userOrg);

    /**
     * 删除用户组织关系
     *
     * @param params
     * @return
     */
    int delUserOrg(Map<String, Object> params);

    /**
     * 查询用户组织关联
     *
     * @param params
     * @return
     */
    List<Map<String, Integer>> queryUserOrgList(Map<String, Object> params);

    /**
     * 根据用户查询组织关联主键列表
     *
     * @param userId
     * @return
     */
    List<Integer> queryUserOrgIdList(int userId);
}
