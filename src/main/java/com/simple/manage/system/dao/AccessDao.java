package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 链接数据操作接口
 * Author chen
 * CreateTime 2018-08-06 11:24
 **/
@Mapper
public interface AccessDao {
    /**
     * 检查数据是否存在
     *
     * @param userId
     * @return
     */
    int checkAccess(Integer userId);

    /**
     * 添加链接
     *
     * @param access
     * @return
     */
    int addAccess(Map<String, Object> access);

    /**
     * 更新链接
     *
     * @param access
     * @return
     */
    int updAccess(Map<String, Object> access);

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
     * @return
     */
    List<Map<String, Object>> queryAccessList(Map<String, Object> params);

    /**
     * 逻辑删除链接
     *
     * @param params
     * @return
     */
    int delAccess(Map<String, Object> params);

    /**
     * 删除链接
     *
     * @param accessId
     * @return
     */
    int delAccessForReal(int accessId);
}
