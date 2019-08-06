package com.simple.manage.business.game.controller;

import com.simple.manage.business.game.service.GameService;
import com.simple.manage.system.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 游戏controller
 * @Author CMM
 * @CreateTime 2019/8/6 20:38
 **/
@RestController
@RequestMapping(value = "/biz/game")
public class GameController extends BaseController {
    @Autowired
    private GameService gameService;


}
