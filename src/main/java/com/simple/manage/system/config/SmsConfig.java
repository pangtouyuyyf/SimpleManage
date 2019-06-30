package com.simple.manage.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

/**
 * Description 短信配置信息
 * Author chen
 * CreateTime 2019-06-30 15:36
 **/
@Data
@Component
@ConfigurationProperties(prefix = "sms")
@EnableCaching
public class SmsConfig {
    private Integer verifyTimeOut;

    private String timeOut;

    private String accessKeyId;

    private String accessKeySecret;

    private String signName;
}
