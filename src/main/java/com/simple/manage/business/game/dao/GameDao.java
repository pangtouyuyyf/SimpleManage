package com.simple.manage.business.game.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 游戏数据操作接口
 * Author chen
 * CreateTime 2018-08-02 10:05
 **/
@Mapper
public interface GameDao {
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
     * @param params
     * @return
     */
    int delGame(Map<String, Object> params);

    /**
     * 删除游戏
     *
     * @param gameId
     * @return
     */
    int delGameForReal(int gameId);
}
