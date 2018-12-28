package com.simple.manage.system.service.impl;

import com.simple.manage.system.dao.OrgDao;
import com.simple.manage.system.dao.RoleDao;
import com.simple.manage.system.dao.UserDao;
import com.simple.manage.system.domain.LoginInfo;
import com.simple.manage.system.entity.Org;
import com.simple.manage.system.entity.Role;
import com.simple.manage.system.entity.User;
import com.simple.manage.system.redis.RedisOperation;
import com.simple.manage.system.service.CommonService;
import com.simple.manage.system.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public LoginInfo saveLoginInfo(int userId, int roleId, String channel) {
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
    public LoginInfo saveLoginInfo(User user, Role role, String channel) {
        Org org = this.orgDao.queryOrgEntity(role.getOrgId());
        return this.saveLoginInfo(user, role, org, channel);
    }

    /**
     * 保存更新登录信息
     *
     * @param user
     * @param role
     * @param org
     * @param channel
     */
    public LoginInfo saveLoginInfo(User user, Role role, Org org, String channel) {
        //保存当前登录信息
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setChannel(channel);
        loginInfo.setUser(user);
        loginInfo.setRole(role);
        loginInfo.setOrg(org);
        List<String> loginInfoKeyParts = Arrays.asList(CommonUtil.LOGIN_INFO_PREFIX, channel, Integer.toString(user.getId()), Integer.toString(role.getId()));
        this.redisOperation.setObj(String.join(CommonUtil.UNDERLINE, loginInfoKeyParts), loginInfo);
        return loginInfo;
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
