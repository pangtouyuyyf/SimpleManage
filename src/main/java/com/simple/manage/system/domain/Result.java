package com.simple.manage.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Description 统一返回数据结果
 * Author chen
 * CreateTime 2018-06-06 17:35
 **/
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;  //返回码

    private String message;  //提示信息

    private String token;  //令牌

    private T data;  //具体内容：泛型
}
