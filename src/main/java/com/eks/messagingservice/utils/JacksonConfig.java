package com.eks.messagingservice.utils;

import com.eks.messagingservice.models.Arduino;
import com.eks.messagingservice.models.ArduinoFriend;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Autowired
    private ArduinoFriendDeserializer arduinoFriendDeserializer;
    @Autowired
    private ArduinoDeserializer arduinoDeserializer;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ArduinoFriend.class, arduinoFriendDeserializer);
        module.addDeserializer(Arduino.class, arduinoDeserializer);
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
