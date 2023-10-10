package com.eks.messagingservice.messaging;

import com.eks.messagingservice.models.Arduino;
import com.eks.messagingservice.models.ArduinoFriend;
import com.eks.messagingservice.services.ArduinoFriendService;
import com.eks.messagingservice.services.ArduinoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    private ArduinoFriendService arduinoFriendService;

    @Autowired
    private ArduinoService arduinoService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "messaging_service_friendship")
    public void handleFriendshipMessage(Message message) {
        try {
            MessageProperties properties = message.getMessageProperties();
            String operationType = properties.getHeader("operation");
            String jsonMessage = new String(message.getBody());
            ArduinoFriend arduinoFriend = objectMapper.readValue(jsonMessage, ArduinoFriend.class);

            if (operationType.equals("save")) {
                arduinoFriendService.saveArduinoFriend(arduinoFriend);
            }
            if (operationType.equals("delete")) {
                arduinoFriendService.deleteArduinoFriend(arduinoFriend);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "messaging_service_arduino")
    public void handleArduinoMessage(Message message) {
        try {
            MessageProperties properties = message.getMessageProperties();
            String operationType = properties.getHeader("operation");
            String jsonMessage = new String(message.getBody());
            Arduino arduino = objectMapper.readValue(jsonMessage, Arduino.class);

            if (operationType.equals("save")) {
                arduinoService.saveArduino(arduino);
            }
            if (operationType.equals("delete")) {
                arduinoService.deleteArduino(arduino);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
