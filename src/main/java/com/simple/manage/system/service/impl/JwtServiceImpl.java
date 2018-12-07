package com.simple.manage.system.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.simple.manage.system.config.JwtConfig;
import com.simple.manage.system.service.JwtService;
import com.simple.manage.system.util.CommonUtil;
import com.simple.manage.system.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
}
