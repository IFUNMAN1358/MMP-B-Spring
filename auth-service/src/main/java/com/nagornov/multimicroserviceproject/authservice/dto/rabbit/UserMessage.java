package com.nagornov.multimicroserviceproject.authservice.dto.rabbit;

import com.nagornov.multimicroserviceproject.authservice.model.User;
import com.nagornov.multimicroserviceproject.authservice.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMessage {

    private String operation;
    private User user;

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }

    public static UserMessage fromString(String str) {
        return JsonUtils.fromJsonString(str, UserMessage.class);
    }
}
