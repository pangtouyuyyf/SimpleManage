package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 公司数据操作接口
 * Author chen
 * CreateTime 2019-01-02 10:00
 **/
@Mapper
public interface CorporationDao {
    /**
     * 检查数据是否存在
     *
     * @param corpId
     * @return
     */
    int checkCorp(Integer corpId);

    /**
     * 添加公司信息
     *
     * @param corp
     * @return
     */
    int addCorp(Map<String, Object> corp);

    /**
     * 更新公司信息
     *
     * @param corp
     * @return
     */
    int updCorp(Map<String, Object> corp);

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


    /**
     * 根据登录用户查询公司列表
     *
     * @param loginName
     * @return
     */
    List<Map<String, Object>> queryCorpListByLoginName(String loginName);
}
