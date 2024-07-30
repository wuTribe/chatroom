package com.wuyufan.service;

import cn.hutool.json.JSONUtil;
import com.wuyufan.bean.Message;
import com.wuyufan.config.WebSocketConfig;
import com.wuyufan.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint(value = "/chat", configurator = WebSocketConfig.class)
@Component
public class ChatEndpoint {

    private final static String CURRENT_USER = "currentUser";

    // 保存在线的用户，key为用户名，value为 Session 对象
    private static final Map<String, Session> onlineUsers = new ConcurrentHashMap<>();

    private HttpSession httpSession;

    /**
     * 建立websocket连接后，被调用
     *
     * @param session Session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        String user = (String) this.httpSession.getAttribute(CURRENT_USER);
        if (user != null) {
            onlineUsers.put(user, session);
        }

        // 通知所有用户，当前用户上线了
        String message = MessageUtils.getMessage(true, null, getFriends());
        broadcastAllUsers(message);
    }


    /**
     * 浏览器发送消息到服务端时该方法会被调用，也就是私聊
     * 张三  -->  李四
     *
     * @param message String
     */
    @OnMessage
    public void onMessage(String message) {
        try {
            // 将消息推送给指定的用户
            Message msg = JSONUtil.toBean(message, Message.class);

            // 获取消息接收方的用户名
            String toName = msg.getToName();
            String tempMessage = msg.getMessage();

            // 获取消息接收方用户对象的 session 对象
            Session session = onlineUsers.get(toName);
            String currentUser = (String) this.httpSession.getAttribute(CURRENT_USER);
            String messageToSend = MessageUtils.getMessage(false, currentUser, tempMessage);

            session.getBasicRemote().sendText(messageToSend);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 断开 websocket 连接时被调用
     *
     * @param session Session
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        // 1.从 onlineUsers 中删除当前用户的 session 对象，表示当前用户已下线
        String user = (String) this.httpSession.getAttribute(CURRENT_USER);
        if (user != null) {
            Session remove = onlineUsers.remove(user);
            if (remove != null) {
                remove.close();
            }

            session.close();
        }

        // 2.通知其他用户，当前用户已下线
        // 注意：不是发送类似于 xxx 已下线的消息，而是向在线用户重新发送一次当前在线的所有用户
        String message = MessageUtils.getMessage(true, null, getFriends());
        broadcastAllUsers(message);
    }




    private void broadcastAllUsers(String message) {
        try {
            Set<Map.Entry<String, Session>> entries = onlineUsers.entrySet();

            for (Map.Entry<String, Session> entry : entries) {
                // 获取到所有用户对应的 session 对象
                Session session = entry.getValue();

                // 使用 getBasicRemote() 方法发送同步消息
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception exception) {
            log.error("", exception);
        }
    }

    private Set<String> getFriends() {
        return onlineUsers.keySet();
    }
}
