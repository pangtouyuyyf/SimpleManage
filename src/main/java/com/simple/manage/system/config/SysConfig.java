package com.simple.manage.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

/**
 * Description 系统通用配置
 * Author chen
 * CreateTime 2018-07-25 16:38
 **/
@Data
@Component
@ConfigurationProperties(prefix = "sys")
@EnableCaching
public class SysConfig {
    private String name;  //系统名称

    private String code;  //系统编码

    private boolean enableAUZ;  //是否开启权限验证

    private boolean showIp;  //是否显示IP

    private String password;  //系统重置登录密码

    private boolean cleanLoginInfo;  //退出登录是否清除当前登录信息缓存
}
