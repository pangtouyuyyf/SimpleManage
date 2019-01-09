package com.simple.manage.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.RoleRouteService;
import com.simple.manage.system.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 角色路由业务controller
 * Author chen
 * CreateTime 2018-09-12 15:26
 **/
@RestController
@RequestMapping(value = "/sys/roleRoute")
public class RoleRouteController extends BaseController implements TokenController {
    @Autowired
    private RoleRouteService roleRouteService;

    /**
     * 查询角色菜单
     *
     * @param body 参数
     * @return
     */
    @PostMapping(value = "/queryList")
    public Result queryRoleRouteList(@RequestBody JSONObject body) throws Exception {
        int roleId = body.getInteger("roleId");
        JSONArray moduleIdArr = body.getJSONArray("moduleIds");
        List<Integer> moduleIdList = JSONArray.parseArray(JSONObject.toJSONString(moduleIdArr), Integer.class);

        Map<String, Object> param = new HashMap<>();
        param.put("roleId", roleId);
        param.put("moduleIds", moduleIdList);

        return this.success(this.roleRouteService.queryRoleRouteList(param));
    }

    /**
     * 添加角色菜单
     *
     * @param body
     * @return
     */
    @PostMapping(value = "/add")
    public Result addRoleRoute(@RequestBody JSONObject body) throws Exception {
        int currentUserId = getLoginInfo().getUser().getId();
        return success(this.roleRouteService.saveRoleRoute(currentUserId, body));
    }

    /**
     * 检验当前用户是否有权限直接访问url
     *
     * @param url
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/check")
    public Result checkRoleRoute(@RequestParam("url") String url) throws Exception {
        Result result = success();
        Map<String, Object> param = new HashMap<>();
        param.put("roleIds", getLoginInfo().getRolelist());
        param.put("corp_id", getLoginInfo().getCorpId());
        param.put("url", CommonUtil.urlHandler(url));
        int count = this.roleRouteService.countRoleRoute(param);
        if (count == 0) {
            result = fail("没有权限访问");
        }
        return result;
    }
}
