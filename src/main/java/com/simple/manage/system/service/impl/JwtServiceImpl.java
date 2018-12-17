package com.simple.manage.system.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.simple.manage.system.aspact.TokenVerifyAspect;
import com.simple.manage.system.config.JwtConfig;
import com.simple.manage.system.enums.SysExpEnum;
import com.simple.manage.system.redis.RedisOperation;
import com.simple.manage.system.service.JwtService;
import com.simple.manage.system.util.CommonUtil;
import com.simple.manage.system.util.LogUtil;
import com.simple.manage.system.util.ResultUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Description jwt接口实现
 * Author chen
 * CreateTime 2018-07-19 16:06
 **/
@Service
public class JwtServiceImpl implements JwtService {
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisOperation redisOperation;

    /**
     * 创建令牌
     *
     * @param userId  用户Id
     * @param roleId  角色id
     * @param channel 客户端渠道(app/web)
     * @return
     */
    public String createJWT(String userId, String roleId, String channel) {
        String result = null;
        Date now = new Date();  //当前时间
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getBase64Secret());  //秘钥算法

            result = JWT.create()
                    .withIssuer(jwtConfig.getIssuer())      //设置发行者
                    .withClaim(CommonUtil.USER_ID, userId)       //设置参数
                    .withClaim(CommonUtil.ROLE_ID, roleId)       //设置参数
                    .withClaim(CommonUtil.CHANNEL, channel)    //设置参数
                    .withNotBefore(now)    //设置最早时间
                    .sign(algorithm);      //签名
        } catch (Exception e) {
            LogUtil.error(JwtServiceImpl.class, e.toString());
        }
        return result;
    }

    /**
     * 解析令牌
     *
     * @param token 令牌
     * @return
     */
    public DecodedJWT parseJWT(String token) {
        return Optional.ofNullable(token).map(
                t -> {
                    try {
                        // 判断token是否合法
                        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getBase64Secret());
                        JWTVerifier verifier = JWT.require(algorithm).withIssuer(jwtConfig.getIssuer()).build();
                        return verifier.verify(t);
                    } catch (Exception e) {
                        LogUtil.error(JwtServiceImpl.class, e.toString());
                        return null;
                    }
                }
        ).orElse(null);
    }

    /**
     * 判断令牌
     *
     * @param token
     * @return
     */
    public boolean judgeJWT(String token) {
        boolean result = true;

        DecodedJWT jwt = this.parseJWT(token);

        /** 验证令牌合法性 **/
        if (jwt == null) {
            LogUtil.error(TokenVerifyAspect.class, LocalDateTime.now() + " Websocket令牌验证失败");
            return false;
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
            LogUtil.error(TokenVerifyAspect.class, LocalDateTime.now() + " Websocket令牌参数有误");
            return false;
        }

        /** 获取服务器缓存令牌 **/
        List<String> tokenKeyParts = Arrays.asList(CommonUtil.TOKEN_PREFIX, channel, userId, roleId);
        String tokenRedisKey = String.join(CommonUtil.UNDERLINE, tokenKeyParts);
        String tokenRedis = this.redisOperation.getStr(tokenRedisKey);

        /** 验证令牌缓存情况 **/
        if (StringUtil.isNullOrEmpty(tokenRedis)) {
            LogUtil.error(TokenVerifyAspect.class, LocalDateTime.now() + " Websocket令牌缓存缺失");
            return false;
        }

        /** 查看剩余有效时间 **/
        long time = this.redisOperation.getStrExpire(tokenRedisKey);
        if (time < 1) {
            LogUtil.error(TokenVerifyAspect.class, LocalDateTime.now() + " Websocket令牌缓存失效");
            return false;
        }

        /** 比对redis内令牌和传入令牌是否一致，防止劫持前一次有效令牌做操作 **/
        if (jwtConfig.isAntiHijack()) {
            if (tokenRedis.compareTo(token) != 0) {
                LogUtil.error(TokenVerifyAspect.class, LocalDateTime.now() + " Websocket令牌比对失败");
                return false;
            }
        }

        return result;
    }
}
