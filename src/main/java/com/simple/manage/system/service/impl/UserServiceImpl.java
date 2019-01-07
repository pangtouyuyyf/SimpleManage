package com.simple.manage.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.dao.UserDao;
import com.simple.manage.system.dao.UserRoleDao;
import com.simple.manage.system.entity.User;
import com.simple.manage.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 用户数据接口实现
 * Author chen
 * CreateTime 2018-07-19 16:14
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 添加或更新用户
     *
     * @param user
     * @return
     */
    public int addOrUpdUser(Map<String, Object> user) {
        int result = 0;
        Integer id = user.get("user_id") == null ? null : Integer.valueOf(user.get("user_id").toString());
        int count = this.userDao.checkUser(id);
        if (count == 0) {
            //新增
            result = this.userDao.addUser(user);
        } else if (count == 1) {
            //修改
            result = this.userDao.updUser(user);
        } else {
        }
        return result;
    }

    /**
     * 查询用户对象
     *
     * @param params
     * @return
     */
    public User queryUserEntity(Map<String, Object> params) {
        return this.userDao.queryUserEntity(params);
    }

    /**
     * 查询用户
     *
     * @param userId
     * @return
     */
    public Map<String, Object> queryUser(int userId) {
        return this.userDao.queryUser(userId);
    }

    /**
     * 查询用户列表
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    public PageInfo queryUserList(Map<String, Object> params, int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> userDao.queryUserList(params));

    }

    /**
     * 逻辑删除用户
     *
     * @param userId
     * @param currentUserId
     * @return
     */
    @Transactional
    public int delUser(int userId, int currentUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);

        //删除关联
        this.userRoleDao.delUserRole(params);

        params.put("update_id", currentUserId);
        params.put("update_time", LocalDateTime.now());
        params.put("is_delete", "1");

        return this.userDao.delUser(params);
    }

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @Transactional
    public int delUserForReal(int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);

        //删除关联
        this.userRoleDao.delUserRole(params);

        return this.userDao.delUserForReal(userId);
    }
}
