package com.simple.manage.system.service;

import java.util.List;
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
     * @param corp
     * @return
     */
    int addOrUpdCorp(Map<String, Object> corp);

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
     * @return
     */
    List<Map<String, Object>> queryCorpList(Map<String, Object> params);

    /**
     * 逻辑删除公司信息
     *
     * @param params
     * @return
     */
    int delCorp(Map<String, Object> params);

    /**
     * 删除公司信息
     *
     * @param corpId
     * @return
     */
    int delCorpForReal(int corpId);
}
