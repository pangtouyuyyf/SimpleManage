package com.simple.manage.system.aspact;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.simple.manage.system.config.JwtConfig;
import com.simple.manage.system.domain.LoginInfoResult;
import com.simple.manage.system.enums.SysExpEnum;
import com.simple.manage.system.redis.RedisOperation;
import com.simple.manage.system.service.CommonService;
import com.simple.manage.system.service.JwtService;
import com.simple.manage.system.util.CommonUtil;
import com.simple.manage.system.util.LogUtil;
import com.simple.manage.system.util.ResultUtil;
import io.netty.util.internal.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Description token验证
 * Author chen
 * CreateTime 2018-06-05 16:01
 **/
@Aspect
@Order(0)
@Component
public class TokenVerifyAspect {
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private CommonService commonService;

    /**
     * 业务controller类层面的Token验证(对所有TokenController实现类进行token验证),地毯式验证
     */
    @Pointcut("execution(* com.simple.manage.system.controller.TokenController+.*(..))")
    public void carpetTokenVerify() {
    }

    /**
     * 业务controller类方法自定义注解Token验证(对Controller类中方法用此注解进行token验证),精确式验证
     */
    @Pointcut("@annotation(com.simple.manage.system.annotation.TokenAnnotation)")
    public void exactTokenVerify() {
    }

    /**
     * 验证处理
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("carpetTokenVerify() || exactTokenVerify()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String token = request.getHeader(CommonUtil.TOKEN);

        DecodedJWT jwt = jwtService.parseJWT(token);

        /** 验证令牌合法性 **/
        if (jwt == null) {
            LogUtil.error(TokenVerifyAspect.class, LocalDateTime.now() + " 令牌验证失败");
            return ResultUtil.error(SysExpEnum.NEED_LOGIN);
        }

        /** 获取令牌中的用户、角色和登录渠道 **/
        String userId = jwt.getClaim(CommonUtil.USER_ID).asString();
        String roleId = jwt.getClaim(CommonUtil.ROLE_ID).asString();
        String channel = jwt.getClaim(CommonUtil.CHANNEL).asString();

        /** 验证令牌参数 **/
        if (StringUtil.isNullOrEmpty(userId)
                || StringUtil.isNullOrEmpty(roleId)
                || StringUtil.isNullOrEmpty(channel)
                || !(CommonUtil.CHANNEL_WEB.equals(channel) || CommonUtil.CHANNEL_APP.equals(channel))) {
            LogUtil.error(TokenVerifyAspect.class, LocalDateTime.now() + " 令牌参数有误");
            return ResultUtil.error(SysExpEnum.NEED_LOGIN);
        }

        /** 获取服务器缓存令牌 **/
        List<String> tokenKeyParts = Arrays.asList(CommonUtil.TOKEN_PREFIX, userId, roleId, channel);
        String tokenRedisKey = String.join(CommonUtil.UNDERLINE, tokenKeyParts);
        String tokenRedis = this.redisOperation.getStr(tokenRedisKey);

        /** 验证令牌缓存情况 **/
        if (StringUtil.isNullOrEmpty(tokenRedis)) {
            LogUtil.error(TokenVerifyAspect.class, LocalDateTime.now() + " 令牌缓存缺失");
            return ResultUtil.error(SysExpEnum.NEED_LOGIN);
        }

        /** 查看剩余有效时间 **/
        long time = this.redisOperation.getStrExpire(tokenRedisKey);
        if (time < 1) {
            LogUtil.error(TokenVerifyAspect.class, LocalDateTime.now() + " 令牌缓存失效");
            return ResultUtil.error(SysExpEnum.LOGIN_AGAIN);
        }

        /** 比对redis内令牌和传入令牌是否一致，防止劫持前一次有效令牌做操作 **/
        if (jwtConfig.isAntiHijack()) {
            if (tokenRedis.compareTo(token) != 0) {
                LogUtil.error(TokenVerifyAspect.class, LocalDateTime.now() + " 令牌比对失败");
                return ResultUtil.error(SysExpEnum.REMOTE_LOGIN);
            }
        }

        /** 令牌续权 **/
        if (jwtConfig.isEnableRenew()) {
            if (CommonUtil.CHANNEL_WEB.equals(channel)) {
                if (time < jwtConfig.getWebUpdateInterval()) {
                    this.redisOperation.expireStr(tokenRedisKey, jwtConfig.getWebLifecycle());
                }
            } else {
                if (time < jwtConfig.getAppUpdateInterval()) {
                    this.redisOperation.expireStr(tokenRedisKey, jwtConfig.getAppLifecycle());
                }
            }
        }

        /** 将登录数据写入threadlocal **/
        LoginInfoResult loginInfoResult = this.commonService.saveLoginInfo(Integer.valueOf(userId), Integer.valueOf(roleId), channel);
        if (!loginInfoResult.isChecked()) {
            LogUtil.error(TokenVerifyAspect.class, LocalDateTime.now() + " 没有登录信息缓存");
            return ResultUtil.error(SysExpEnum.NO_LOGIN_INFO);
        }

        RequestLoginContextHolder.setRequestLoginInfo(loginInfoResult.getLoginInfo());

        /** 执行目标 **/
        return joinPoint.proceed();
    }

    /**
     * 异常处理
     *
     * @param throwable
     * @return
     */
    @AfterThrowing(pointcut = "carpetTokenVerify() || exactTokenVerify()", throwing = "throwable")
    public Object AfterThrowingAspect(Throwable throwable) {
        LogUtil.error(TokenVerifyAspect.class, throwable.toString());
        return ResultUtil.error(SysExpEnum.COMMON_ERROR);
    }
}
