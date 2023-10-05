package com.eks.messagingservice.utils;

import com.eks.messagingservice.models.Arduino;
import com.eks.messagingservice.services.ArduinoService;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ArduinoDeserializer extends StdDeserializer<Arduino> {

    @Autowired
    private ArduinoService arduinoService;

    public ArduinoDeserializer() {
        this(null);
    }


    public ArduinoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Arduino deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String arduinoId = jp.getValueAsString();

        Arduino arduino = arduinoService.findById(arduinoId);

        if (arduino == null) {
            throw new IOException("Arduino not found for id: " + arduinoId);
        }

        return arduino;
    }
}
