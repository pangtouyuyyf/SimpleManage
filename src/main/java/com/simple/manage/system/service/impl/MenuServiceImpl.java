package com.simple.manage.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.dao.MenuDao;
import com.simple.manage.system.dao.RoleMenuDao;
import com.simple.manage.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 菜单服务接口实现类
 * Author chen
 * CreateTime 2018-08-02 11:33
 **/
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RoleMenuDao roleMenuDao;

    /**
     * 添加或更新菜单
     *
     * @param menu
     * @return
     */
    public int addOrUpdMenu(Map<String, Object> menu) {
        return this.menuDao.addOrUpdMenu(menu);
    }

    /**
     * 查询菜单
     *
     * @param menuId
     * @return
     */
    public Map<String, Object> queryMenu(int menuId) {
        return this.menuDao.queryMenu(menuId);
    }

    /**
     * 查询菜单
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    public PageInfo queryMenuList(Map<String, Object> params, int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> menuDao.queryMenuList(params));

    }

    /**
     * 逻辑删除菜单
     *
     * @param menuId 菜单号
     * @param userId 用户
     * @return
     */
    @Transactional
    public int delMenu(int menuId, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("menu_id", menuId);

        //删除关联
        this.roleMenuDao.delRoleMenu(params);

        params.put("update_id", userId);
        params.put("update_time", LocalDateTime.now());
        params.put("is_delete", "1");

        return this.menuDao.delMenu(params);
    }

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    @Transactional
    public int delMenuForReal(int menuId) {
        Map<String, Object> params = new HashMap<>();
        params.put("menu_id", menuId);

        //删除关联
        this.roleMenuDao.delRoleMenu(params);

        return this.menuDao.delMenuForReal(menuId);
    }
}
