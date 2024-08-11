package com.nagornov.multimicroserviceproject.authservice.dto.session;

import lombok.Data;

@Data
public class UpdateSessionRequest {

    private String refreshToken;

}
