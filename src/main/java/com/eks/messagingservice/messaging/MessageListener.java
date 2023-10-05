package com.eks.messagingservice.messaging;

import com.eks.messagingservice.models.ArduinoFriend;
import com.eks.messagingservice.services.ArduinoFriendService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    private ArduinoFriendService arduinoFriendService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "messaging_service_friendship")
    public void handleMessage(byte[] messageBody) {
        try {
            String jsonMessage = new String(messageBody);
            ArduinoFriend arduinoFriend = objectMapper.readValue(jsonMessage, ArduinoFriend.class);
            arduinoFriendService.saveArduinoFriend(arduinoFriend);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
