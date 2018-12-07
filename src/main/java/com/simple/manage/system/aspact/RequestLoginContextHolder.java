package com.simple.manage.system.aspact;

import com.simple.manage.system.domain.LoginInfo;
import org.springframework.core.NamedInheritableThreadLocal;

/**
 * Description ThreadLocal
 * Author chen
 * CreateTime 2018-07-19 15:54
 **/

public abstract class RequestLoginContextHolder {
    private static final ThreadLocal<LoginInfo> inheritableUserHolder = new NamedInheritableThreadLocal<LoginInfo>("Request LoginInfo");

    public static void destroy() {
        inheritableUserHolder.remove();
    }

    public static LoginInfo getRequestLoginInfo() {
        return inheritableUserHolder.get();
    }

    public static void setRequestLoginInfo(LoginInfo loginInfo) {
        inheritableUserHolder.set(loginInfo);
    }
}
