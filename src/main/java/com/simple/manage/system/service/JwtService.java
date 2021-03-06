package com.simple.manage.system.service;

import java.util.Map;

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
     * @param type
     * @return
     */
    String createJWT(String userId, String type);

    /**
     * 解析令牌
     *
     * @param token
     * @return
     */
    Map<String, String> parseJWT(String token);

    /**
     * 判断令牌
     *
     * @param token
     * @return
     */
    boolean judgeJWT(String token);
}
