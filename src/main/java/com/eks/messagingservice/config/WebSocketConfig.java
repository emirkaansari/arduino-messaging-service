package com.eks.messagingservice.config;

import com.eks.messagingservice.services.ArduinoFriendService;
import com.eks.messagingservice.services.ArduinoService;
import com.eks.messagingservice.services.HandshakeService;
import com.eks.messagingservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.WebSocketHandler;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final MessageService messageService;
    private final ArduinoService arduinoService;
    private final ArduinoFriendService arduinoFriendService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/ws")
                .addInterceptors(new ArduinoHandShakeInterceptor())
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new CustomWebSocketHandler(messageService, arduinoService, arduinoFriendService);
    }
}
