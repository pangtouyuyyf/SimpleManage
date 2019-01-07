package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 角色请求访问权限数据操作接口
 * Author chen
 * CreateTime 2018-08-06 14:09
 **/
@Mapper
public interface RoleAccessDao {
    /**
     * 批量添加角色请求关系
     *
     * @param roleAccess
     * @return
     */
    int addRoleAccess(List<Map<String, Object>> roleAccess);

    /**
     * 查询角色请求关系
     *
     * @param params
     * @return
     */
    List<Map<String, Integer>> queryAll(Map<String, Object> params);

    /**
     * 批量删除角色请求关系
     *
     * @param roleAccess
     * @return
     */
    int delRoleAccessBatch(List<Map<String, Integer>> roleAccess);

    /**
     * 删除角色请求关系
     *
     * @param params
     * @return
     */
    int delRoleAccess(Map<String, Object> params);

    /**
     * 查询角色请求关系
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> queryRoleAccessList(Map<String, Object> params);

    /**
     * 统计角色可使用某access数量
     *
     * @param params
     * @return
     */
    int countRoleAccess(Map<String, Object> params);

    /**
     * 根据角色主键查询所有
     *
     * @param roleId
     * @return
     */
    List<Map<String, Object>> queryAllByRoleId(int roleId);
}
