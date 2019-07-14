package com.simple.manage.system.entity;

import lombok.Data;

/**
 * Description 用户
 * Author chen
 * CreateTime 2018-07-19 15:55
 **/
@Data
public class User {
    private int id;

    private String name;

    private String loginName;

    private String password;

    private String phone;

    private String email;

    private int roleId;
}
