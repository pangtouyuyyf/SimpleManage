package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 角色菜单关联数据操作接口
 * Author chen
 * CreateTime 2018-08-03 16:28
 **/
@Mapper
public interface RoleMenuDao {
    /**
     * 批量添加角色菜单关系
     *
     * @param roleMenu
     * @return
     */
    int addRoleMenu(List<Map<String, Object>> roleMenu);

    /**
     * 查询角色菜单关系
     *
     * @param params
     * @return
     */
    List<Map<String, Integer>> queryAll(Map<String, Object> params);

    /**
     * 批量删除角色菜单关系
     *
     * @param roleMenu
     * @return
     */
    int delRoleMenuBatch(List<Map<String, Integer>> roleMenu);

    /**
     * 删除角色菜单关系
     *
     * @param params
     * @return
     */
    int delRoleMenu(Map<String, Object> params);

    /**
     * 查询角色菜单关系
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryRoleMenuList(Map<String, Object> params);

    /**
     * 查询角色可用菜单
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryAvlRoleMenuList(Map<String, Object> params);

    /**
     * 查询角色菜单操作
     *
     * @param roleId
     * @return
     */
    List<Map<String, Object>> queryRoleMenuOperate(int roleId);

    /**
     * 查询所有菜单操作
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryAllOperate(Map<String, Object> params);

    /**
     * 批量保存角色菜单操作
     *
     * @param roleMenuOperate
     * @return
     */
    int updRoleMenuOperate(List<Map<String, Object>> roleMenuOperate);

    /**
     * 根据当前路由匹配菜单中url获取当前角色菜单操作码
     *
     * @param params
     * @return
     */
    String queryMenuOperateCode(Map<String, Object> params);

    /**
     * 根据角色主键查询所有
     *
     * @param roleId
     * @return
     */
    List<Map<String, Object>> queryAllByRoleId(int roleId);
}
