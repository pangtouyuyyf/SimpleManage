package com.simple.manage.system.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Description 角色请求服务接口
 * Author chen
 * CreateTime 2018-08-07 11:04
 **/

public interface RoleAccessService {
    /**
     * 批量添加角色请求关系
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    int saveRoleAccess(int currentUserId, JSONObject obj);

    /**
     * 查询角色请求关系
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryRoleAccessList(Map<String, Object> params);

    /**
     * 统计角色可使用某access数量
     *
     * @param params
     * @return
     */
    int countRoleAccess(Map<String, Object> params);
}
