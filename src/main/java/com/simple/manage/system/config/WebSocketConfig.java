package com.simple.manage.system.config;

import com.simple.manage.system.interceptor.WSInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Description websocket配置
 * Author chen
 * CreateTime 2018-08-08 9:48
 **/
@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
//通过EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息,此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private WSInterceptor wsInterceptor;
    /**
     * endPoint 注册STOMP协议的节点(endpoint),并映射指定的url
     *
     * @param registry
     */
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").addInterceptors(wsInterceptor).setAllowedOrigins("*");
    }

    /**
     * 配置消息代理(message broker)
     *
     * @param registry
     */
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic");
    }
}
