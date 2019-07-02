package com.simple.manage.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.RoleMenuService;
import com.simple.manage.system.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 角色菜单业务controller
 * Author chen
 * CreateTime 2018-09-12 14:48
 **/
@RestController
@RequestMapping(value = "/sys/roleMenu")
public class RoleMenuController extends BaseController implements TokenController {
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 查询角色菜单
     *
     * @param body 参数
     * @return
     */
    @PostMapping(value = "/queryList")
    public Result queryRoleMenuList(@RequestBody JSONObject body) throws Exception {
        int roleId = body.getInteger("roleId");
        JSONArray moduleIdArr = body.getJSONArray("moduleIds");
        List<Integer> moduleIdList = JSONArray.parseArray(JSONObject.toJSONString(moduleIdArr), Integer.class);

        Map<String, Object> param = new HashMap<>();
        param.put("roleId", roleId);
        param.put("moduleIds", moduleIdList);

        //todo

        return this.success(this.roleMenuService.queryRoleMenuList(param));
    }

    /**
     * 添加角色菜单
     *
     * @param body
     * @return
     */
    @PostMapping(value = "/add")
    public Result addRoleMenu(@RequestBody JSONObject body) throws Exception {
        int currentUserId = getLoginInfo().getUser().getId();
        return success(this.roleMenuService.saveRoleMenu(currentUserId, body));
    }

    /**
     * 获取当前用户可用菜单
     *
     * @return
     */
    @GetMapping(value = "/queryAvlList")
    public Result queryAvlRoleMenuList() throws Exception {
        List<Integer> rIdList = Arrays.asList(getLoginInfo().getUser().getRoleId());
        return this.success(this.roleMenuService.queryAvlRoleMenuList(rIdList));
    }

    /**
     * 获取角色菜单菜单操作
     *
     * @return
     */
    @GetMapping(value = "/queryOperateList")
    public Result queryRoleMenuOperate(@RequestParam("roleId") Integer roleId,
                                       @RequestParam("page") Integer page,
                                       @RequestParam("size") Integer size) throws Exception {
        PageInfo pageInfo = this.roleMenuService.queryRoleMenuOperate(roleId, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(this.pageResult);
    }

    /**
     * 添加角色菜单
     *
     * @param body
     * @return
     */
    @PostMapping(value = "/addOperate")
    public Result addRoleMenuOperate(@RequestBody JSONObject body) throws Exception {
        int currentUserId = getLoginInfo().getUser().getId();
        return success(this.roleMenuService.saveRoleMenuOperate(currentUserId, body));
    }

    /**
     * 根据当前路由匹配菜单中url获取当前角色菜单操作码
     *
     * @param url
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/code")
    public Result queryOperateByUrl(@RequestParam("url") String url) throws Exception {
        List<Integer> rIdList = Arrays.asList(getLoginInfo().getUser().getRoleId());
        Map<String, Object> params = new HashMap<>();
        params.put("url", CommonUtil.urlHandler(url));
        params.put("roleIds", rIdList);

        //TODO
        return success(this.roleMenuService.queryMenuOperateCode(params));
    }
}
