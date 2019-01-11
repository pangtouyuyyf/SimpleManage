package com.simple.manage.system.service;

import java.util.List;
import java.util.Map;

/**
 * Description 用户组织关联服务接口
 * Author chen
 * CreateTime 2019-01-11 9:51
 **/

public interface UserOrgService {
    /**
     * 批量添加用户公司关系
     *
     * @param userOrg
     * @return
     */
    int addUserOrg(List<Map<String, Object>> userOrg);

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
     * @param userOrg
     * @return
     */
    int delUserOrgBatch(List<Map<String, Integer>> userOrg);

    /**
     * 删除用户公司关系
     *
     * @param params
     * @return
     */
    int delUserOrg(Map<String, Object> params);
}
