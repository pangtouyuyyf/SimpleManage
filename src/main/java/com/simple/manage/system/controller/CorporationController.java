package com.simple.manage.system.controller;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.CorporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 公司信息controller
 * Author chen
 * CreateTime 2019-01-02 10:37
 **/
@RestController
@RequestMapping(value = "/sys/corp")
public class CorporationController extends BaseController implements TokenController{
    @Autowired
    private CorporationService corporationService;

    /**
     * 添加更新公司信息
     *
     * @param id       主键
     * @param name     名称
     * @param note     备注
     * @return
     */
    @PostMapping(value = "/addOrUpd")
    public Result addOrUpdCorp(@RequestParam(value = "id", required = false) Integer id,
                                 @RequestParam("name") String name,
                                 @RequestParam(value = "note", required = false) String note) throws Exception {
        Map<String, Object> corp = new HashMap<>();
        corp.put("corp_id", id);
        corp.put("corp_name", name);
        corp.put("corp_note", note);
        corp.put("create_id", getLoginInfo().getUser().getId());
        corp.put("create_time", LocalDateTime.now());
        corp.put("update_id", getLoginInfo().getUser().getId());
        corp.put("update_time", LocalDateTime.now());
        this.corporationService.addOrUpdCorp(corp);
        return success();
    }

    /**
     * 查询公司信息
     *
     * @param id 主键
     * @return
     */
    @GetMapping(value = "/queryOne")
    public Result queryCorp(@RequestParam("id") Integer id) throws Exception {
        return this.success(this.corporationService.queryCorp(id));
    }

    /**
     * 查询公司信息列表
     *
     * @param name     名称
     * @param page     页码
     * @param size     页数
     * @return
     */
    @GetMapping(value = "/queryList")
    public Result queryCorpList(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("size") Integer size) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("corp_name", name);
        PageInfo pageInfo = this.corporationService.queryCorpList(params, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(pageResult);
    }

    /**
     * 删除公司信息
     *
     * @param id 主键
     * @return
     */
    @DeleteMapping(value = "/del")
    public Result delCorp(@RequestParam("id") Integer id) throws Exception {
        this.corporationService.delCorp(id, getLoginInfo().getUser().getId());
        return success();
    }
}
