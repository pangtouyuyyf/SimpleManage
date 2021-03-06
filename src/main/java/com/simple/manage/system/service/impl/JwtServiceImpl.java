package com.simple.manage.system.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.simple.manage.system.config.JwtConfig;
import com.simple.manage.system.redis.RedisOperation;
import com.simple.manage.system.service.JwtService;
import com.simple.manage.system.util.CommonUtil;
import com.simple.manage.system.util.LogUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
     * @param userId  用户主键
     * @param channel 客户端渠道(app/web)
     * @return
     */
    public String createJWT(String userId, String channel) {
        String result = null;
        Date now = new Date();  //当前时间
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getBase64Secret());  //秘钥算法

            result = JWT.create()
                    .withIssuer(jwtConfig.getIssuer())                  //设置发行者
                    .withClaim(CommonUtil.USER_ID, userId)              //设置参数
                    .withClaim(CommonUtil.CHANNEL, channel)             //设置参数
                    .withNotBefore(now)                                 //设置最早时间
                    .sign(algorithm);                                   //签名加密
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
    public Map<String, String> parseJWT(String token) {
        return Optional.ofNullable(token).map(
                t -> {
                    try {
                        // 判断token是否合法
                        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getBase64Secret());
                        JWTVerifier verifier = JWT.require(algorithm).withIssuer(jwtConfig.getIssuer()).build();
                        Map<String, Claim> map = verifier.verify(t).getClaims();
                        Map<String, String> resultMap = new HashMap<>();
                        map.forEach((k, v) -> resultMap.put(k, v.asString()));
                        return resultMap;
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

        Map<String, String> jwtMap = this.parseJWT(token);

        /** 验证令牌合法性 **/
        if (jwtMap == null) {
            LogUtil.error(JwtServiceImpl.class, LocalDateTime.now() + " Websocket令牌验证失败");
            return false;
        }

        /** 获取令牌中的用户、角色和登录渠道 **/
        String userId = jwtMap.get(CommonUtil.USER_ID);
        String channel = jwtMap.get(CommonUtil.CHANNEL);

        /** 验证令牌参数 **/
        if (StringUtil.isNullOrEmpty(userId)
                || StringUtil.isNullOrEmpty(channel)
                || !(CommonUtil.CHANNEL_WEB.equals(channel) || CommonUtil.CHANNEL_APP.equals(channel))) {
            LogUtil.error(JwtServiceImpl.class, LocalDateTime.now() + " Websocket令牌参数有误");
            return false;
        }

        /** 获取服务器缓存令牌 **/
        List<String> tokenKeyParts = Arrays.asList(CommonUtil.TOKEN_PREFIX, userId, channel);
        String tokenRedisKey = String.join(CommonUtil.UNDERLINE, tokenKeyParts);
        String tokenRedis = this.redisOperation.getStr(tokenRedisKey);

        /** 验证令牌缓存情况 **/
        if (StringUtil.isNullOrEmpty(tokenRedis)) {
            LogUtil.error(JwtServiceImpl.class, LocalDateTime.now() + " Websocket令牌缓存缺失");
            return false;
        }

        /** 查看剩余有效时间 **/
        long time = this.redisOperation.getStrExpire(tokenRedisKey);
        if (time < 1) {
            LogUtil.error(JwtServiceImpl.class, LocalDateTime.now() + " Websocket令牌缓存失效");
            return false;
        }

        /** 比对redis内令牌和传入令牌是否一致，防止劫持前一次有效令牌做操作 **/
        if (jwtConfig.isAntiHijack()) {
            if (tokenRedis.compareTo(token) != 0) {
                LogUtil.error(JwtServiceImpl.class, LocalDateTime.now() + " Websocket令牌比对失败");
                return false;
            }
        }

        return result;
    }
}
