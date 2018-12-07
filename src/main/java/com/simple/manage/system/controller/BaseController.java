package com.simple.manage.system.controller;

import com.simple.manage.system.aspact.RequestLoginContextHolder;
import com.simple.manage.system.domain.PageResult;
import com.simple.manage.system.domain.Result;
import com.simple.manage.system.domain.LoginInfo;
import com.simple.manage.system.util.ResultUtil;

/**
 * Description 基础controller
 * Author chen
 * CreateTime 2018-07-03 17:38
 **/

public class BaseController {
    public PageResult pageResult = new PageResult();

    /**
     * 返回成功
     *
     * @return
     */
    public Result success() {
        return ResultUtil.success();
    }

    /**
     * 返回成功(含参)
     *
     * @param obj
     * @return
     */
    public Result success(Object obj) {
        return ResultUtil.success(obj);
    }

    /**
     * 返回成功(含参,只针对登录使用)
     *
     * @param token
     * @param obj
     * @return
     */
    public Result success(String token, Object obj) {
        return ResultUtil.success(token, obj);
    }

    /**
     * 返回失败
     *
     * @return
     */
    public Result fail() {
        return ResultUtil.error();
    }

    /**
     * 返回失败(含参)
     *
     * @param msg
     * @return
     */
    public Result fail(String msg) {
        return ResultUtil.error(msg);
    }


    /**
     * 返回失败(含参)
     *
     * @param obj
     * @return
     */
    public Result fail(Object obj) {
        return ResultUtil.error(obj);
    }

    /**
     * 返回失败(含参)
     *
     * @param msg
     * @param obj
     * @return
     */
    public Result fail(String msg, Object obj) {
        return ResultUtil.error(msg, obj);
    }


    /**
     * 自定义返回(code尽量不要与系统code重复,参照SysExpEnum)
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public Result message(int code, String msg, Object data) {
        return ResultUtil.message(code, msg, data);
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    public LoginInfo getLoginInfo() {
        return RequestLoginContextHolder.getRequestLoginInfo();
    }
}
