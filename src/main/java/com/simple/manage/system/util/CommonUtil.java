package com.simple.manage.system.util;

import io.netty.util.internal.StringUtil;

/**
 * Description 公共工具类
 * Author chen
 * CreateTime 2018-07-24 16:45
 **/

public class CommonUtil {
    //星号
    public static final String ASTERISK = "*";

    //是
    public static final String SIGN_YES = "0";

    //否
    public static final String SIGN_NO = "1";

    //令牌标识字段
    public static final String TOKEN = "token";

    //下划线分隔符
    public static final String UNDERLINE = "_";

    //反斜杠分隔符
    public static final String BACKSLASH = "/";

    //问号分隔符
    public static final String QUESTION_MARK = "?";

    //分号分隔符
    public static final String SEMICOLON = ";";

    //客户登录渠道
    public static final String CHANNEL = "channel";

    //客户web渠道登录
    public static final String CHANNEL_WEB = "web";

    //客户app渠道登录
    public static final String CHANNEL_APP = "app";

    //用户ID标识字段
    public static final String USER_ID = "id";

    //组织ID标识字段
    public static final String ORG_ID = "oId";

    //令牌命名前缀
    public static final String TOKEN_PREFIX = "token";

    //登录信息命名前缀
    public static final String LOGIN_INFO_PREFIX = "loginInfo";

    //短信验证码后缀
    public static final String SMS_CODE_SUFFIX = "smsCode";

    //操作权限编码位数
    public static final int OPERATE_CODE_LENGTH = 6;

    //默认角色操作编码
    public static final int OPERATE_CODE_DEFAULT = 0;

    //默认树根节点父节点id
    public static final int TREE_ROOT_PARENT_ID = -1;

    //默认树节点排序
    public static final int TREE_DEFAULT_ORDER = 1;

    //创建二级管理员角色名称后缀
    public static final String SECOND_LEVEL_ROLE_NAME_SUFFIX = "_管理员";

    //创建二级管理员角色代码前缀
    public static final String SECOND_LEVEL_ROLE_CODE_PREFIX = "SYS_";

    //默认二级管理员角色代码
    public static final String SECOND_LEVEL_ROLE_CODE = "ADMIN";

    //短信验证码redis主键前缀
    public static final String SMS_VERIFY_CODE_PREFIX ="verifyCode_";

    /**
     * url处理(去掉url参数)
     *
     * @param url
     * @return
     */
    public static String urlHandler(String url) {
        if (StringUtil.isNullOrEmpty(url)) {
            return null;
        }
        if (url.contains(SEMICOLON)) {
            url = url.substring(0, url.indexOf(SEMICOLON));
        }
        if (url.contains(QUESTION_MARK)) {
            url = url.substring(0, url.indexOf(QUESTION_MARK));
        }
        return url;
    }
}
