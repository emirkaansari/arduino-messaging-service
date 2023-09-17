package com.eks.messagingservice.services;

import com.eks.messagingservice.models.Message;
import com.eks.messagingservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(String senderArduinoId, String receiverArduinoId, String context) {
        Message message = new Message();
        message.setSenderArduinoId(senderArduinoId);
        message.setReceiverArduinoId(receiverArduinoId);
        message.setContext(context);
        message.setDate(new Date());

        return messageRepository.save(message);
    }
}