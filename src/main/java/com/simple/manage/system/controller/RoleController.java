package com.simple.manage.system.controller;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 角色业务controller
 * Author chen
 * CreateTime 2018-08-07 17:14
 **/
@RestController
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController implements TokenController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询角色
     *
     * @param id 主键
     * @return
     */
    @GetMapping(value = "/queryOne")
    public Result queryRole(@RequestParam("id") Integer id) throws Exception {
        return this.success(this.roleService.queryRole(id));
    }

    /**
     * 查询角色列表
     *
     * @param code   编码
     * @param name   名称
     * @param page   页码
     * @param size   页数
     * @param corpId 公司编号
     * @return
     */
    @GetMapping(value = "/queryList")
    public Result queryRoleList(@RequestParam(value = "code", required = false) String code,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "corpId", required = false) Integer corpId,
                                @RequestParam("page") Integer page,
                                @RequestParam("size") Integer size) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("role_code", code);
        params.put("role_name", name);
        params.put("corp_id", corpId);
        PageInfo pageInfo = this.roleService.queryRoleList(params, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(pageResult);
    }

    /**
     * 添加更新角色
     *
     * @param id    主键
     * @param code  编码
     * @param name  名称
     * @param order 排序
     * @param note  备注
     * @param orgId 组织主键
     * @return
     */
    @PostMapping(value = "/addOrUpd")
    public Result addOrUpdRole(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam("code") String code,
                               @RequestParam("name") String name,
                               @RequestParam("order") Integer order,
                               @RequestParam(value = "note", required = false) String note,
                               @RequestParam(value = "orgId", required = false) Integer orgId) throws Exception {
        Map<String, Object> role = new HashMap<>();
        role.put("role_id", id);
        role.put("role_code", code);
        role.put("role_name", name);
        role.put("role_order", order);
        role.put("role_note", note);
        role.put("org_id", orgId);
        role.put("create_id", getLoginInfo().getUser().getId());
        role.put("create_time", LocalDateTime.now());
        role.put("update_id", getLoginInfo().getUser().getId());
        role.put("update_time", LocalDateTime.now());

        this.roleService.addOrUpdRole(role);
        return success();
    }

    /**
     * 删除角色
     *
     * @param id 主键
     * @return
     */
    @DeleteMapping(value = "/del")
    public Result delRole(@RequestParam("id") Integer id) throws Exception {
        this.roleService.delRole(id, getLoginInfo().getUser().getId());
        return success();
    }
}
