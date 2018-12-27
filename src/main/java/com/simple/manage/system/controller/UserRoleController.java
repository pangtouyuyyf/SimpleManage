package com.simple.manage.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param body 参数
     * @return
     */
    @PostMapping(value = "/queryList")
    public Result queryUserRoleList(@RequestBody JSONObject body) throws Exception {
        int userId = body.getInteger("userId");
        JSONArray orgIdArr = body.getJSONArray("orgIds");
        List<Integer> orgIdList = JSONArray.parseArray(JSONObject.toJSONString(orgIdArr), Integer.class);

        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("orgIds", orgIdList);

        return this.success(this.userRoleService.queryUserRoleList(param));
    }

    /**
     * 添加用户角色
     *
     * @param body
     * @return
     */
    @PostMapping(value = "/add")
    public Result addUserRole(@RequestBody JSONObject body) throws Exception {
        int currentUserId = getLoginInfo().getUser().getId();
        return success(this.userRoleService.saveUserRole(currentUserId, body));
    }
}
