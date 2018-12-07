package com.simple.manage.system.enums;

/**
 * Description 系统异常码
 * Author chen
 * CreateTime 2018-06-05 16:04
 **/

public enum SysExpEnum {
    COMMON_ERROR(-1, "未知异常"),    //系统异常

    SUCCESS(0, "success"),    //请求处理成功

    FAIL(1, "failed"),    //请求处理失败

    NEED_LOGIN(2, "请先登录"),    //需要登录

    LOGIN_AGAIN(2, "由于您长时间未操作,请重新登录"),  //需要登录

    REMOTE_LOGIN(2, "您的账号已异地登录"),  //需要登录

    REJECT(3, "没有权限操作");    //没有权限

    private int code;

    private String message;

    SysExpEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
