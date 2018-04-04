package com.dptest.webscoketdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * 描述: 消息代理端点应许/websocket端口号为63342的端口访问
 *
 * @author dengpeng
 * @create 2018-04-03 14:56
 */
@SuppressWarnings("deprecation")
@Configuration
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/websocket").setAllowedOrigins("http://localhost:63342").withSockJS();
  }
}
