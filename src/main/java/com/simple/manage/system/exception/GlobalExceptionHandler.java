package com.simple.manage.system.exception;

import com.simple.manage.system.domain.Result;
import com.simple.manage.system.enums.SysExpEnum;
import com.simple.manage.system.util.LogUtil;
import com.simple.manage.system.util.ResultUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description controller通用异常处理
 * Author chen
 * CreateTime 2018-06-05 16:31
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 通用异常处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result commonHandler(Exception ex) {
        LogUtil.error(GlobalExceptionHandler.class, ExceptionUtils.getStackTrace(ex));

        Result result = ResultUtil.error(SysExpEnum.COMMON_ERROR);

        // 判断异常类型
//        if (e instanceof MissingServletRequestParameterException) {
//
//
//        }

        return result;
    }
}
