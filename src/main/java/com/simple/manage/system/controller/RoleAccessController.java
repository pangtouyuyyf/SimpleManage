package com.simple.manage.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.RoleAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 查询请求列表
     *
     * @param body 参数
     * @return
     */
    @PostMapping(value = "/queryList")
    public Result queryRoleAccessList(@RequestBody JSONObject body) throws Exception {
        int roleId = body.getInteger("roleId");
        JSONArray moduleIdArr = body.getJSONArray("moduleIds");
        List<Integer> moduleIdList = JSONArray.parseArray(JSONObject.toJSONString(moduleIdArr), Integer.class);

        Map<String, Object> param = new HashMap<>();
        param.put("roleId", roleId);
        param.put("moduleIds", moduleIdList);

        //todo
        return this.success(this.roleAccessService.queryRoleAccessList(param));
    }

    /**
     * 添加角色请求
     *
     * @param body
     * @return
     */
    @PostMapping(value = "/add")
    public Result addRoleAccess(@RequestBody JSONObject body) throws Exception {
        int currentUserId = getLoginInfo().getUser().getId();
        //todo
        return success(this.roleAccessService.saveRoleAccess(currentUserId, body));
    }
}
