package com.simple.manage.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.dao.DataDictDao;
import com.simple.manage.system.service.DataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 数据字典业务接口实现
 * Author chen
 * CreateTime 2018-07-27 17:39
 **/
@Service
public class DataDictServiceImpl implements DataDictService {
    @Autowired
    private DataDictDao dataDictDao;

    /**
     * 添加或更新数据字典主表信息
     *
     * @param dataDict
     * @return
     */
    public int addOrUpdDataDict(Map<String, Object> dataDict) {
        int result = 0;
        Integer id = dataDict.get("dict_id") == null ? null : Integer.valueOf(dataDict.get("dict_id").toString());
        int count = this.dataDictDao.checkDataDict(id);
        if (count == 0) {
            //新增
            result = this.dataDictDao.addDataDict(dataDict);
        } else if (count == 1) {
            //修改
            result = this.dataDictDao.updDataDict(dataDict);
        } else {
        }
        return result;
    }

    /**
     * 数据字典主表查询
     *
     * @param dictId
     * @return
     */
    public Map<String, Object> queryDataDict(int dictId) {
        return this.dataDictDao.queryDataDict(dictId);
    }

    /**
     * 数据字典主表分页查询
     *
     * @param params 参数
     * @param page   页码
     * @param size   页数
     * @return
     */
    public PageInfo queryDataDictList(Map<String, Object> params, int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> dataDictDao.queryDataDictList(params));

    }

    /**
     * 查询是否有同名数据字典
     *
     * @param name
     * @return
     */
    public int querySameDataDict(String name) {
        return this.dataDictDao.querySameDataDict(name);
    }

    /**
     * 逻辑删除数据字典主表信息(同时删除子表)
     *
     * @param dictId
     * @return
     */
    @Transactional
    public int delDataDict(int dictId, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("dict_id", dictId);
        params.put("update_id", userId);
        params.put("update_time", LocalDateTime.now());
        params.put("is_delete", "1");

        int result = this.dataDictDao.delDataDict(params);
        result = result + this.dataDictDao.delDataDictLin(params);
        return result;
    }

    /**
     * 删除数据字典主表信息(同时删除子表)
     *
     * @param dictId
     * @return
     */
    @Transactional
    public int delDataDictForReal(int dictId) {
        int result = this.dataDictDao.delDataDictForReal(dictId);
        Map<String, Object> params = new HashMap<>();
        params.put("dict_id", dictId);
        result = result + this.dataDictDao.delDataDictLinForReal(params);
        return result;
    }

    /**
     * 添加或更新数据字典子表信息
     *
     * @param dataDictLin
     * @return
     */
    public int addOrUpdDataDictLin(Map<String, Object> dataDictLin) {
        return this.dataDictDao.addOrUpdDataDictLin(dataDictLin);
    }

    /**
     * 查询数据字典子表信息
     *
     * @param dictId 主表主键
     * @param page   页码
     * @param size   页数
     */
    public PageInfo queryDataDictLinList(int dictId, int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> dataDictDao.queryDataDictLinList(dictId));
    }

    /**
     * 查询是否有同值数据字典明细
     *
     * @param params
     * @return
     */
    public int querySameDataDictLin(Map<String, Object> params) {
        return this.dataDictDao.querySameDataDictLin(params);
    }

    /**
     * 逻辑删除数据字典子表信息
     *
     * @param linId
     * @param userId
     * @return
     */
    public int delDataDictLin(int linId, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("lin_id", linId);
        params.put("update_id", userId);
        params.put("update_time", LocalDateTime.now());
        params.put("is_delete", "1");

        return this.dataDictDao.delDataDictLin(params);
    }

    /**
     * 删除数据字典子表信息
     *
     * @param params
     * @return
     */
    public int delDataDictLinForReal(Map<String, Object> params) {
        return this.delDataDictLinForReal(params);
    }

    /**
     * 根据主表数据字典名查询子表信息
     *
     * @param dictName
     * @return
     */
    public List<Map<String, Object>> queryDataDictLinByName(String dictName) {
        return this.dataDictDao.queryDataDictLinByName(dictName);
    }
}
