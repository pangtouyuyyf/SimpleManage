package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 用户角色关联数据操作接口
 * Author chen
 * CreateTime 2018-08-03 16:26
 **/
@Mapper
public interface UserRoleDao {

    int addUserRole(List<Map<String, Object>> userRoles);

    /**
     * 查询用户角色列表
     *
     * @param userId
     * @return
     */
    List<Map<String, Object>> queryUserRoleList(int userId);

    /**
     * 删除用户角色
     *
     * @param params
     * @return
     */
    int delUserRole(Map<String, Object> params);
}
