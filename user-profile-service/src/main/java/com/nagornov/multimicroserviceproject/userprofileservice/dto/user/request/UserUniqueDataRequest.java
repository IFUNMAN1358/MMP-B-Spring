package com.nagornov.multimicroserviceproject.userprofileservice.dto.user.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UserUniqueDataRequest {

    private UUID userId;
    private String username;
    private String phoneNumber;
    private String email;

}
