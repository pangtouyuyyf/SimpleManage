package com.simple.manage.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.dao.ModuleDao;
import com.simple.manage.system.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description 模块服务接口实现类
 * Author chen
 * CreateTime 2018-08-02 11:37
 **/
@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;

    /**
     * 添加或更新模块
     *
     * @param module
     * @return
     */
    public int addOrUpdModule(Map<String, Object> module) {
        int result = 0;
        Integer id = module.get("module_id") == null ? null : Integer.valueOf(module.get("module_id").toString());
        int count = this.moduleDao.checkModule(id);
        if (count == 0) {
            //新增
            result = this.moduleDao.addModule(module);
        } else if (count == 1) {
            //修改
            result = this.moduleDao.updModule(module);
        } else {
        }
        return result;
    }

    /**
     * 查询模块
     *
     * @param moduleId
     * @return
     */
    public Map<String, Object> queryModule(int moduleId) {
        return this.moduleDao.queryModule(moduleId);
    }

    /**
     * 查询模块列表
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    public PageInfo queryModuleList(Map<String, Object> params, int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> moduleDao.queryModuleList(params));
    }

    /**
     * 根据类型查询模块字典
     *
     * @param type
     * @return
     */
    public List<Map<String, Object>> queryModuleDataDictByType(String type) {
        return this.moduleDao.queryModuleDataDictByType(type);
    }

    /**
     * 根据类型查询模块树
     *
     * @param type
     * @return
     */
    public List<Map<String, Object>> queryModuleTreeByType(String type) {
        List<Map<String, Object>> tree = this.moduleDao.queryModuleTreeByType(type);
        if (tree != null && !tree.isEmpty()) {
            tree = tree.stream().peek(node -> {
                node.put("isLeaf", true);
            }).collect(Collectors.toList());
        }
        return tree;
    }

    /**
     * 逻辑删除模块
     *
     * @param moduleId 模块号
     * @param userId   用户
     * @return
     */
    public int delModule(int moduleId, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("module_id", moduleId);
        params.put("update_id", userId);
        params.put("update_time", LocalDateTime.now());
        params.put("is_delete", "1");

        return this.moduleDao.delModule(params);
    }

    /**
     * 删除模块
     *
     * @param moduleId
     * @return
     */
    public int delModuleForReal(int moduleId) {
        return this.moduleDao.delModuleForReal(moduleId);
    }
}
