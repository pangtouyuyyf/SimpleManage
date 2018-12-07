package com.simple.manage.system.ftp;

import com.simple.manage.system.config.FtpConfig;
import com.simple.manage.system.util.LogUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Description ftp工具类
 * Author chen
 * CreateTime 2018-07-26 17:18
 **/
@Component
public class FtpOperation {
    private static final boolean BINARY_MODE = true;

    private static final boolean PASSIVE_MODE = true;

    @Autowired
    private FtpConfig ftpConfig;

    /**
     * 初始化FTPClient
     *
     * @param host     ip
     * @param port     端口
     * @param userName 用户名
     * @param pwd      密码
     * @throws IOException
     */
    private FTPClient initFtpClient(String host, int port, String userName, String pwd) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host, port);

        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            ftpClient.disconnect();
            LogUtil.error(FtpOperation.class, LocalDateTime.now() + " ftp 连接失败");
        }

        if (!ftpClient.login(userName, pwd)) {
            LogUtil.error(FtpOperation.class, LocalDateTime.now() + " ftp 登录失败");
        }

        if (BINARY_MODE) {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        }
        if (PASSIVE_MODE) {
            ftpClient.enterLocalPassiveMode();
        }
        return ftpClient;
    }

    /**
     * FTP上传
     *
     * @param bytes      文件字节数据
     * @param fileName   目标文件名
     * @param host       ip
     * @param port       端口
     * @param userName   用户名
     * @param pwd        密码
     * @param serverPath 文件目录
     * @return
     */
    public boolean upload(byte[] bytes, String fileName, String host, int port, String userName, String pwd, String serverPath) {
        boolean result = false;
        FTPClient ftpClient = null;

        try {
            ftpClient = initFtpClient(host, port, userName, pwd);

            //检查文件目录并判断是否新增
            String fullPath = serverPath + fileName;
            String folder = fullPath.substring(0, fullPath.lastIndexOf("/"));
            checkIsExistAndBuild(ftpClient, folder);

            //上传文件
            result = ftpClient.storeFile(fullPath, new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            LogUtil.error(FtpOperation.class, e.toString());
            throw new RuntimeException();
        } finally {
            disconnect(ftpClient);
        }
        return result;
    }

    /**
     * 默认FTP上传
     *
     * @param bytes    文件字节数据
     * @param fileName 目标文件名
     * @return
     */
    public boolean upload(byte[] bytes, String fileName) {
        return this.upload(bytes, fileName,
                ftpConfig.getHost(), ftpConfig.getPort(),
                ftpConfig.getUser(), ftpConfig.getPassword(),
                ftpConfig.getPath());
    }

    /**
     * FTP下载
     *
     * @param fileName   目标文件名
     * @param host       ip
     * @param port       端口
     * @param userName   用户名
     * @param pwd        密码
     * @param serverPath 文件目录
     * @return
     */
    public byte[] download(String fileName, String host, int port, String userName, String pwd, String serverPath) {
        FTPClient ftpClient = null;

        byte fileBytes[];

        try {
            ftpClient = initFtpClient(host, port, userName, pwd);

            //检查文件目录并判断是否新增
            String fullPath = serverPath + fileName;

            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            ftpClient.retrieveFile(fullPath, bytearrayoutputstream);
            fileBytes = bytearrayoutputstream.toByteArray();
        } catch (IOException e) {
            LogUtil.error(FtpOperation.class, e.toString());
            throw new RuntimeException();
        } finally {
            disconnect(ftpClient);
        }
        return fileBytes;
    }

    /**
     * 默认FTP下载
     *
     * @param fileName
     * @return
     */
    public byte[] download(String fileName) {
        return this.download(fileName,
                ftpConfig.getHost(), ftpConfig.getPort(),
                ftpConfig.getUser(), ftpConfig.getPassword(),
                ftpConfig.getPath());
    }


    /**
     * 关闭连接
     *
     * @param ftpClient
     */
    private void disconnect(FTPClient ftpClient) {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.logout();
            }
        } catch (IOException e) {
            LogUtil.error(FtpOperation.class, e.toString());
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                LogUtil.error(FtpOperation.class, e.toString());
            }
        }
    }

    /**
     * 判断文件夹是否存在，若不存创建一个
     *
     * @param ftpClient ftp
     * @param dir       目录
     * @throws IOException
     */
    private void checkIsExistAndBuild(FTPClient ftpClient, String dir) throws IOException {
        if (!ftpClient.changeWorkingDirectory(dir)) {
            ftpClient.makeDirectory(dir);
        }
    }
}
