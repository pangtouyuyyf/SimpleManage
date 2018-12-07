package com.simple.manage.system.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

/**
 * Description 支付宝配置
 * Author chen
 * CreateTime 2018-12-07 14:14
 **/
@Component
@ConfigurationProperties(prefix = "alipay")
@EnableCaching
@Data
public class AlipayConfig {
    private String serveUrl;  //支付宝网关

    private String appId;  //APPID

    private String storeId;  //商户门店编号

    private String appPrivateKey;  //应用私钥

    private String alipayPublicKey;   //支付宝公钥

    private String charset; //编码

    private String dataFormat;  //数据格式

    private String signType; //签名方式

    private String notifyUrl;  //支付宝服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问

    private String qrCodeImgUrl;  //支付宝生成临时二维码图片地址
}
