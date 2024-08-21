package com.nagornov.multimicroserviceproject.userprofileservice.dto.session;

import lombok.Data;

import java.util.UUID;

@Data
public class SessionRequest {

    private UUID userId;
    private String accessToken;
    private String refreshToken;
    private String device;
    private String service;
    private String os;
    private String location;

}
