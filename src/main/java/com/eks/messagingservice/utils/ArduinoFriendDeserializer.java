package com.eks.messagingservice.utils;


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
        String arduino1 = node.get("arduino1").asText();
        String arduino2 = node.get("arduino2").asText();

        ArduinoFriend arduinoFriend = new ArduinoFriend();
        arduinoFriend.setFriendShipId(Long.parseLong(friendShipId));

        arduinoFriend.setArduino1(arduinoService.findById(arduino1));
        arduinoFriend.setArduino2(arduinoService.findById(arduino2));

        return arduinoFriend;
    }
}

