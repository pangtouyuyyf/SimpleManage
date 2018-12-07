package com.simple.manage.system.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Description 角色路由服务接口
 * Author chen
 * CreateTime 2018-09-12 15:21
 **/

public interface RoleRouteService {
    /**
     * 批量添加角色路由关系
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    int saveRoleRoute(int currentUserId, JSONObject obj);

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
}
