package com.simple.manage.system.service.impl;

import com.simple.manage.system.dao.RoleDao;
import com.simple.manage.system.dao.UserDao;
import com.simple.manage.system.domain.LoginInfo;
import com.simple.manage.system.domain.LoginInfoResult;
import com.simple.manage.system.entity.User;
import com.simple.manage.system.redis.RedisOperation;
import com.simple.manage.system.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Description 公共服务接口实现
 * Author chen
 * CreateTime 2018-12-28 9:52
 **/
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 保存更新登录信息
     *
     * @param loginInfoKey 用户信息缓存主键
     * @param userId       用户主键
     * @param channel      登录渠道
     */
    public LoginInfoResult saveLoginInfo(String loginInfoKey, int userId, String channel) {
        LoginInfoResult loginInfoResult = new LoginInfoResult();

        LoginInfo loginInfo = (LoginInfo) this.redisOperation.getObj(loginInfoKey);
        if (loginInfo != null) {
            loginInfoResult.setChecked(true);
            loginInfoResult.setLoginInfo(loginInfo);
            return loginInfoResult;
        }

        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        User user = this.userDao.queryUserEntity(param);

        return this.saveLoginInfo(loginInfoKey, user, channel);
    }

    /**
     * 保存更新登录信息
     *
     * @param loginInfoKey 用户信息缓存主键
     * @param user         用户
     * @param channel      登录渠道
     */
    public LoginInfoResult saveLoginInfo(String loginInfoKey, User user, String channel) {
        LoginInfoResult loginInfoResult = new LoginInfoResult();
        //防止用户信息变更仍可以用原来缓存信息登录系统
        if (user == null) {
            loginInfoResult.setChecked(false);
            loginInfoResult.setLoginInfo(null);
            return loginInfoResult;
        }

        LoginInfo loginInfo = Optional.ofNullable((LoginInfo) this.redisOperation.getObj(loginInfoKey)).orElseGet(() -> {
            //保存当前登录信息
            LoginInfo temp = new LoginInfo();
            temp.setChannel(channel);
            temp.setUser(user);
            this.redisOperation.setObj(loginInfoKey, temp);
            return temp;
        });

        loginInfoResult.setChecked(true);
        loginInfoResult.setLoginInfo(loginInfo);

        return loginInfoResult;
    }

    /**
     * 批量删除登录信息
     *
     * @param regex
     */
    public void deleteLoginInfo(String regex) {
        this.redisOperation.deleteBatch(regex);
    }
}
