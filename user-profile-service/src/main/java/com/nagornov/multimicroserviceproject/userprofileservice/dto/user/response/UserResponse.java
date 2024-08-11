package com.nagornov.multimicroserviceproject.userprofileservice.dto.user.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nagornov.multimicroserviceproject.userprofileservice.config.security.GrantedAuthorityDeserializer;
import com.nagornov.multimicroserviceproject.userprofileservice.model.Role;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserResponse {

    private UUID userId;
    private String username;
    private String firstName;
    private String lastName;

    @JsonDeserialize(contentUsing = GrantedAuthorityDeserializer.class)
    private Set<Role> roles;

}
