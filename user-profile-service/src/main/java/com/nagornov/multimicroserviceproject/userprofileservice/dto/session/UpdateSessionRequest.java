package com.nagornov.multimicroserviceproject.userprofileservice.dto.session;

import lombok.Data;

@Data
public class UpdateSessionRequest {

    private String refreshToken;

}
