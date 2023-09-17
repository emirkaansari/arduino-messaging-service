package com.eks.messagingservice.config;

import com.eks.messagingservice.services.HandshakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.IOException;
import java.util.Map;

@Component
public class ArduinoHandShakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private HandshakeService handshakeService;

    public ArduinoHandShakeInterceptor(HandshakeService handshakeService) {
        this.handshakeService = handshakeService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String arduinoID = request.getHeaders().getFirst("arduinoID");
        if (arduinoID != null && handshakeService.isIdValid(arduinoID)) {
            attributes.put("arduinoID", arduinoID);
            return true;
        } else {
            response.setStatusCode(HttpStatus.BAD_REQUEST);
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
    }
}
