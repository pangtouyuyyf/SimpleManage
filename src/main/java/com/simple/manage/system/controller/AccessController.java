package com.simple.manage.system.controller;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 链接业务controller
 * Author chen
 * CreateTime 2018-08-07 17:15
 **/
@RestController
@RequestMapping(value = "/sys/access")
public class AccessController extends BaseController implements TokenController {
    @Autowired
    private AccessService accessService;

    /**
     * 查询链接
     *
     * @param id 主键
     * @return
     */
    @GetMapping(value = "/queryOne")
    public Result queryAccess(@RequestParam("id") Integer id) throws Exception {
        return this.success(this.accessService.queryAccess(id));
    }

    /**
     * 查询链接列表
     *
     * @param name     链接名称
     * @param moduleId 模块id
     * @param page     页码
     * @param size     页数
     * @return
     */
    @GetMapping(value = "/queryList")
    public Result queryAccessList(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "moduleId", required = false) Integer moduleId,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("size") Integer size) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("access_name", name);
        params.put("module_id", moduleId);
        params.put("corp_id", getLoginInfo().getUser().getCorpId());
        PageInfo pageInfo = this.accessService.queryAccessList(params, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(pageResult);
    }

    /**
     * 添加更新链接
     *
     * @param id       主键
     * @param moduleId 模块id
     * @param name     名称
     * @param url      链接地址
     * @param order    排序
     * @param note     备注
     * @return
     */
    @PostMapping(value = "/addOrUpd")
    public Result addOrUpdAccess(@RequestParam(value = "id", required = false) Integer id,
                                 @RequestParam("moduleId") Integer moduleId,
                                 @RequestParam("name") String name,
                                 @RequestParam("url") String url,
                                 @RequestParam("order") Integer order,
                                 @RequestParam(value = "note", required = false) String note) throws Exception {
        Map<String, Object> access = new HashMap<>();
        access.put("access_id", id);
        access.put("module_id", moduleId);
        access.put("access_name", name);
        access.put("access_url", url.trim());
        access.put("access_order", order);
        access.put("access_note", note);
        access.put("create_id", getLoginInfo().getUser().getId());
        access.put("create_time", LocalDateTime.now());
        access.put("update_id", getLoginInfo().getUser().getId());
        access.put("update_time", LocalDateTime.now());
        this.accessService.addOrUpdAccess(access);
        return success();
    }

    /**
     * 删除链接
     *
     * @param id 主键
     * @return
     */
    @DeleteMapping(value = "/del")
    public Result delAccess(@RequestParam("id") Integer id) throws Exception {
        this.accessService.delAccess(id, getLoginInfo().getUser().getId());
        return success();
    }
}
