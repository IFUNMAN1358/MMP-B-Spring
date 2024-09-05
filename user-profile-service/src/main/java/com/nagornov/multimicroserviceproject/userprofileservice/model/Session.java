package com.nagornov.multimicroserviceproject.userprofileservice.model;

import com.nagornov.multimicroserviceproject.userprofileservice.util.JsonUtils;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Session {

    private Long sessionId;
    private UUID userId;
    private String serviceName;
    private String accessToken;
    private String refreshToken;
    private String deviceName;
    private String deviceOs;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime lastActivity;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }

    public static Session fromString(String str) {
        return JsonUtils.fromJsonString(str, Session.class);
    }

}
