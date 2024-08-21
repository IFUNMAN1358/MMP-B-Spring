package com.nagornov.multimicroserviceproject.authservice.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "session_id", unique = true, nullable = false, insertable = false, updatable = false)
    private Long sessionId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "access_token", length = 400, nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false, updatable = false)
    private String refreshToken;

    @Column(name = "device", nullable = false, updatable = false)
    private String device;

    @Column(name = "service", nullable = false, updatable = false)
    private String service;

    @Column(name = "os", nullable = false)
    private String os;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "last_activity", nullable = false)
    private LocalDateTime lastActivity;

    @PrePersist
    private void onCreate() {
        this.lastActivity = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.lastActivity = LocalDateTime.now();
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Session to JSON string", e);
        }
    }

    public static Session fromString(String str) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(str, Session.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON string to Session", e);
        }
    }

}
