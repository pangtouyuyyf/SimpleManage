package com.simple.manage.system.service.impl;

import com.simple.manage.system.config.FtpConfig;
import com.simple.manage.system.dao.AttachmentDao;
import com.simple.manage.system.ftp.FtpOperation;
import com.simple.manage.system.service.AttachmentService;
import com.simple.manage.system.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Description 附件服务接口实现
 * Author chen
 * CreateTime 2018-12-06 10:48
 **/
@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private FtpOperation ftpOperation;

    @Autowired
    private FtpConfig ftpConfig;

    /**
     * 添加附件
     *
     * @param file
     * @param folder
     * @param userId
     * @return
     */
    public int addAttachment(MultipartFile file, String folder, int userId) {
        String fileUrl = this.upload(file, folder);

        Map<String, Object> attachment = new HashMap<>();
        attachment.put("attach_name", file.getOriginalFilename());
        attachment.put("attach_type", getFileType(file.getOriginalFilename()));
        attachment.put("attach_size", file.getSize());
        attachment.put("attach_size_show", getFileSizeDescription(file.getSize()));
        attachment.put("attach_url", fileUrl);
        attachment.put("create_id", userId);
        attachment.put("create_time", LocalDateTime.now());
        getFileType(file.getOriginalFilename());

        this.attachmentDao.addAttachment(attachment);
        return Integer.valueOf(attachment.get("attach_id").toString());
    }

    /**
     * 查询单个附件
     *
     * @param attachId
     * @return
     */
    public Map<String, Object> queryAttachment(int attachId) {
        return this.attachmentDao.queryAttachment(attachId);
    }

    /**
     * 删除附件
     *
     * @param attachId
     * @return
     */
    public int delAttachment(int attachId) {
        return this.attachmentDao.delAttachment(attachId);
    }

    /**
     * 批量删除附件
     *
     * @param attachIds
     * @return
     */
    public int delAttachments(List<Integer> attachIds) {
        return this.attachmentDao.delAttachments(attachIds);
    }

    /**
     * 修改附件展示状态
     *
     * @param param
     * @return
     */
    public int displayAttachment(Map<String, Object> param) {
        return this.attachmentDao.displayAttachment(param);
    }

    /**
     * 下载附件
     *
     * @param url
     * @param name
     * @param response
     */
    public void downloadAttachment(String url, String name, HttpServletResponse response) throws IOException {
        String source = url.substring(ftpConfig.getUrl().length() + 1);
        byte[] file = this.ftpOperation.download(source);
        response.setHeader("Content-Disposition", "attachment;fileName=" + name);
        OutputStream os = response.getOutputStream();
        os.write(file);
        os.flush();
        os.close();
    }

    /**
     * 上传文件
     *
     * @param file
     * @param folder
     * @return
     * @throws RuntimeException
     */
    private String upload(MultipartFile file, String folder) throws RuntimeException {
        String newFileName = null;

        try {
            if (file != null && !file.isEmpty()) {
                String realName = file.getOriginalFilename();
                String fileType[] = realName.split("\\.");
                newFileName = UUID.randomUUID().toString().replace("-", "");
                newFileName = folder + "/" + newFileName + "." + fileType[1];
                this.ftpOperation.upload(file.getBytes(), newFileName);
                if (ftpConfig.getUrl().endsWith("/")) {
                    newFileName = ftpConfig.getUrl() + newFileName;
                } else {
                    newFileName = ftpConfig.getUrl() + "/" + newFileName;
                }
            }
        } catch (IOException e) {
            LogUtil.error(AttachmentServiceImpl.class, "附件上传失败" + e.toString());
            throw new RuntimeException();
        }

        return newFileName;
    }

    /**
     * 获取文件大小
     *
     * @param size
     * @return
     */
    private String getFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }

    /**
     * 获取文件类型
     *
     * @param fileName
     * @return
     */
    private String getFileType(String fileName) {
        String type = null;
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
        /** 0：图片 1：视屏 2：其他(待补充) **/
        switch (extension) {
            case "JPG":
                type = "0";
                break;
            case "JPEG":
                type = "0";
                break;
            case "PNG":
                type = "0";
                break;
            case "GIF":
                type = "0";
                break;
            case "BMP":
                type = "0";
                break;
            case "AVI":
                type = "1";
                break;
            case "FLV":
                type = "1";
                break;
            case "MP4":
                type = "1";
                break;
            case "WMV":
                type = "1";
                break;
            case "MKV":
                type = "1";
                break;
            default:
                type = "2";
                break;
        }
        return type;
    }
}
