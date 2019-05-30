package com.simple.manage.system.aspect;

import com.simple.manage.system.config.SysConfig;
import com.simple.manage.system.util.LogUtil;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Description http登录请求拦截
 * Author chen
 * CreateTime 2018-07-27 15:37
 **/
@Aspect
@Component
public class HttpAspect {
    @Autowired
    private SysConfig sysConfig;

    /**
     * 业务controller类层面的Token验证(对所有TokenController实现类进行token验证),地毯式验证
     */
    @Pointcut("execution(* com.simple.manage.system.controller.LoginController.login(..))")
    public void http() {
    }

    /**
     * 请求调用前
     */
    @After("http()")
    public void doAfter() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if (sysConfig.isShowIp()) {
            LogUtil.info(HttpAspect.class, request.getRemoteAddr());
        }
    }
}
