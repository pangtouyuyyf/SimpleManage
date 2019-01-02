package com.simple.manage.system.service.impl;

import com.simple.manage.system.dao.OrgDao;
import com.simple.manage.system.dao.RoleDao;
import com.simple.manage.system.dao.UserDao;
import com.simple.manage.system.domain.LoginInfo;
import com.simple.manage.system.domain.LoginInfoResult;
import com.simple.manage.system.entity.Role;
import com.simple.manage.system.entity.User;
import com.simple.manage.system.redis.RedisOperation;
import com.simple.manage.system.service.CommonService;
import com.simple.manage.system.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private OrgDao orgDao;

    /**
     * 保存更新登录信息
     *
     * @param userId
     * @param roleId
     * @param channel
     */
    public LoginInfoResult saveLoginInfo(int userId, int roleId, String channel) {
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        User user = this.userDao.queryUserEntity(param);
        Role role = this.roleDao.queryRoleEntity(roleId);
        return this.saveLoginInfo(user, role, channel);
    }

    /**
     * 保存更新登录信息
     *
     * @param user
     * @param role
     * @param channel
     */
    public LoginInfoResult saveLoginInfo(User user, Role role, String channel) {
        LoginInfoResult loginInfoResult = new LoginInfoResult();
        //防止用户信息变更仍可以用原来缓存信息登录系统
        if (user == null || role == null) {
            loginInfoResult.setChecked(false);
            loginInfoResult.setLoginInfo(null);
            return loginInfoResult;
        }

        List<String> loginInfoKeyParts = Arrays.asList(CommonUtil.LOGIN_INFO_PREFIX, Integer.toString(user.getId()), Integer.toString(role.getId()), channel);
        String loginInfoKey = String.join(CommonUtil.UNDERLINE, loginInfoKeyParts);

        LoginInfo loginInfo = Optional.ofNullable((LoginInfo) this.redisOperation.getObj(loginInfoKey)).orElseGet(() -> {
            //保存当前登录信息
            LoginInfo temp = new LoginInfo();
            temp.setChannel(channel);
            temp.setUser(user);
            temp.setRole(role);
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
