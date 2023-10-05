package com.eks.messagingservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class ArduinoFriend {
    @Id
    private Long friendShipId;

    @ManyToOne
    @JoinColumn(name = "arduinoId1", referencedColumnName = "arduinoId")
    private Arduino arduino1;

    @ManyToOne
    @JoinColumn(name = "arduinoId2", referencedColumnName = "arduinoId")
    private Arduino arduino2;
}

