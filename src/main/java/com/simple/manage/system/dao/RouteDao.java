package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 前端路由表
 * Author chen
 * CreateTime 2018-09-06 16:32
 **/
@Mapper
public interface RouteDao {
    /**
     * 检查数据是否存在
     *
     * @param routeId
     * @return
     */
    int checkRoute(Integer routeId);

    /**
     * 添加路由
     *
     * @param route
     * @return
     */
    int addRoute(Map<String, Object> route);

    /**
     * 更新路由
     *
     * @param route
     * @return
     */
    int updRoute(Map<String, Object> route);

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
     * @return
     */
    List<Map<String, Object>> queryRouteList(Map<String, Object> params);

    /**
     * 逻辑删除路由
     *
     * @param params
     * @return
     */
    int delRoute(Map<String, Object> params);

    /**
     * 删除路由
     *
     * @param routeId
     * @return
     */
    int delRouteForReal(int routeId);
}
