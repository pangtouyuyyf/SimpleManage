package com.simple.manage.system.controller;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.annotation.AuthorizationAnnotation;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 菜单业务controller
 * Author chen
 * CreateTime 2018-08-07 17:16
 **/
@RestController
@RequestMapping(value = "/sys/menu")
public class MenuController extends BaseController implements TokenController {
    @Autowired
    private MenuService menuService;

    /**
     * 查询菜单
     *
     * @param id 主键
     * @return
     */
    @AuthorizationAnnotation
    @GetMapping(value = "/queryOne")
    public Result queryMenu(@RequestParam("id") Integer id) throws Exception {
        return this.success(this.menuService.queryMenu(id));
    }

    /**
     * 查询菜单列表
     *
     * @param name     名称
     * @param moduleId 模块id
     * @param page     页码
     * @param size     页数
     * @return
     */
    @AuthorizationAnnotation
    @GetMapping(value = "/queryList")
    public Result queryMenuList(@RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "moduleId", required = false) Integer moduleId,
                                @RequestParam("page") Integer page,
                                @RequestParam("size") Integer size) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("menu_name", name);
        params.put("module_id", moduleId);
        PageInfo pageInfo = this.menuService.queryMenuList(params, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(pageResult);
    }

    /**
     * 添加更新菜单
     *
     * @param id       主键
     * @param moduleId 模块id
     * @param name     名称
     * @param url      url地址
     * @param order    排序
     * @param note     备注
     * @return
     */
    @AuthorizationAnnotation
    @PostMapping(value = "/addOrUpd")
    public Result addOrUpdMenu(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam("moduleId") Integer moduleId,
                               @RequestParam("name") String name,
                               @RequestParam("url") String url,
                               @RequestParam("order") Integer order,
                               @RequestParam(value = "note", required = false) String note) throws Exception {
        Map<String, Object> menu = new HashMap<>();
        menu.put("menu_id", id);
        menu.put("module_id", moduleId);
        menu.put("menu_name", name);
        menu.put("menu_url", url.trim());
        menu.put("menu_order", order);
        menu.put("menu_note", note);
        menu.put("create_id", getLoginInfo().getUser().getId());
        menu.put("create_time", LocalDateTime.now());
        menu.put("update_id", getLoginInfo().getUser().getId());
        menu.put("update_time", LocalDateTime.now());
        this.menuService.addOrUpdMenu(menu);
        return success();
    }

    /**
     * 删除菜单
     *
     * @param id 主键
     * @return
     */
    @AuthorizationAnnotation
    @DeleteMapping(value = "/del")
    public Result delMenu(@RequestParam("id") Integer id) throws Exception {
        this.menuService.delMenu(id, getLoginInfo().getUser().getId());
        return success();
    }
}
