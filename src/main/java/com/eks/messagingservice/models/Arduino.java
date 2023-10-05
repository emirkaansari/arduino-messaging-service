package com.eks.messagingservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Arduino {
    @Id
    private String arduinoId;
    private String ownerUsername;
    private String status;
    private Date lastSeen;

    @OneToMany(mappedBy = "arduino1")
    private List<ArduinoFriend> friendList;
}

