package com.nagornov.multimicroserviceproject.authservice.dto.registration;

import lombok.Data;

@Data
public class RegVerifyRequest {

    private String verificationCode;

}
