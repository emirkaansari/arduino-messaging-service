package com.eks.messagingservice.services;

import org.springframework.stereotype.Service;

@Service
public class HandshakeService {
    private String kk;

    //Verify ArduinoId is valid, and registered to a user.
    public boolean isIdValid(String senderArduinoID) {
        return true;
    }
}
