package com.wzcsoft.dzpjdy.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    /**
     * 自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {

        System.out.println("=================================================");
        System.out.println("websocketconfig");
        System.out.println("=================================================");

        return new ServerEndpointExporter();
    }

}