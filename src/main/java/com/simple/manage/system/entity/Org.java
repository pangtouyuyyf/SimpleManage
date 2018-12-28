package com.simple.manage.system.entity;

import lombok.Data;

/**
 * Description 组织信息
 * Author chen
 * CreateTime 2018-12-28 9:28
 **/
@Data
public class Org {
    private int id;

    private String name;

    private int parentId;
}
