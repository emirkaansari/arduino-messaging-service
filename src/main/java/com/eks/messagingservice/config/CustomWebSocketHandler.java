package com.eks.messagingservice.config;

import com.eks.messagingservice.models.Message;
import com.eks.messagingservice.services.MessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> sessions = new ArrayList<>();

    @Autowired
    private MessageService messageService;

    public CustomWebSocketHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        sendOnlineUserCount();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        sendOnlineUserCount();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());

        Message arduinoMessage = messageService.createMessage((String) session.getAttributes().get("arduinoID"),
                jsonNode.get("receiverArduinoID").asText(),
                jsonNode.get("context").asText());


        WebSocketSession receiverSession = findSessionByArduinoID(arduinoMessage.getReceiverArduinoId());

        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(new TextMessage(arduinoMessage.getContext() + " from: "
                    + arduinoMessage.getSenderArduinoId()));
        } else {
            // Receiver is not online, send a message to sender
            WebSocketSession senderSession = findSessionByArduinoID(arduinoMessage.getSenderArduinoId());
            if (senderSession != null && senderSession.isOpen()) {
                senderSession.sendMessage(new TextMessage("User "
                        + arduinoMessage.getReceiverArduinoId() + " is not online."));
            }
        }
    }

    private WebSocketSession findSessionByArduinoID(String arduinoID) {
        for (WebSocketSession webSocketSession : sessions) {
            String sessionArduinoID = (String) webSocketSession.getAttributes().get("arduinoID");
            if (sessionArduinoID != null && sessionArduinoID.equals(arduinoID)) {
                return webSocketSession;
            }
        }
        return null;
    }

    private void sendOnlineUserCount() throws IOException {
        int onlineUserCount = sessions.size();

        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage("Online users: " + onlineUserCount));
            }
        }
    }
}

