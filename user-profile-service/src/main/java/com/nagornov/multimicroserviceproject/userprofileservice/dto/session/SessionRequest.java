package com.nagornov.multimicroserviceproject.userprofileservice.dto.session;

import lombok.Data;

@Data
public class SessionRequest {

    private String serviceName;
    private String accessToken;
    private String refreshToken;
    private String deviceName;
    private String deviceOs;
    private String location;

}
