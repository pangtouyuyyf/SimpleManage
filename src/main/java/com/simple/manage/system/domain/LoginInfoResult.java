package com.simple.manage.system.domain;

import lombok.Data;

/**
 * Description 登录缓存数据结果
 * Author chen
 * CreateTime 2018-12-29 9:48
 **/
@Data
public class LoginInfoResult {
    private boolean isChecked;

    private LoginInfo loginInfo;
}
