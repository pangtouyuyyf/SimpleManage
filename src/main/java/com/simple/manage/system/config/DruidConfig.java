package com.simple.manage.system.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Description druid连接池配置
 * Author chen
 * CreateTime 2018-07-27 16:58
 **/
@Configuration
public class DruidConfig {
    @Bean
    public ServletRegistrationBean statViewServlet() {
        /** 创建servlet注册实体 **/
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        /** 设置ip白名单 **/
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");

        /** 设置ip黑名单,如果allow与deny同时存在,deny优先于allow **/
        servletRegistrationBean.addInitParameter("deny", "192.168.7.100");

        /** 设置控制台管理用户 **/
        servletRegistrationBean.addInitParameter("loginUsername", "druid");
        servletRegistrationBean.addInitParameter("loginPassword", "12345");

        /** 是否可以重置数据 **/
        servletRegistrationBean.addInitParameter("resetEnable", "false");

        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean statFilter() {
        /** 创建过滤器 **/
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        /** 设置过滤器过滤路径 **/
        List<String> list = new ArrayList<>();
        list.add("/*");
        filterRegistrationBean.setUrlPatterns(list);

        /** 忽略过滤的形式 **/
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");

        return filterRegistrationBean;
    }
}
