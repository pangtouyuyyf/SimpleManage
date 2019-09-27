package com.simple.manage.system.domain;

import com.simple.manage.system.entity.User;
import lombok.Data;

import java.util.List;

/**
 * Description 登录信息
 * Author chen
 * CreateTime 2018-09-26 9:51
 **/
@Data
public class LoginInfo {
    private String channel;  //登录渠道(web/app)

    private User user;  //个人信息
}
