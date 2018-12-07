package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 数据字典数据操作接口
 * Author chen
 * CreateTime 2018-07-27 17:40
 **/
@Mapper
public interface DataDictDao {
    /**
     * 添加或更新数据字典主表信息
     *
     * @param dataDict
     * @return
     */
    int addOrUpdDataDict(Map<String, Object> dataDict);

    /**
     * 数据字典主表查询
     *
     * @param dictId
     * @return
     */
    Map<String, Object> queryDataDict(int dictId);

    /**
     * 数据字典主表查询
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryDataDictList(Map<String, Object> params);

    /**
     * 查询是否有同名数据字典
     *
     * @param name
     * @return
     */
    int querySameDataDict(String name);

    /**
     * 逻辑删除数据字典主表信息
     *
     * @param params
     * @return
     */
    int delDataDict(Map<String, Object> params);

    /**
     * 删除数据字典主表信息
     *
     * @param dictId
     * @return
     */
    int delDataDictForReal(int dictId);

    /**
     * 添加或更新数据字典子表信息
     *
     * @param dataDictLin
     * @return
     */
    int addOrUpdDataDictLin(Map<String, Object> dataDictLin);

    /**
     * 查询数据字典子表信息列表
     *
     * @param dictId
     * @return
     */
    List<Map<String, Object>> queryDataDictLinList(int dictId);

    /**
     * 查询是否有同值数据字典明细
     *
     * @param params
     * @return
     */
    int querySameDataDictLin(Map<String, Object> params);

    /**
     * 逻辑删除数据字典子表信息
     *
     * @param params
     * @return
     */
    int delDataDictLin(Map<String, Object> params);

    /**
     * 删除数据字典子表信息
     *
     * @param params
     * @return
     */
    int delDataDictLinForReal(Map<String, Object> params);

    /**
     * 根据主表数据字典名查询子表信息
     *
     * @param dictName
     * @return
     */
    List<Map<String, Object>> queryDataDictLinByName(String dictName);
}
