package com.simple.manage.system.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Description 附件服务接口
 * Author chen
 * CreateTime 2018-12-06 10:47
 **/

public interface AttachmentService {
    /**
     * 添加附件
     *
     * @param file
     * @param folder
     * @param userId
     * @return
     */
    int addAttachment(MultipartFile file, String folder, int userId);

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

    /**
     * 下载附件
     *
     * @param url
     * @param name
     * @param response
     */
    void downloadAttachment(String url, String name, HttpServletResponse response) throws IOException;
}
