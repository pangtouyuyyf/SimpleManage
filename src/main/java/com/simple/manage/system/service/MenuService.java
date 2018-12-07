package com.simple.manage.system.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Description 菜单服务接口
 * Author chen
 * CreateTime 2018-08-02 11:32
 **/

public interface MenuService {
    /**
     * 添加或更新菜单
     *
     * @param menu
     * @return
     */
    int addOrUpdMenu(Map<String, Object> menu);

    /**
     * 查询菜单
     *
     * @param menuId
     * @return
     */
    Map<String, Object> queryMenu(int menuId);

    /**
     * 查询菜单
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    PageInfo queryMenuList(Map<String, Object> params, int page, int size);

    /**
     * 逻辑删除菜单
     *
     * @param menuId 菜单号
     * @param userId 用户
     * @return
     */
    int delMenu(int menuId, int userId);

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    int delMenuForReal(int menuId);
}
