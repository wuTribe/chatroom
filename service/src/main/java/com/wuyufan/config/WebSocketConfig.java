package com.wuyufan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

@Configuration
@EnableWebSocket
public class WebSocketConfig extends ServerEndpointConfig.Configurator {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


    @Override
    public void modifyHandshake(ServerEndpointConfig serverEndpointConfig,
                                HandshakeRequest request,
                                HandshakeResponse response) {
        // 获取 HttpSession 对象
        HttpSession httpSession = (HttpSession) request.getHttpSession();

        // 将 httpSession 对象保存起来，存到 ServerEndpointConfig 对象中
        // 在 ChatEndpoint 类的 onOpen 方法就能通过 EndpointConfig 对象获取在这里存入的数据
        serverEndpointConfig.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }
}
