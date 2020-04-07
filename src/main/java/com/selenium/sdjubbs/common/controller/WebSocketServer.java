package com.selenium.sdjubbs.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.selenium.sdjubbs.common.api.Api;
import com.selenium.sdjubbs.common.util.TimeUtil;
import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint(Api.CHAT_ROOM + "/{name}/{uuid}")
@Slf4j
/**
 * 通过websocket实现匿名聊天室
 */
public class WebSocketServer {
    private static AtomicInteger count = new AtomicInteger(0);
    private static Map<String, WebSocketServer> servers = new ConcurrentHashMap();
    private Session session = null;
    private String name = null;
    //因为是匿名聊天，所以用户名无法区分用户，通过js生成的uuid来区分
    private String uuid = null;

    /**
     * 连接创建成功
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name, @PathParam("uuid") String uuid) {
        log.info("创建连接");
        this.session = session;
        this.name = name;
        this.uuid = uuid;
        count.incrementAndGet();
        servers.put(name + uuid, this);
        try {
            JSONObject result = new JSONObject();
            result.put("count", count);
            if (uuid != null && servers.containsKey(name+uuid)) {
                for (WebSocketServer server : servers.values()) {
                    server.session.getBasicRemote().sendText(result.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        count.decrementAndGet();
        servers.remove(name + uuid);
    }

    /**
     * 接受消息并通过session返回消息
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("onmessage: "+message);
        JSONObject result = new JSONObject();
        result.put("time", TimeUtil.getTime());
        result.put("message", message);
        result.put("name", name);
        if (uuid != null && servers.containsKey(name+uuid)) {
            for (WebSocketServer server : servers.values()) {
                server.session.getBasicRemote().sendText(result.toString());
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        try {
            JSONObject result = new JSONObject();
            result.put("count", count);
            session.getBasicRemote().sendText(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
