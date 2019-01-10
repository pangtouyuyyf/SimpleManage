package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 用户公司数据操作
 * Author chen
 * CreateTime 2019-01-10 9:10
 **/
@Mapper
public interface UserCorpDao {
    /**
     * 批量添加用户公司关系
     *
     * @param userCorp
     * @return
     */
    int addUserCorp(List<Map<String, Object>> userCorp);

    /**
     * 查询用户公司关系
     *
     * @param params
     * @return
     */
    List<Map<String, Integer>> queryAll(Map<String, Object> params);

    /**
     * 批量删除用户公司关系
     *
     * @param roleAccess
     * @return
     */
    int delUserCorpBatch(List<Map<String, Integer>> roleAccess);

    /**
     * 删除用户公司关系
     *
     * @param params
     * @return
     */
    int delUserCorp(Map<String, Object> params);
}
