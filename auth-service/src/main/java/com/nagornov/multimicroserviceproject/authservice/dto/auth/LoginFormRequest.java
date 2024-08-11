package com.nagornov.multimicroserviceproject.authservice.dto.auth;

import lombok.Data;

@Data
public class LoginFormRequest {

    String username;
    String email;
    String phoneNumber;
    String password;

}
