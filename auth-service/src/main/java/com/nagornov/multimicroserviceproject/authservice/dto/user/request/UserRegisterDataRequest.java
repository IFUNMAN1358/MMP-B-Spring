package com.nagornov.multimicroserviceproject.authservice.dto.user.request;

import lombok.Data;

@Data
public class UserRegisterDataRequest {

    private String username;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

}
