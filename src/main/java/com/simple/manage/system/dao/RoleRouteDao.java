package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 角色路由关联数据操作
 * Author chen
 * CreateTime 2018-09-07 10:48
 **/
@Mapper
public interface RoleRouteDao {
    /**
     * 批量添加角色路由关系
     *
     * @param roleRoute
     * @return
     */
    int addRoleRoute(List<Map<String, Object>> roleRoute);

    /**
     * 查询角色路由关系
     *
     * @param params
     * @return
     */
    List<Map<String, Integer>> queryAll(Map<String, Object> params);

    /**
     * 批量删除角色路由关系
     *
     * @param roleRoute
     * @return
     */
    int delRoleRouteBatch(List<Map<String, Integer>> roleRoute);

    /**
     * 删除角色路由关系
     *
     * @param params
     * @return
     */
    int delRoleRoute(Map<String, Object> params);

    /**
     * 查询角色路由关系
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryRoleRouteList(Map<String, Object> params);

    /**
     * 统计角色访问路由
     *
     * @param params
     * @return
     */
    int countRoleRoute(Map<String, Object> params);

    /**
     * 根据角色主键查询所有
     *
     * @param roleId
     * @return
     */
    List<Map<String, Object>> queryAllByRoleId(int roleId);
}
