package com.simple.manage.system.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Description 用户角色操作服务接口
 * Author chen
 * CreateTime 2018-09-11 11:22
 **/

public interface UserRoleService {
    /**
     * 查询用户角色列表
     *
     * @param userId
     * @return
     */
    List<Map<String, Object>> queryUserRoleList(int userId);

    /**
     * 保存用户角色信息
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    int saveUserRole(int currentUserId, JSONObject obj);
}
