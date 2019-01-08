package com.simple.manage.system.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Description 角色菜单服务接口
 * Author chen
 * CreateTime 2018-09-12 14:39
 **/

public interface RoleMenuService {
    /**
     * 批量添加角色菜单关系
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    int saveRoleMenu(int currentUserId, JSONObject obj);

    /**
     * 查询角色菜单关系
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryRoleMenuList(Map<String, Object> params);

    /**
     * 查询当前用户可用菜单
     *
     * @param rIdList
     * @return
     */
    List<Map<String, Object>> queryAvlRoleMenuList(List<Integer> rIdList);

    /**
     * 查询角色菜单操作
     *
     * @param roleId
     * @param page
     * @param size
     * @return
     */
    PageInfo queryRoleMenuOperate(int roleId, int page, int size);

    /**
     * 批量保存角色菜单操作
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    int saveRoleMenuOperate(int currentUserId, JSONObject obj);

    /**
     * 根据当前路由匹配菜单中url获取当前角色菜单操作码
     *
     * @param params
     * @return
     */
    String queryMenuOperateCode(Map<String, Object> params);
}
