package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 模块数据操作接口
 * Author chen
 * CreateTime 2018-08-02 11:13
 **/
@Mapper
public interface ModuleDao {
    /**
     * 检查数据是否存在
     *
     * @param moduleId
     * @return
     */
    int checkModule(Integer moduleId);

    /**
     * 添加模块
     *
     * @param module
     * @return
     */
    int addModule(Map<String, Object> module);

    /**
     * 更新模块
     *
     * @param module
     * @return
     */
    int updModule(Map<String, Object> module);

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
     * @return
     */
    List<Map<String, Object>> queryModuleList(Map<String, Object> params);

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
     * @param params
     * @return
     */
    int delModule(Map<String, Object> params);

    /**
     * 删除模块
     *
     * @param moduleId
     * @return
     */
    int delModuleForReal(int moduleId);

    /**
     * 查询角色可用菜单模块
     *
     * @param roleId
     * @return
     */
    List<Map<String, Object>> queryAvlMenuModuleList(int roleId);
}
