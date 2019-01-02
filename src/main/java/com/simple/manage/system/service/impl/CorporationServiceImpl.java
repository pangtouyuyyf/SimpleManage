package com.simple.manage.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simple.manage.system.dao.CorporationDao;
import com.simple.manage.system.service.CorporationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
     * @param params
     * @return
     */
    public int delCorp(Map<String, Object> params) {
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
