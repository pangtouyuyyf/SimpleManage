package com.simple.manage.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.manage.system.dao.UserOrgDao;
import com.simple.manage.system.service.UserOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 用户组织关联服务接口实现
 * Author chen
 * CreateTime 2019-01-11 9:53
 **/
@Service
public class UserOrgServiceImpl implements UserOrgService {
    @Autowired
    private UserOrgDao userOrgDao;

    /**
     * 查询用户组织关联
     *
     * @param params
     * @return
     */
    public List<Map<String, Integer>> queryUserOrgList(Map<String, Object> params) {
        return this.userOrgDao.queryUserOrgList(params);
    }

    /**
     * 保存用户组织信息
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    @Transactional
    public int saveUserOrg(int currentUserId, JSONObject obj) {
        int result = 0;
        int userId = obj.getInteger("userId");

        //删除原有用户组织
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        result += this.userOrgDao.delUserOrg(params);

        JSONArray arr = obj.getJSONArray("list");
        if (arr == null || arr.isEmpty()) {
            return result;
        }

        //新增现配用户组织
        Map<String, Object> userOrg = null;
        List<Map<String, Object>> userOrgList = new ArrayList<>();
        List<Integer> orgIds = JSONArray.parseArray(JSONObject.toJSONString(arr), Integer.class);
        for (int orgId : orgIds) {
            userOrg = new HashMap<>();
            userOrg.put("user_id", userId);
            userOrg.put("org_id", orgId);
            userOrg.put("create_id", currentUserId);
            userOrg.put("create_time", LocalDateTime.now());
            userOrgList.add(userOrg);
        }
        result += this.userOrgDao.addUserOrg(userOrgList);

        return result;
    }

    /**
     * 根据用户查询组织关联主键列表
     *
     * @param userId
     * @return
     */
    public List<Integer> queryUserOrgIdList(int userId) {
        return this.userOrgDao.queryUserOrgIdList(userId);
    }
}
