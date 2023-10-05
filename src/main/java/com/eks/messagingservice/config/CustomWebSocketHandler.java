package com.eks.messagingservice.config;

import com.eks.messagingservice.models.Message;
import com.eks.messagingservice.services.ArduinoFriendService;
import com.eks.messagingservice.services.ArduinoService;
import com.eks.messagingservice.services.MessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> sessions = new ArrayList<>();

    @Autowired
    private MessageService messageService;
    @Autowired
    private ArduinoService arduinoService;
    @Autowired
    private ArduinoFriendService arduinoFriendService;

    public CustomWebSocketHandler(MessageService messageService, ArduinoService arduinoService, ArduinoFriendService arduinoFriendService) {
        this.messageService = messageService;
        this.arduinoService = arduinoService;
        this.arduinoFriendService = arduinoFriendService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        String arduinoId = (String) session.getAttributes().get("arduinoID");
        arduinoService.changeOnlineStatus(arduinoId, "online");
        sendOnlineUserCount();
        List<String> friendIds = arduinoFriendService.getOnlineFriendIds(arduinoId);
        if (!friendIds.isEmpty()) {
            String arduinoUserName = arduinoService.findOwnerUsernameById(arduinoId);
            for (int i = 0; i < friendIds.size(); i++) {
                WebSocketSession socketSession = findSessionByArduinoID(friendIds.get(i));
                if (socketSession != null && socketSession.isOpen()) {
                    socketSession.sendMessage(new TextMessage("Your friend "
                            + arduinoUserName + " is online!"));
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        String arduinoId = (String) session.getAttributes().get("arduinoID");
        arduinoService.changeOnlineStatus(arduinoId, "offline");
        arduinoService.updateLastSeen(arduinoId);
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

