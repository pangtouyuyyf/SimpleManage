package com.simple.manage.system.controller;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.annotation.AuthorizationAnnotation;
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
    @AuthorizationAnnotation
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
    @AuthorizationAnnotation
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
    @AuthorizationAnnotation
    @PostMapping(value = "/addOrUpd")
    public Result addOrUpdRoute(@RequestParam(value = "id", required = false) Integer id,
                                @RequestParam("moduleId") Integer moduleId,
                                @RequestParam("name") String name,
                                @RequestParam("url") String url,
                                @RequestParam("order") Integer order,
                                @RequestParam(value = "note", required = false) String note) throws Exception {
        Map<String, Object> Route = new HashMap<>();
        Route.put("route_id", id);
        Route.put("module_id", moduleId);
        Route.put("route_name", name);
        Route.put("route_url", url);
        Route.put("route_order", order);
        Route.put("route_note", note);
        Route.put("create_id", getLoginInfo().getUser().getId());
        Route.put("create_time", LocalDateTime.now());
        Route.put("update_id", getLoginInfo().getUser().getId());
        Route.put("update_time", LocalDateTime.now());
        this.routeService.addOrUpdRoute(Route);
        return success();
    }

    /**
     * 删除路由
     *
     * @param id 主键
     * @return
     */
    @AuthorizationAnnotation
    @DeleteMapping(value = "/del")
    public Result delRoute(@RequestParam("id") Integer id) throws Exception {
        this.routeService.delRoute(id, getLoginInfo().getUser().getId());
        return success();
    }
}
