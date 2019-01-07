package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 菜单数据操作接口
 * Author chen
 * CreateTime 2018-08-02 10:05
 **/
@Mapper
public interface MenuDao {
    /**
     * 检查数据是否存在
     *
     * @param menuId
     * @return
     */
    int checkMenu(Integer menuId);

    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    int addMenu(Map<String, Object> menu);

    /**
     * 更新菜单
     *
     * @param menu
     * @return
     */
    int updMenu(Map<String, Object> menu);

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
     * @return
     */
    List<Map<String, Object>> queryMenuList(Map<String, Object> params);

    /**
     * 逻辑删除菜单
     *
     * @param params
     * @return
     */
    int delMenu(Map<String, Object> params);

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    int delMenuForReal(int menuId);
}
