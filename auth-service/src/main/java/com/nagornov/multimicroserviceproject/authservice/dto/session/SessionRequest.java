package com.nagornov.multimicroserviceproject.authservice.dto.session;

import com.nagornov.multimicroserviceproject.authservice.util.JsonUtils;
import lombok.Data;

@Data
public class SessionRequest {

    private String serviceName;
    private String accessToken;
    private String refreshToken;
    private String deviceName;
    private String deviceOs;
    private String location;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }

    public static SessionRequest fromString(String str) {
        return JsonUtils.fromJsonString(str, SessionRequest.class);
    }

}
