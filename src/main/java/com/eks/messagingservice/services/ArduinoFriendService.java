package com.eks.messagingservice.services;

import com.eks.messagingservice.models.ArduinoFriend;
import com.eks.messagingservice.repository.ArduinoFriendRepository;
import com.eks.messagingservice.repository.ArduinoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArduinoFriendService {

    private final ArduinoFriendRepository arduinoFriendRepository;

    public List<String> getOnlineFriendIds(String arduinoID) {
        return arduinoFriendRepository.findOnlineFriendIdsByArduinoId(arduinoID);
    }

    public void saveArduinoFriend(ArduinoFriend arduinoFriend) {
        arduinoFriendRepository.save(arduinoFriend);
    }

}
