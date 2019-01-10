package com.simple.manage.system.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Description 公司服务接口
 * Author chen
 * CreateTime 2019-01-02 10:31
 **/

public interface CorporationService {
    /**
     * 添加或更新公司信息
     *
     * @param id
     * @param name
     * @param code
     * @param note
     * @param userId
     * @return
     */
    int addOrUpdCorp(Integer id, String name, String code, String note, int userId);

    /**
     * 查询公司信息
     *
     * @param corpId
     * @return
     */
    Map<String, Object> queryCorp(int corpId);

    /**
     * 查询公司信息列表
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    PageInfo queryCorpList(Map<String, Object> params, int page, int size);

    /**
     * 逻辑删除公司信息
     *
     * @param corpId
     * @param userId
     * @return
     */
    int delCorp(int corpId, int userId);

    /**
     * 删除公司信息
     *
     * @param corpId
     * @return
     */
    int delCorpForReal(int corpId);
}
