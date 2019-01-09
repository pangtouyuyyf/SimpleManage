package com.simple.manage.system.aspact;

import com.simple.manage.system.config.SysConfig;
import com.simple.manage.system.enums.SysExpEnum;
import com.simple.manage.system.util.CommonUtil;
import com.simple.manage.system.util.LogUtil;
import com.simple.manage.system.util.ResultUtil;
import io.netty.util.internal.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 角色操作权限验证(注：必须先获取登录信息)
 * Author chen
 * CreateTime 2018-08-07 10:50
 **/
@Aspect
@Order(1)
@Component
public class AuthorizationAspect {
    @Autowired
    private SysConfig sysConfig;

//    @Autowired
//    private RoleAccessService roleAccessService;

    /**
     * 业务controller类方法自定义注解角色操作权限验证(对Controller类中方法用此注解进行用户操作权限验证),精确式验证
     */
    @Pointcut("@annotation(com.simple.manage.system.annotation.AuthorizationAnnotation)")
    public void authorizationVerify() {
    }

    /**
     * 验证处理
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("authorizationVerify()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        /** 角色权限校验 **/
        if (sysConfig.isEnableAUZ()) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            String contextPath = request.getContextPath();
            List<Integer> roleList = RequestLoginContextHolder.getRequestLoginInfo().getRoleList();
            String access = request.getRequestURI();

            if (!StringUtil.isNullOrEmpty(contextPath) && !CommonUtil.BACKSLASH.equals(contextPath)) {
                access = access.substring(access.indexOf(contextPath) + contextPath.length(), access.length());
            }

            Map<String, Object> params = new HashMap<>();
            params.put("role_id", roleList);
            params.put("access_url", access);
//            int count = this.roleAccessService.countRoleAccess(params);

//            if (count < 1) {
//                return ResultUtil.error(SysExpEnum.REJECT);
//            }
        }

        /** 执行目标 **/
        return joinPoint.proceed();
    }

    /**
     * 异常处理
     *
     * @param throwable
     * @return
     */
    @AfterThrowing(pointcut = "authorizationVerify()", throwing = "throwable")
    public Object AfterThrowingAspect(Throwable throwable) {
        LogUtil.error(AuthorizationAspect.class, throwable.toString());
        return ResultUtil.error(SysExpEnum.COMMON_ERROR);
    }
}
