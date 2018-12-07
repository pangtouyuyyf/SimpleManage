package com.simple.manage.system.util;

import com.simple.manage.system.domain.Result;
import com.simple.manage.system.enums.SysExpEnum;

/**
 * Description 数据结果统一处理
 * Author chen
 * CreateTime 2018-06-06 17:39
 **/

public class ResultUtil {
    /**
     * 返回成功
     *
     * @return
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 返回成功(含参)
     *
     * @param obj
     * @return
     */
    public static Result success(Object obj) {
        Result result = new Result();
        result.setCode(SysExpEnum.SUCCESS.getCode());
        result.setMessage(SysExpEnum.SUCCESS.getMessage());
        result.setData(obj);
        return result;
    }

    /**
     * 返回成功(含参,仅在登录操作下使用)
     *
     * @param token
     * @param obj
     * @return
     */
    public static Result success(String token, Object obj) {
        Result result = new Result();
        result.setCode(SysExpEnum.SUCCESS.getCode());
        result.setMessage(SysExpEnum.SUCCESS.getMessage());
        result.setToken(token);
        result.setData(obj);
        return result;
    }

    /**
     * 返回失败
     *
     * @return
     */
    public static Result error() {
        Result result = new Result();
        result.setCode(SysExpEnum.FAIL.getCode());
        result.setMessage(SysExpEnum.FAIL.getMessage());
        result.setData(null);
        return result;
    }

    /**
     * 返回失败(含参)
     *
     * @param msg
     * @return
     */
    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(SysExpEnum.FAIL.getCode());
        result.setMessage(msg);
        result.setData(null);
        return result;
    }

    /**
     * 返回失败(含参)
     *
     * @param obj
     * @return
     */
    public static Result error(Object obj) {
        Result result = new Result();
        result.setCode(SysExpEnum.FAIL.getCode());
        result.setMessage(SysExpEnum.FAIL.getMessage());
        result.setData(obj);
        return result;
    }

    /**
     * 返回失败(含参)
     *
     * @param msg
     * @param obj
     * @return
     */
    public static Result error(String msg, Object obj) {
        Result result = new Result();
        result.setCode(SysExpEnum.FAIL.getCode());
        result.setMessage(msg);
        result.setData(obj);
        return result;
    }

    /**
     * 返回失败(含参)
     *
     * @param sysExpEnum
     * @return
     */
    public static Result error(SysExpEnum sysExpEnum) {
        Result result = new Result();
        result.setCode(sysExpEnum.getCode());
        result.setMessage(sysExpEnum.getMessage());
        result.setData(null);
        return result;
    }

    /**
     * 自定义提示返回结果(含参)
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Result message(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(msg);
        result.setData(data);
        return result;
    }
}