package com.simple.manage.system.service;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.entity.Role;

import java.util.Map;

/**
 * Description 角色服务接口
 * Author chen
 * CreateTime 2018-08-02 15:00
 **/

public interface RoleService {
    /**
     * 添加或更新角色
     *
     * @param role
     * @return
     */
    int addOrUpdRole(Map<String, Object> role);

    /**
     * 查询角色
     *
     * @param roleId
     * @return
     */
    Map<String, Object> queryRole(int roleId);

    /**
     * 查询角色对象
     *
     * @param roleId
     * @return
     */
    Role queryRoleEntity(int roleId);

    /**
     * 查询角色列表
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    PageInfo queryRoleList(Map<String, Object> params, int page, int size);

    /**
     * 逻辑删除角色
     *
     * @param roleId
     * @param userId
     * @return
     */
    int delRole(int roleId, int userId);

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    int delRoleForReal(int roleId);

    /**
     * 查询当前用户登录角色
     *
     * @param userId
     * @return
     */
    Role queryCurUserRole(int userId);
}
