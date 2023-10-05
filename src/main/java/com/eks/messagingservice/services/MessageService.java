package com.eks.messagingservice.services;

import com.eks.messagingservice.models.Message;
import com.eks.messagingservice.repository.ArduinoFriendRepository;
import com.eks.messagingservice.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message createMessage(String senderArduinoId, String receiverArduinoId, String context) {
        Message message = new Message();
        message.setSenderArduinoId(senderArduinoId);
        message.setReceiverArduinoId(receiverArduinoId);
        message.setContext(context);
        message.setDate(Date.from(Instant.now()));

        return messageRepository.save(message);
    }


}
