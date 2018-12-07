package com.simple.manage.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

/**
 * Description jwt 配置对象
 * Author chen
 * CreateTime 2018-06-07 16:41
 **/
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
@EnableCaching
public class JwtConfig {
    private String issuer;  //jwt发行者

    private String base64Secret;  //jwt基础64位秘钥

    private Integer webLifecycle;  //jwt web端作用时间:秒

    private Integer webUpdateInterval;  //jwt web更新间隔:秒

    private Integer appLifecycle;  //jwt app端作用时间:秒

    private Integer appUpdateInterval;  //jwt app更新间隔:秒

    private boolean enableRenew;  //开启自动令牌续权

    private boolean antiHijack;  //开启令牌反劫持
}
