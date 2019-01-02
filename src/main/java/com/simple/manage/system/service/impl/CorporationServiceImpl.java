package com.simple.manage.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.dao.CorporationDao;
import com.simple.manage.system.service.CorporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 公司服务接口实现
 * Author chen
 * CreateTime 2019-01-02 10:31
 **/
@Service
public class CorporationServiceImpl implements CorporationService {
    @Autowired
    private CorporationDao corporationDao;

    /**
     * 添加或更新公司信息
     *
     * @param corp
     * @return
     */
    public int addOrUpdCorp(Map<String, Object> corp) {
        return this.corporationDao.addOrUpdCorp(corp);
    }

    /**
     * 查询公司信息
     *
     * @param corpId
     * @return
     */
    public Map<String, Object> queryCorp(int corpId) {
        return this.corporationDao.queryCorp(corpId);
    }

    /**
     * 查询公司信息列表
     *
     * @param params
     * @param page
     * @param size
     * @return
     */
    public PageInfo queryCorpList(Map<String, Object> params, int page, int size) {
        return PageHelper.startPage(page, size).doSelectPageInfo(() -> corporationDao.queryCorpList(params));
    }

    /**
     * 逻辑删除公司信息
     *
     * @param corpId
     * @param userId
     * @return
     */
    public int delCorp(int corpId, int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("corp_id", corpId);
        params.put("update_id", userId);
        params.put("update_time", LocalDateTime.now());
        params.put("is_delete", "1");
        return this.corporationDao.delCorp(params);
    }

    /**
     * 删除公司信息
     *
     * @param corpId
     * @return
     */
    public int delCorpForReal(int corpId) {
        return this.corporationDao.delCorpForReal(corpId);
    }
}
