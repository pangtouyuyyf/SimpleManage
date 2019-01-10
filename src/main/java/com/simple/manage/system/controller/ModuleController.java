package com.simple.manage.system.controller;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 模块操作controller
 * Author chen
 * CreateTime 2018-08-02 15:10
 **/
@RestController
@RequestMapping(value = "/sys/module")
public class ModuleController extends BaseController implements TokenController {
    @Autowired
    private ModuleService moduleService;

    /**
     * 查询模块
     *
     * @param id 主键
     * @return
     */
    @GetMapping(value = "/queryOne")
    public Result queryModule(@RequestParam("id") Integer id) throws Exception {
        return this.success(this.moduleService.queryModule(id));
    }

    /**
     * 查询模块列表
     *
     * @param name 模块名称
     * @param type 类型
     * @param page 页码
     * @param size 页数
     * @return
     */
    @GetMapping(value = "/queryList")
    public Result queryModuleList(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "type", required = false) String type,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("size") Integer size) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("module_name", name);
        params.put("module_type", type);
        params.put("corp_id", getLoginInfo().getUser().getCorpId());
        PageInfo pageInfo = this.moduleService.queryModuleList(params, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(pageResult);
    }

    /**
     * 添加更新模块
     *
     * @param id    主键
     * @param name  名称
     * @param type  类型
     * @param icon  图标
     * @param order 排序
     * @param note  备注
     * @return
     */
    @PostMapping(value = "/addOrUpd")
    public Result addOrUpdModule(@RequestParam(value = "id", required = false) Integer id,
                                 @RequestParam("name") String name,
                                 @RequestParam("type") String type,
                                 @RequestParam(value = "icon", required = false) String icon,
                                 @RequestParam("order") Integer order,
                                 @RequestParam(value = "note", required = false) String note) throws Exception {
        Map<String, Object> module = new HashMap<>();
        module.put("module_id", id);
        module.put("module_name", name);
        module.put("module_type", type);
        module.put("module_icon", icon);
        module.put("module_order", order);
        module.put("module_note", note);
        module.put("create_id", getLoginInfo().getUser().getId());
        module.put("create_time", LocalDateTime.now());
        module.put("update_id", getLoginInfo().getUser().getId());
        module.put("update_time", LocalDateTime.now());
        this.moduleService.addOrUpdModule(module);
        return success();
    }

    /**
     * 删除模块
     *
     * @param id 主键
     * @return
     */
    @DeleteMapping(value = "/del")
    public Result delModule(@RequestParam("id") Integer id) throws Exception {
        this.moduleService.delModule(id, getLoginInfo().getUser().getId());
        return success();
    }

    /**
     * 根据类型查询模块字典
     *
     * @param type 类型
     * @return
     */
    @GetMapping(value = "/queryDictByType")
    public Result queryModuleDataDictByType(@RequestParam("type") String type) throws Exception {
        return success(this.moduleService.queryModuleDataDictByType(type));
    }

    /**
     * 根据类型查询模块树
     *
     * @param type 类型
     * @return
     */
    @GetMapping(value = "/queryTreeByType")
    public Result queryModuleTreeByType(@RequestParam("type") String type) throws Exception {
        return success(this.moduleService.queryModuleTreeByType(type));
    }
}
