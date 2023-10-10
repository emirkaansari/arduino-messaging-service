package com.eks.messagingservice.services;

import com.eks.messagingservice.models.Arduino;
import com.eks.messagingservice.repository.ArduinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArduinoService {

    private final ArduinoRepository arduinoRepository;

    @Transactional
    public void changeOnlineStatus(String arduinoId, String status) {
        arduinoRepository.updateStatusByArduinoId(arduinoId, status);
    }

    @Transactional
    public void updateLastSeen(String arduinoId) {
        arduinoRepository.updateLastSeenByArduinoId(arduinoId, Date.from(Instant.now()));
    }

    public String findOwnerUsernameById(String arduinoId) {
        Optional<Arduino> arduino = arduinoRepository.findById(arduinoId);
        return arduino.get().getOwnerUsername();
    }

    public Arduino findById(String arduinoId) {
        return arduinoRepository.findById(arduinoId).orElse(null);
    }

    public Arduino saveArduino(Arduino arduino) {
        return arduinoRepository.save(arduino);
    }

    public void deleteArduino(Arduino arduino) {
        arduinoRepository.delete(arduino);
    }
}
