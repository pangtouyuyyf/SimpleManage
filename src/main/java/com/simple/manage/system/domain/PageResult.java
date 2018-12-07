package com.simple.manage.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Description 分页数据
 * Author chen
 * CreateTime 2018-08-02 16:40
 **/
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private long total;  //数据总数

    private T list;  //列表：泛型
}
