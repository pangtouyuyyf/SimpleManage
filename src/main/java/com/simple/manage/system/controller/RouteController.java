package com.simple.manage.system.controller;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 路由业务controller
 * Author chen
 * CreateTime 2018-09-06 17:51
 **/
@RestController
@RequestMapping(value = "/sys/route")
public class RouteController extends BaseController implements TokenController {
    @Autowired
    private RouteService routeService;

    /**
     * 查询路由
     *
     * @param id 主键
     * @return
     */
    @GetMapping(value = "/queryOne")
    public Result queryRoute(@RequestParam("id") Integer id) throws Exception {
        return this.success(this.routeService.queryRoute(id));
    }

    /**
     * 查询路由列表
     *
     * @param name     名称
     * @param moduleId 模块id
     * @param page     页码
     * @param size     页数
     * @return
     */
    @GetMapping(value = "/queryList")
    public Result queryRouteList(@RequestParam(value = "name", required = false) String name,
                                 @RequestParam(value = "moduleId", required = false) Integer moduleId,
                                 @RequestParam("page") Integer page,
                                 @RequestParam("size") Integer size) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("route_name", name);
        params.put("module_id", moduleId);
        PageInfo pageInfo = this.routeService.queryRouteList(params, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(pageResult);
    }

    /**
     * 添加更新路由
     *
     * @param id       主键
     * @param moduleId 模块id
     * @param name     名称
     * @param url      url地址
     * @param order    排序
     * @param note     备注
     * @return
     */
    @PostMapping(value = "/addOrUpd")
    public Result addOrUpdRoute(@RequestParam(value = "id", required = false) Integer id,
                                @RequestParam("moduleId") Integer moduleId,
                                @RequestParam("name") String name,
                                @RequestParam("url") String url,
                                @RequestParam("order") Integer order,
                                @RequestParam(value = "note", required = false) String note) throws Exception {
        Map<String, Object> route = new HashMap<>();
        route.put("route_id", id);
        route.put("module_id", moduleId);
        route.put("route_name", name);
        route.put("route_url", url.trim());
        route.put("route_order", order);
        route.put("route_note", note);
        route.put("create_id", getLoginInfo().getUser().getId());
        route.put("create_time", LocalDateTime.now());
        route.put("update_id", getLoginInfo().getUser().getId());
        route.put("update_time", LocalDateTime.now());
        this.routeService.addOrUpdRoute(route);
        return success();
    }

    /**
     * 删除路由
     *
     * @param id 主键
     * @return
     */
    @DeleteMapping(value = "/del")
    public Result delRoute(@RequestParam("id") Integer id) throws Exception {
        this.routeService.delRoute(id, getLoginInfo().getUser().getId());
        return success();
    }
}
