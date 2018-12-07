package com.simple.manage.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

/**
 * Description ftp 配置文件
 * Author chen
 * CreateTime 2018-07-27 9:38
 **/
@Data
@Component
@ConfigurationProperties(prefix = "ftp")
@EnableCaching
public class FtpConfig {
    private String host;  //ip地址

    private int port;  //端口

    private String user;  //用户名

    private String password;  //密码

    private String path;  //默认保存路径

    private String url;  //访问路径
}
