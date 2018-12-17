package com.simple.manage.system.service;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Description jwt接口
 * Author chen
 * CreateTime 2018-07-19 16:03
 **/

public interface JwtService {
    /**
     * 创建令牌
     *
     * @param userId
     * @param roleId
     * @param type
     * @return
     */
    String createJWT(String userId, String roleId, String type);

    /**
     * 解析令牌
     *
     * @param token
     * @return
     */
    DecodedJWT parseJWT(String token);

    /**
     * 判断令牌
     *
     * @param token
     * @return
     */
    boolean judgeJWT(String token);
}
