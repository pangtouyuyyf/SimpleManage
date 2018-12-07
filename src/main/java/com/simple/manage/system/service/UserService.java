package com.simple.manage.system.service;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Description 用户数据接口
 * Author chen
 * CreateTime 2018-07-19 16:13
 **/

public interface UserService {
    /**
     * 添加或更新用户
     *
     * @param user
     * @return
     */
    int addOrUpdUser(Map<String, Object> user);

    /**
     * 查询用户对象
     *
     * @param params
     * @return
     */
    User queryUserEntity(Map<String, Object> params);

    /**
     * 查询用户
     *
     * @param userId
     * @return
     */
    Map<String, Object> queryUser(int userId);

    /**
     * 查询用户列表
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    PageInfo queryUserList(Map<String, Object> params, int page, int size);

    /**
     * 逻辑删除用户
     *
     * @param userId
     * @param currentUserId
     * @return
     */
    int delUser(int userId, int currentUserId);

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    int delUserForReal(int userId);
}
