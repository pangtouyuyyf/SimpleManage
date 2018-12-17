package com.simple.manage.system.interceptor;

import com.simple.manage.system.service.JwtService;
import com.simple.manage.system.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Description websocket拦截器(Socket建立连接握手和断开)
 * Author chen
 * CreateTime 2018-12-10 14:59
 **/
@Component
public class WSInterceptor extends TextWebSocketHandler implements HandshakeInterceptor {
    @Autowired
    private JwtService jwtService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        ServletServerHttpRequest req = (ServletServerHttpRequest) serverHttpRequest;
        String token = req.getServletRequest().getParameter(CommonUtil.TOKEN);
        boolean canConnect = this.jwtService.judgeJWT(token);
        if (canConnect) {
            // 鉴权通过后，设置当前uid
            map.put(CommonUtil.USER_ID, this.jwtService.parseJWT(token).getClaim(CommonUtil.USER_ID).asString());
        }
        return canConnect;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, @Nullable Exception e) {
        //断开连接时使用
        System.out.println("websocket stop");
    }
}
