package com.simple.manage.system.controller;

import com.simple.manage.system.util.RandomNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Description websocket controller
 * Author chen
 * CreateTime 2018-08-08 10:02
 **/
@RestController
public class WebSocketController extends BaseController {
    private static final String SENDING_URL = "/topic/server-broadcaster";
    private static final String RECEIVING_URL = "/server-receiver";

    @Autowired
    private SimpMessagingTemplate template;

    private AtomicLong counter = new AtomicLong(0);
    private String message = "";

    /**
     * 接受信息
     *
     * @param message
     */
    @MessageMapping(RECEIVING_URL)
    public void onReceivedMessage(String message) {
        System.out.println("New message received : " + message);
    }

    /**
     * 订阅
     *
     * @return
     */
    @SubscribeMapping(SENDING_URL)
    public String onSubscribe() {
        System.out.println("SUBSCRIBED : " + message);
        return "SUBSCRIBED : " + message;
    }

    /**
     * 定时推送
     */
    @Scheduled(fixedRate = 3000)
    public void sendMessage() {
        template.convertAndSend(SENDING_URL, buildNextMessage());
    }

    private String buildNextMessage() {
        message = "Test" + counter.getAndIncrement();
        System.out.println("Send message " + message);
        return Integer.toString(RandomNumUtil.randomInt(100, 200));
    }
}
