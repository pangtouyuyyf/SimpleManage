package com.simple.manage.system.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Description 后台链接操作服务接口
 * Author chen
 * CreateTime 2018-09-06 17:10
 **/

public interface AccessService {
    /**
     * 添加或更新链接
     *
     * @param access
     * @return
     */
    int addOrUpdAccess(Map<String, Object> access);

    /**
     * 查询链接
     *
     * @param accessId
     * @return
     */
    Map<String, Object> queryAccess(int accessId);

    /**
     * 查询链接
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    PageInfo queryAccessList(Map<String, Object> params, int page, int size);

    /**
     * 逻辑删除链接
     *
     * @param accessId
     * @param userId
     * @return
     */
    int delAccess(int accessId, int userId);

    /**
     * 删除链接
     *
     * @param accessId
     * @return
     */
    int delAccessForReal(int accessId);
}
