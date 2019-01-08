package com.simple.manage.system.exception;

import com.simple.manage.system.domain.Result;
import com.simple.manage.system.enums.SysExpEnum;
import com.simple.manage.system.util.ResultUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 通用异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result commonHandler(Exception e) {
        logger.error(ExceptionUtils.getFullStackTrace(e));

        Result result = ResultUtil.error(SysExpEnum.COMMON_ERROR);

        // 判断异常类型
//        if (e instanceof MissingServletRequestParameterException) {
//
//
//        }

        return result;
    }
}
