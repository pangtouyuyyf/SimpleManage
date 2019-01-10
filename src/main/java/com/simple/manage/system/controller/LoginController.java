package com.simple.manage.system.controller;

import com.simple.manage.system.annotation.TokenAnnotation;
import com.simple.manage.system.config.JwtConfig;
import com.simple.manage.system.config.SysConfig;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.entity.User;
import com.simple.manage.system.redis.RedisOperation;
import com.simple.manage.system.service.*;
import com.simple.manage.system.util.CommonUtil;
import com.simple.manage.system.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 登录涉及操作
 * Author chen
 * CreateTime 2018-06-05 15:55
 **/
@RestController
@RequestMapping(value = "/sys")
public class LoginController extends BaseController {
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CorporationService corporationService;

    @Autowired
    private CommonService commonService;

    /**
     * 系统登录
     *
     * @param loginName 登录名(手机号)
     * @param password  密码
     * @param orgId     组织主键
     * @param channel   客户端渠道(app/web)
     * @return
     */
    @GetMapping(value = "/login")
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
                        @RequestParam("orgId") Integer orgId,
                        @RequestParam("channel") String channel) throws Exception {
        if (!CommonUtil.CHANNEL_WEB.equals(channel) && !CommonUtil.CHANNEL_APP.equals(channel)) {
            LogUtil.error(LoginController.class, LocalDateTime.now() + " 登录参数有误");
            return this.fail("登录参数有误");
        }

        Map<String, Object> params = new HashMap<>();
        params.put("phone", loginName);
        params.put("password", password);

        //查询用户
        User user = this.userService.queryUserEntity(params);
        if (user == null) {
            LogUtil.error(LoginController.class, LocalDateTime.now() + " 用户查询失败");
            return this.fail("用户名密码错误");
        }

        //查询当前登录用户角色
        params.clear();
        params.put("user_id", user.getId());
        params.put("org_id", orgId);
        List<Integer> rIdList = this.roleService.queryCurUserRole(params);
        if (rIdList == null || rIdList.isEmpty()) {
            LogUtil.error(LoginController.class, LocalDateTime.now() + " 用户:" + user.getId() + " 角色查询失败");
            return this.fail("该用户没有角色");
        }

        return this.success(loginOperate(user, rIdList, orgId, channel), null);
    }

    /**
     * 注销
     *
     * @param channel 客户端渠道(app/web)
     * @return
     */
    @TokenAnnotation
    @GetMapping(value = "/logout")
    public Result logout(@RequestParam("channel") String channel) throws Exception {
        if (!CommonUtil.CHANNEL_WEB.equals(channel) && !CommonUtil.CHANNEL_APP.equals(channel)) {
            LogUtil.error(LoginController.class, LocalDateTime.now() + " 注销参数有误");
            return this.fail();
        }

        List<String> tokenKeyParts = Arrays.asList(CommonUtil.TOKEN_PREFIX, channel,
                Integer.toString(getLoginInfo().getUser().getId()), Integer.toString(getLoginInfo().getOrgId()));
        this.redisOperation.deleteStr(String.join(CommonUtil.UNDERLINE, tokenKeyParts));

        if (sysConfig.isCleanLoginInfo()) {
            //清除当前登录信息缓存
            List<String> loginInfoKeyParts = Arrays.asList(CommonUtil.LOGIN_INFO_PREFIX,
                    Integer.toString(getLoginInfo().getUser().getId()), Integer.toString(getLoginInfo().getOrgId()));
            this.redisOperation.deleteObj(String.join(CommonUtil.UNDERLINE, loginInfoKeyParts));
        }

        return success();
    }

    /**
     * 获取登录用户所在公司
     *
     * @param loginName 登录名
     * @return
     */
    @GetMapping(value = "/logCorp")
    public Result logOrg(@RequestParam("loginName") String loginName) throws Exception {
        return success(this.corporationService.queryCorpListByLoginName(loginName));
    }

    /**
     * 登录通用操作
     *
     * @param user    用户信息
     * @param rIdList 角色主键集合
     * @param orgId   公司编号
     * @param channel 客户端渠道(app/web)
     * @return
     */
    private String loginOperate(User user, List<Integer> rIdList, int orgId, String channel) {
        //生成令牌
        String token = this.jwtService.createJWT(Integer.toString(user.getId()), Integer.toString(orgId), channel);

        //生成令牌缓存主键
        List<String> tokenKeyParts = Arrays.asList(
                CommonUtil.TOKEN_PREFIX, Integer.toString(user.getId()), Integer.toString(orgId), channel);
        String tokenRedisKey = String.join(CommonUtil.UNDERLINE, tokenKeyParts);

        //生成个人信息缓存主键
        List<String> loginInfoKeyParts = Arrays.asList(
                CommonUtil.LOGIN_INFO_PREFIX, Integer.toString(user.getId()), Integer.toString(orgId), channel);
        String loginInfoKey = String.join(CommonUtil.UNDERLINE, loginInfoKeyParts);

        //保存令牌
        if (CommonUtil.CHANNEL_WEB.equals(channel)) {
            this.redisOperation.setStr(tokenRedisKey, token, jwtConfig.getWebLifecycle());
        } else {
            this.redisOperation.setStr(tokenRedisKey, token, jwtConfig.getAppLifecycle());
        }

        //保存当前登录信息
        this.commonService.saveLoginInfo(loginInfoKey, user, rIdList, orgId, channel);

        return token;
    }
}
