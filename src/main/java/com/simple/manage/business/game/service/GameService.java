package com.simple.manage.business.game.service;

import java.util.List;
import java.util.Map;

/**
 * @Description 游戏接口
 * @Author CMM
 * @CreateTime 2019/8/6 20:31
 **/
public interface GameService {
    /**
     * 添加游戏
     *
     * @param game
     * @return
     */
    int addGame(Map<String, Object> game);

    /**
     * 更新游戏
     *
     * @param game
     * @return
     */
    int updGame(Map<String, Object> game);

    /**
     * 查询游戏
     *
     * @param gameId
     * @return
     */
    Map<String, Object> queryGame(int gameId);

    /**
     * 查询游戏列表
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryGameList(Map<String, Object> params);

    /**
     * 逻辑删除游戏
     *
     * @param gameId
     * @param userId
     * @return
     */
    int delGame(int gameId, int userId);

    /**
     * 删除游戏
     *
     * @param gameId
     * @return
     */
    int delGameForReal(int gameId);
}
