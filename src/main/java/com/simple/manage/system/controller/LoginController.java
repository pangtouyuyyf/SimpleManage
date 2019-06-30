package com.simple.manage.system.controller;

import com.github.pagehelper.util.StringUtil;
import com.simple.manage.system.annotation.TokenAnnotation;
import com.simple.manage.system.config.JwtConfig;
import com.simple.manage.system.config.SmsConfig;
import com.simple.manage.system.config.SysConfig;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.entity.User;
import com.simple.manage.system.redis.RedisOperation;
import com.simple.manage.system.service.*;
import com.simple.manage.system.util.CommonUtil;
import com.simple.manage.system.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
    private SmsConfig smsConfig;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private UserService userService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private SmsService smsService;

    public static final String REGEX_MOBILE = "^1[3|4|5|7|8][0-9]\\\\d{8}$";

    /**
     * 系统登录
     *
     * @param loginName 登录名(手机号)
     * @param password  密码
     * @param channel   客户端渠道(app/web)
     * @return
     */
    @GetMapping(value = "/login")
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password,
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

        return this.success(loginOperate(user, channel), null);
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
    @GetMapping(value = "/logOrg")
    public Result logOrg(@RequestParam("loginName") String loginName) throws Exception {
        return success(this.orgService.queryOrgListByLoginName(loginName));
    }

    /**
     * 登录通用操作
     *
     * @param user    用户信息
     * @param channel 客户端渠道(app/web)
     * @return
     */
    private String loginOperate(User user, String channel) {
        //生成令牌
        String token = this.jwtService.createJWT(Integer.toString(user.getId()), channel);

        //生成令牌缓存主键
        List<String> tokenKeyParts = Arrays.asList(
                CommonUtil.TOKEN_PREFIX, Integer.toString(user.getId()), channel);
        String tokenRedisKey = String.join(CommonUtil.UNDERLINE, tokenKeyParts);

        //生成个人信息缓存主键
        List<String> loginInfoKeyParts = Arrays.asList(
                CommonUtil.LOGIN_INFO_PREFIX, Integer.toString(user.getId()), channel);
        String loginInfoKey = String.join(CommonUtil.UNDERLINE, loginInfoKeyParts);

        //保存令牌
        if (CommonUtil.CHANNEL_WEB.equals(channel)) {
            this.redisOperation.setStr(tokenRedisKey, token, jwtConfig.getWebLifecycle());
        } else {
            this.redisOperation.setStr(tokenRedisKey, token, jwtConfig.getAppLifecycle());
        }

        //保存当前登录信息
        this.commonService.saveLoginInfo(loginInfoKey, user.getId(), channel);

        return token;
    }

    /**
     * 发送短信验证码
     *
     * @param mobile 手机号码
     * @return
     */
    @PostMapping(value = "/sendVerifySms")
    public Result<Object> sendVerifySms(@RequestParam("mobile") String mobile) throws Exception {
        Result result = null;
        String verifyCode = null;

        if (!Pattern.matches(REGEX_MOBILE, mobile.trim())) {
            return fail("手机号码格式不对!");
        }

        String redisKey = CommonUtil.SMS_VERIFY_CODE_PREFIX + mobile;
        verifyCode = redisOperation.getStr(redisKey);

        if (StringUtil.isEmpty(verifyCode)) {
            return fail("验证码已发送，请稍后重试");
        }

        //发送短信验证码
        verifyCode = smsService.sendVerifySms(mobile.trim());

        if (StringUtil.isNotEmpty(verifyCode)) {
            //保存短信验证码至redis并设置失效时间
            redisOperation.setStr(redisKey, verifyCode, smsConfig.getVerifyTimeOut());
            result = success("验证码已发送，请及时查收");
        } else {
            result = fail("验证码发送失败，稍后重试");
        }

        return result;
    }
}
