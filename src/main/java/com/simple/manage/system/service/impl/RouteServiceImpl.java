package com.simple.manage.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.dao.RoleRouteDao;
import com.simple.manage.system.dao.RouteDao;
import com.simple.manage.system.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 路由操作服务接口实现
 * Author chen
 * CreateTime 2018-09-06 17:45
 **/
@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteDao routeDao;

    @Autowired
    private RoleRouteDao roleRouteDao;

    /**
     * 添加或更新路由
     *
     * @param route
     * @return
     */
    public int addOrUpdRoute(Map<String, Object> route) {
        return this.routeDao.addOrUpdRoute(route);
    }

    /**
     * 查询路由
     *
     * @param routeId
     * @return
     */
    public Map<String, Object> queryRoute(int routeId) {
        return this.routeDao.queryRoute(routeId);
    }

    /**
     * 查询路由列表
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    public PageInfo queryRouteList(Map<String, Object> params, int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> routeDao.queryRouteList(params));
    }

    /**
     * 逻辑删除路由
     *
     * @param routeId
     * @param userId
     * @return
     */
    @Transactional
    public int delRoute(int routeId, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("route_id", routeId);

        //删除关联
        this.roleRouteDao.delRoleRoute(params);

        params.put("update_id", userId);
        params.put("update_time", LocalDateTime.now());
        params.put("is_delete", "1");

        return this.routeDao.delRoute(params);
    }

    /**
     * 删除路由
     *
     * @param routeId
     * @return
     */
    @Transactional
    public int delRouteForReal(int routeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("route_id", routeId);

        //删除关联
        this.roleRouteDao.delRoleRoute(params);

        return this.routeDao.delRouteForReal(routeId);
    }
}
