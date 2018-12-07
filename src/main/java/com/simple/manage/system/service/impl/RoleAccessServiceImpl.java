package com.simple.manage.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.manage.system.dao.RoleAccessDao;
import com.simple.manage.system.service.RoleAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Description 角色请求服务接口实现
 * Author chen
 * CreateTime 2018-08-07 11:05
 **/
@Service
public class RoleAccessServiceImpl implements RoleAccessService {
    @Autowired
    private RoleAccessDao roleAccessDao;

    /**
     * 批量添加角色请求关系
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    @Transactional
    public int saveRoleAccess(int currentUserId, JSONObject obj) {
        int result = 0;
        if (!obj.containsKey("roleId") || !obj.containsKey("list")) {
            return result;
        }
        int roleId = obj.getInteger("roleId");
        JSONArray arr = obj.getJSONArray("list");

        Map<String, Object> param = new HashMap<>();
        param.put("role_id", roleId);

        //查询现有角色请求
        List<Map<String, Integer>> savedList = Optional.of(this.roleAccessDao.queryAll(param)).orElseGet(() -> {
            return new ArrayList<>();
        });

        //生成前台保存角色请求
        Map<String, Integer> roleAccess = null;
        List<Map<String, Integer>> roleAccesses = new ArrayList<>();

        List<Integer> accessIds = JSONArray.parseArray(JSONObject.toJSONString(arr), Integer.class);
        if (accessIds != null && !accessIds.isEmpty()) {
            for (int accessId : accessIds) {
                roleAccess = new HashMap<>();
                roleAccess.put("role_id", roleId);
                roleAccess.put("access_id", accessId);
                roleAccesses.add(roleAccess);
            }
        }

        //获取待删除的集合并删除
        List<Map<String, Integer>> delList = savedList.stream().filter(item -> !roleAccesses.contains(item)).collect(Collectors.toList());
        if (delList != null && !delList.isEmpty()) {
            result += this.roleAccessDao.delRoleAccessBatch(delList);
        }

        //获取待保存集合(去重)并保存
        List<Map<String, Integer>> tempAddList = roleAccesses.stream().distinct().filter(item -> !savedList.contains(item)).collect(Collectors.toList());
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
            result += this.roleAccessDao.addRoleAccess(addList);
        }

        return result;
    }

    /**
     * 查询角色请求关系
     *
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryRoleAccessList(Map<String, Object> params) {
        return this.roleAccessDao.queryRoleAccessList(params);
    }

    /**
     * 统计角色可使用某access数量
     *
     * @param params
     * @return
     */
    public int countRoleAccess(Map<String, Object> params) {
        return this.roleAccessDao.countRoleAccess(params);
    }
}
