package com.simple.manage.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simple.manage.system.annotation.AuthorizationAnnotation;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description 用户角色业务controller
 * Author chen
 * CreateTime 2018-09-11 11:25
 **/
@RestController
@RequestMapping(value = "/sys/userRole")
public class UserRoleController extends BaseController implements TokenController {
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 查询用户角色
     *
     * @param userId 用户主键
     * @return
     */
    @AuthorizationAnnotation
    @GetMapping(value = "/queryList")
    public Result queryUserRoleList(@RequestParam("userId") Integer userId) throws Exception {
        return this.success(this.userRoleService.queryUserRoleList(userId));
    }

    /**
     * 添加用户角色
     *
     * @param body
     * @return
     */
    @AuthorizationAnnotation
    @PostMapping(value = "/add")
    public Result addUserRole(@RequestBody JSONObject body) throws Exception {
        int currentUserId = getLoginInfo().getUser().getId();
        return success(this.userRoleService.saveUserRole(currentUserId, body));
    }
}
