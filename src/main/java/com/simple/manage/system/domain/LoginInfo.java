package com.simple.manage.system.domain;

import com.simple.manage.system.entity.Org;
import com.simple.manage.system.entity.Role;
import com.simple.manage.system.entity.User;
import lombok.Data;

/**
 * Description 登录信息
 * Author chen
 * CreateTime 2018-09-26 9:51
 **/
@Data
public class LoginInfo {
    private String channel;

    private User user;

    private Role role;

    private Org org;
}
