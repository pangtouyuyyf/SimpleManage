package com.simple.manage.business.game.service.impl;

import com.simple.manage.business.game.dao.GameDao;
import com.simple.manage.business.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 游戏接口实现
 * @Author CMM
 * @CreateTime 2019/8/6 20:36
 **/
@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameDao gameDao;

    /**
     * 添加游戏
     *
     * @param game
     * @return
     */
    public int addGame(Map<String, Object> game) {
        return gameDao.addGame(game);
    }

    /**
     * 更新游戏
     *
     * @param game
     * @return
     */
    public int updGame(Map<String, Object> game) {
        return gameDao.updGame(game);
    }

    /**
     * 查询游戏
     *
     * @param gameId
     * @return
     */
    public Map<String, Object> queryGame(int gameId) {
        return gameDao.queryGame(gameId);
    }

    /**
     * 查询游戏列表
     *
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryGameList(Map<String, Object> params) {
        return gameDao.queryGameList(params);
    }

    /**
     * 逻辑删除游戏
     *
     * @param gameId
     * @param userId
     * @return
     */
    public int delGame(int gameId, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("game_id", gameId);
        params.put("update_id", userId);
        params.put("update_time", LocalDateTime.now());

        return this.gameDao.delGame(params);
    }

    /**
     * 删除游戏
     *
     * @param gameId
     * @return
     */
    public int delGameForReal(int gameId) {
        return gameDao.delGameForReal(gameId);
    }
}
