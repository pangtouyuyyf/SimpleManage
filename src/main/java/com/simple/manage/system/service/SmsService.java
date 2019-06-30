package com.simple.manage.system.service;

import com.aliyuncs.CommonResponse;

/**
 * Description 短信服务接口
 * Author chen
 * CreateTime 2019-06-30 15:21
 **/

public interface SmsService {
    CommonResponse sendSms(String phoneNums, String signName, String templateCode, String template);

    String sendVerifySms(String phoneNum);
}
