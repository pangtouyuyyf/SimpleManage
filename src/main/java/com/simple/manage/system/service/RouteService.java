package com.simple.manage.system.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Description 路由操作服务接口
 * Author chen
 * CreateTime 2018-09-06 17:44
 **/

public interface RouteService {
    /**
     * 添加或更新路由
     *
     * @param route
     * @return
     */
    int addOrUpdRoute(Map<String, Object> route);

    /**
     * 查询路由
     *
     * @param routeId
     * @return
     */
    Map<String, Object> queryRoute(int routeId);

    /**
     * 查询路由列表
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    PageInfo queryRouteList(Map<String, Object> params, int page, int size);

    /**
     * 逻辑删除路由
     *
     * @param routeId
     * @param userId
     * @return
     */
    int delRoute(int routeId, int userId);

    /**
     * 删除路由
     *
     * @param routeId
     * @return
     */
    int delRouteForReal(int routeId);
}
