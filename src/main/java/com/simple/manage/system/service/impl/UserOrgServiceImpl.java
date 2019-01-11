package com.simple.manage.system.service.impl;

import com.simple.manage.system.dao.UserOrgDao;
import com.simple.manage.system.service.UserOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description 用户组织关联服务接口实现
 * Author chen
 * CreateTime 2019-01-11 9:53
 **/
@Service
public class UserOrgServiceImpl implements UserOrgService {
    @Autowired
    private UserOrgDao userOrgDao;

    /**
     * 批量添加用户组织关系
     *
     * @param userOrg
     * @return
     */
    public int addUserOrg(List<Map<String, Object>> userOrg) {
        return this.userOrgDao.addUserOrg(userOrg);
    }

    /**
     * 查询用户组织关系
     *
     * @param params
     * @return
     */
    public List<Map<String, Integer>> queryAll(Map<String, Object> params) {
        return this.userOrgDao.queryAll(params);
    }

    /**
     * 批量删除用户组织关系
     *
     * @param userOrg
     * @return
     */
    public int delUserOrgBatch(List<Map<String, Integer>> userOrg) {
        return this.userOrgDao.delUserOrgBatch(userOrg);
    }

    /**
     * 删除用户组织关系
     *
     * @param params
     * @return
     */
    public int delUserOrg(Map<String, Object> params) {
        return this.userOrgDao.delUserOrg(params);
    }

    /**
     * 查询用户组织关联
     *
     * @param params
     * @return
     */
    public List<Map<String, Integer>> queryUserOrgList(Map<String, Object> params) {
        return this.userOrgDao.queryUserOrgList(params);
    }
}
