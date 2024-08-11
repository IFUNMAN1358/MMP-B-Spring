package com.nagornov.multimicroserviceproject.userprofileservice.dto.user.request;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nagornov.multimicroserviceproject.userprofileservice.config.security.UserDeserializer;
import com.nagornov.multimicroserviceproject.userprofileservice.model.User;
import lombok.Data;

@Data
public class UserRequest {

    @JsonDeserialize(using = UserDeserializer.class)
    private User user;

    @JsonCreator
    public UserRequest(@JsonProperty("user") User user) {
        this.user = user;
    }

}
