package com.simple.manage.system.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Description 用户组织关联服务接口
 * Author chen
 * CreateTime 2019-01-11 9:51
 **/

public interface UserOrgService {
    /**
     * 查询用户组织关联
     *
     * @param params
     * @return
     */
    List<Map<String, Integer>> queryUserOrgList(Map<String, Object> params);

    /**
     * 保存用户组织信息
     *
     * @param currentUserId
     * @param obj
     * @return
     */
    int saveUserOrg(int currentUserId, JSONObject obj);

    /**
     * 根据用户查询组织关联主键列表
     *
     * @param userId
     * @return
     */
    List<Integer> queryUserOrgIdList(int userId);
}
