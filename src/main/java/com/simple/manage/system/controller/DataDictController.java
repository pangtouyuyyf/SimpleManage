package com.simple.manage.system.controller;

import com.github.pagehelper.PageInfo;
import com.simple.manage.system.annotation.TokenAnnotation;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.service.DataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 数据字典controller
 * Author chen
 * CreateTime 2018-07-27 15:55
 **/
@RestController
@RequestMapping(value = "/sys/datadict")
public class DataDictController extends BaseController {
    @Autowired
    private DataDictService dataDictService;

    /**
     * 根据主表数据字典名查询子表信息
     *
     * @param name 数据字典名
     * @return
     */
    @GetMapping(value = "/queryDataDictLinByName")
    public Result<Object> queryDataDictLinByName(@RequestParam("name") String name) throws Exception {
        List<Map<String, Object>> dataDictLins = this.dataDictService.queryDataDictLinByName(name);
        return success(dataDictLins);
    }

    /**
     * 查询数组字典列表
     *
     * @param name 名称
     * @param note 备注
     * @param page 页码
     * @param size 页数
     * @return
     */
    @TokenAnnotation
    @GetMapping(value = "/queryMainList")
    public Result queryModuleList(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "note", required = false) String note,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("size") Integer size) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("dict_name", name);
        params.put("dict_note", note);
        PageInfo pageInfo = this.dataDictService.queryDataDictList(params, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(pageResult);
    }

    /**
     * 添加数据字典主表信息
     *
     * @param id    主键
     * @param name  名称
     * @param order 顺序
     * @param note  备注
     * @return
     */
    @TokenAnnotation
    @PostMapping(value = "/addOrUpdDataDict")
    public Result<Object> addDataDict(@RequestParam(value = "id", required = false) Integer id,
                                      @RequestParam("name") String name,
                                      @RequestParam("order") Integer order,
                                      @RequestParam("note") String note) throws Exception {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> dataDict = new HashMap<>();
        params.put("dict_name", name);

        if (id == null) {
            //新增
            int size = this.dataDictService.querySameDataDict(name);
            if (size > 0) {
                return fail("该名称的数据字典已存在!");
            }
        }

        dataDict.put("dict_id", id);
        dataDict.put("dict_name", name);
        dataDict.put("dict_note", note);
        dataDict.put("dict_order", order);
        dataDict.put("create_id", getLoginInfo().getUser().getId());
        dataDict.put("create_time", LocalDateTime.now());
        dataDict.put("update_id", getLoginInfo().getUser().getId());
        dataDict.put("update_time", LocalDateTime.now());
        this.dataDictService.addOrUpdDataDict(dataDict);
        return success();

    }

    /**
     * 数据字典主表查询
     *
     * @param id 主键
     * @return
     */
    @TokenAnnotation
    @GetMapping(value = "/queryOne")
    public Result<Object> queryDataDict(@RequestParam("id") Integer id) throws Exception {
        return success(this.dataDictService.queryDataDict(id));
    }

    /**
     * 逻辑删除数据字典主表信息
     *
     * @param id 主键
     * @return
     */
    @TokenAnnotation
    @DeleteMapping(value = "/delMain")
    public Result<Object> deleteDataDict(@RequestParam("id") Integer id) throws Exception {
        this.dataDictService.delDataDict(id, getLoginInfo().getUser().getId());
        return success();
    }

    /**
     * 添加或更新数据字典子表信息
     *
     * @param id     主键
     * @param dictId 主表id
     * @param key    key
     * @param value  value
     * @param order  排序
     * @return
     */
    @TokenAnnotation
    @PostMapping(value = "/addOrUpdDataDictLin")
    public Result<Object> addDataDictLin(@RequestParam(value = "id", required = false) Integer id,
                                         @RequestParam("dictId") Integer dictId,
                                         @RequestParam("key") String key,
                                         @RequestParam("value") String value,
                                         @RequestParam("order") Integer order) throws Exception {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> dataDictLin = new HashMap<>();

        params.put("dict_id", dictId);
        params.put("lin_key", key);

        if (id == null) {
            //新增
            int count = this.dataDictService.querySameDataDictLin(params);
            if (count > 0) {
                return fail("该数据字典明细已存在!");
            }
        }

        dataDictLin.put("lin_id", id);
        dataDictLin.put("dict_id", dictId);
        dataDictLin.put("lin_key", key);
        dataDictLin.put("lin_value", value);
        dataDictLin.put("lin_order", order);
        dataDictLin.put("create_id", getLoginInfo().getUser().getId());
        dataDictLin.put("create_time", LocalDateTime.now());
        dataDictLin.put("update_id", getLoginInfo().getUser().getId());
        dataDictLin.put("update_time", LocalDateTime.now());
        this.dataDictService.addOrUpdDataDictLin(dataDictLin);

        return success();
    }

    /**
     * 查询数据字典子表信息列表
     *
     * @param dictId 主表主键
     * @param page   页码
     * @param size   页数
     * @return
     */
    @TokenAnnotation
    @GetMapping(value = "/queryLinList")
    public Result<Object> queryDataDictLinList(@RequestParam("dictId") Integer dictId,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("size") Integer size) throws Exception {
        PageInfo pageInfo = this.dataDictService.queryDataDictLinList(dictId, page, size);
        this.pageResult.setList(pageInfo.getList());
        this.pageResult.setTotal(pageInfo.getTotal());
        return this.success(pageResult);

    }

    /**
     * 逻辑删除数据字典子表信息
     *
     * @param linId 主键
     * @return
     */
    @TokenAnnotation
    @DeleteMapping(value = "/delLin")
    public Result<Object> deleteDataDictLin(@RequestParam("linId") Integer linId) throws Exception {
        this.dataDictService.delDataDictLin(linId, getLoginInfo().getUser().getId());
        return success();
    }
}
