package com.simple.manage.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.manage.system.annotation.AuthorizationAnnotation;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.RoleAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 角色请求业务controller
 * Author chen
 * CreateTime 2018-09-12 15:45
 **/
@RestController
@RequestMapping(value = "/sys/roleAccess")
public class RoleAccessController extends BaseController implements TokenController {
    @Autowired
    private RoleAccessService roleAccessService;

    /**
     * 查询请求菜单
     *
     * @param body 参数
     * @return
     */
    @AuthorizationAnnotation
    @PostMapping(value = "/queryList")
    public Result queryRoleAccessList(@RequestBody JSONObject body) throws Exception {
        int roleId = body.getInteger("roleId");
        JSONArray moduleIdArr = body.getJSONArray("moduleIds");
        List<Integer> moduleIdList = JSONArray.parseArray(JSONObject.toJSONString(moduleIdArr), Integer.class);

        Map<String, Object> param = new HashMap<>();
        param.put("roleId", roleId);
        param.put("moduleIds", moduleIdList);

        return this.success(this.roleAccessService.queryRoleAccessList(param));
    }

    /**
     * 添加角色请求
     *
     * @param body
     * @return
     */
    @AuthorizationAnnotation
    @PostMapping(value = "/add")
    public Result addRoleAccess(@RequestBody JSONObject body) throws Exception {
        int currentUserId = getLoginInfo().getUser().getId();
        return success(this.roleAccessService.saveRoleAccess(currentUserId, body));
    }
}
