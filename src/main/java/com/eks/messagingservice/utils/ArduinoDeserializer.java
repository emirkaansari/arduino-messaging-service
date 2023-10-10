package com.eks.messagingservice.utils;

import com.eks.messagingservice.models.Arduino;
import com.eks.messagingservice.models.ArduinoFriend;
import com.eks.messagingservice.services.ArduinoService;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ArduinoDeserializer extends StdDeserializer<Arduino> {


    public ArduinoDeserializer() {
        this(null);
    }


    public ArduinoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Arduino deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        String arduinoId = node.get("arduinoId").asText();
        String ownerUserName = node.get("ownerUsername").asText();

        Arduino arduino = new Arduino();
        arduino.setArduinoId(arduinoId);
        arduino.setOwnerUsername(ownerUserName);

        return arduino;
    }
}
