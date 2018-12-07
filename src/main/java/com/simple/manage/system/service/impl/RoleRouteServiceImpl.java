package com.simple.manage.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.manage.system.dao.RoleRouteDao;
import com.simple.manage.system.service.RoleRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description 角色路由服务接口实现
 * Author chen
 * CreateTime 2018-09-12 15:21
 **/
@Service
public class RoleRouteServiceImpl implements RoleRouteService {
    @Autowired
    private RoleRouteDao roleRouteDao;

    /**
     * 批量添加角色路由关系
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    @Transactional
    public int saveRoleRoute(int currentUserId, JSONObject obj) {
        int result = 0;
        if (!obj.containsKey("roleId") || !obj.containsKey("list")) {
            return result;
        }
        int roleId = obj.getInteger("roleId");
        JSONArray arr = obj.getJSONArray("list");

        Map<String, Object> param = new HashMap<>();
        param.put("role_id", roleId);

        //查询现有角色路由
        List<Map<String, Integer>> savedList = Optional.of(this.roleRouteDao.queryAll(param)).orElseGet(() -> {
            return new ArrayList<>();
        });

        //生成前台保存角色路由
        Map<String, Integer> roleRoute = null;
        List<Map<String, Integer>> roleRoutes = new ArrayList<>();

        List<Integer> routeIds = JSONArray.parseArray(JSONObject.toJSONString(arr), Integer.class);
        if (routeIds != null && !routeIds.isEmpty()) {
            for (int routeId : routeIds) {
                roleRoute = new HashMap<>();
                roleRoute.put("role_id", roleId);
                roleRoute.put("route_id", routeId);
                roleRoutes.add(roleRoute);
            }
        }

        //获取待删除的集合并删除
        List<Map<String, Integer>> delList = savedList.stream().filter(item -> !roleRoutes.contains(item)).collect(Collectors.toList());
        if (delList != null && !delList.isEmpty()) {
            result += this.roleRouteDao.delRoleRouteBatch(delList);
        }

        //获取待保存集合(去重)并保存
        List<Map<String, Integer>> tempAddList = roleRoutes.stream().distinct().filter(item -> !savedList.contains(item)).collect(Collectors.toList());
        if (tempAddList != null && !tempAddList.isEmpty()) {
            List<Map<String, Object>> addList = new ArrayList<>();
            Map<String, Object> tempAdd = null;
            for (Map<String, Integer> temp : tempAddList) {
                tempAdd = new HashMap<>();
                tempAdd.putAll(temp);
                tempAdd.put("create_id", currentUserId);
                tempAdd.put("create_time", LocalDateTime.now());
                addList.add(tempAdd);
            }
            result += this.roleRouteDao.addRoleRoute(addList);
        }

        return result;
    }

    /**
     * 查询角色路由关系
     *
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryRoleRouteList(Map<String, Object> params) {
        return this.roleRouteDao.queryRoleRouteList(params);
    }

    /**
     * 统计角色访问路由
     *
     * @param params
     * @return
     */
    public int countRoleRoute(Map<String, Object> params) {
        return this.roleRouteDao.countRoleRoute(params);
    }
}
