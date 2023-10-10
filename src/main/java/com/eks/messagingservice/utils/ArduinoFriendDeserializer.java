package com.eks.messagingservice.utils;


import com.eks.messagingservice.models.Arduino;
import com.eks.messagingservice.models.ArduinoFriend;
import com.eks.messagingservice.services.ArduinoService;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ArduinoFriendDeserializer extends StdDeserializer<ArduinoFriend> {

    @Autowired
    private ArduinoService arduinoService;

    public ArduinoFriendDeserializer() {
        this(null);
    }


    public ArduinoFriendDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ArduinoFriend deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        String friendShipId = node.get("friendShipId").asText();
        String arduinoId1 = node.get("arduino1").asText();
        String arduinoId2 = node.get("arduino2").asText();

        ArduinoFriend arduinoFriend = new ArduinoFriend();
        arduinoFriend.setFriendShipId(Long.parseLong(friendShipId));

        Arduino arduino1 = arduinoService.findById(arduinoId1);
        Arduino arduino2 = arduinoService.findById(arduinoId2);

        if (arduino1 == null || arduino2 == null) {
            throw new RuntimeException("Arduino doesn't exist in db.");
        }

        arduinoFriend.setArduino1(arduino1);
        arduinoFriend.setArduino2(arduino2);

        return arduinoFriend;
    }
}

