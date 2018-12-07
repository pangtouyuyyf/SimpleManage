package com.simple.manage.system.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Description 附件操作接口
 * Author chen
 * CreateTime 2018-12-06 10:36
 **/
@Mapper
public interface AttachmentDao {
    /**
     * 添加附件
     *
     * @param attachment
     * @return
     */
    int addAttachment(Map<String, Object> attachment);

    /**
     * 查询单个附件
     *
     * @param attachId
     * @return
     */
    Map<String, Object> queryAttachment(int attachId);

    /**
     * 删除附件
     *
     * @param attachId
     * @return
     */
    int delAttachment(int attachId);

    /**
     * 批量删除附件
     *
     * @param attachIds
     * @return
     */
    int delAttachments(List<Integer> attachIds);

    /**
     * 修改附件展示状态
     *
     * @param param
     * @return
     */
    int displayAttachment(Map<String, Object> param);
}
