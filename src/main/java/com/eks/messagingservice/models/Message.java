package com.eks.messagingservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Table(name = "message")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderArduinoId;
    private String receiverArduinoId;
    private String context;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false, updatable = false)
    private Date date;
}

