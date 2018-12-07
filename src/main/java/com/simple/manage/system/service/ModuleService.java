package com.simple.manage.system.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Description 模块服务接口
 * Author chen
 * CreateTime 2018-08-02 11:36
 **/

public interface ModuleService {
    /**
     * 添加或更新模块
     *
     * @param module
     * @return
     */
    int addOrUpdModule(Map<String, Object> module);

    /**
     * 查询模块
     *
     * @param moduleId
     * @return
     */
    Map<String, Object> queryModule(int moduleId);

    /**
     * 查询模块列表
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    PageInfo queryModuleList(Map<String, Object> params, int page, int size);

    /**
     * 根据类型查询模块字典
     *
     * @param type
     * @return
     */
    List<Map<String, Object>> queryModuleDataDictByType(String type);

    /**
     * 根据类型查询模块树
     *
     * @param type
     * @return
     */
    List<Map<String, Object>> queryModuleTreeByType(String type);

    /**
     * 逻辑删除模块
     *
     * @param moduleId 模块号
     * @param userId   用户
     * @return
     */
    int delModule(int moduleId, int userId);

    /**
     * 删除模块
     *
     * @param moduleId
     * @return
     */
    int delModuleForReal(int moduleId);
}
