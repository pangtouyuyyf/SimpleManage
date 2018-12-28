package com.simple.manage.system.service;

import com.simple.manage.system.domain.LoginInfo;
import com.simple.manage.system.entity.Org;
import com.simple.manage.system.entity.Role;
import com.simple.manage.system.entity.User;

/**
 * Description 公共服务接口
 * Author chen
 * CreateTime 2018-12-28 9:52
 **/

public interface CommonService {
    /**
     * 保存更新登录信息
     *
     * @param userId
     * @param roleId
     * @param channel
     */
    LoginInfo saveLoginInfo(int userId, int roleId, String channel);

    /**
     * 保存更新登录信息
     *
     * @param user
     * @param role
     * @param channel
     */
    LoginInfo saveLoginInfo(User user, Role role, String channel);

    /**
     * 保存更新登录信息
     *
     * @param user
     * @param role
     * @param org
     * @param channel
     */
    LoginInfo saveLoginInfo(User user, Role role, Org org, String channel);

    /**
     * 批量删除登录信息
     *
     * @param regex
     */
    void deleteLoginInfo(String regex);
}