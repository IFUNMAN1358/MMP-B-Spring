package com.nagornov.multimicroserviceproject.userprofileservice.dto.rabbit;

import com.nagornov.multimicroserviceproject.userprofileservice.model.Session;
import com.nagornov.multimicroserviceproject.userprofileservice.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionMessage {

    private String service;
    private String operation;
    private Session session;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }

    public static SessionMessage fromString(String str) {
        return JsonUtils.fromJsonString(str, SessionMessage.class);
    }

}
