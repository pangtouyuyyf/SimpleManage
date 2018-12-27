package com.simple.manage.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.manage.system.dao.UserRoleDao;
import com.simple.manage.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 用户角色操作服务接口实现
 * Author chen
 * CreateTime 2018-09-11 11:23
 **/
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 查询用户角色列表
     *
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryUserRoleList(Map<String, Object> params) {
        return this.userRoleDao.queryUserRoleList(params);
    }

    /**
     * 保存用户角色信息
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    @Transactional
    public int saveUserRole(int currentUserId, JSONObject obj) {
        int result = 0;
        int userId = obj.getInteger("userId");

        //删除原有用户角色
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        result += this.userRoleDao.delUserRole(params);

        JSONArray arr = obj.getJSONArray("list");
        if (arr == null || arr.isEmpty()) {
            return result;
        }

        //新增现配用户角色
        Map<String, Object> userRole = null;
        List<Map<String, Object>> userRoles = new ArrayList<>();
        List<Integer> roleIds = JSONArray.parseArray(JSONObject.toJSONString(arr), Integer.class);
        for (int roleId : roleIds) {
            userRole = new HashMap<>();
            userRole.put("user_id", userId);
            userRole.put("role_id", roleId);
            userRole.put("create_id", currentUserId);
            userRole.put("create_time", LocalDateTime.now());
            userRoles.add(userRole);
        }
        result += this.userRoleDao.addUserRole(userRoles);

        return result;
    }
}
