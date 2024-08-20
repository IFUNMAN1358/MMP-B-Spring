package com.nagornov.multimicroserviceproject.userprofileservice.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Session {

    private Long sessionId;
    private UUID userId;
    private String accessToken;
    private String refreshToken;
    private String device;
    private String service;
    private String os;
    private String location;
    private LocalDateTime lastActivity;

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
