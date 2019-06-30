package com.simple.manage.system.service;

import com.simple.manage.system.domain.LoginInfoResult;
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
     * @param loginInfoKey
     * @param userId
     * @param channel
     */
    LoginInfoResult saveLoginInfo(String loginInfoKey, int userId, String channel);

    /**
     * 保存更新登录信息
     *
     * @param loginInfoKey
     * @param user
     * @param channel
     */
    LoginInfoResult saveLoginInfo(String loginInfoKey, User user, String channel);

    /**
     * 批量删除登录信息
     *
     * @param regex
     */
    void deleteLoginInfo(String regex);
}
