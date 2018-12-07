package com.simple.manage.system.dao;

import com.simple.manage.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 角色数据操作接口
 * Author chen
 * CreateTime 2018-08-02 14:44
 **/
@Mapper
public interface RoleDao {
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
     * @return
     */
    List<Map<String, Object>> queryRoleList(Map<String, Object> params);

    /**
     * 逻辑删除角色
     *
     * @param params
     * @return
     */
    int delRole(Map<String, Object> params);

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