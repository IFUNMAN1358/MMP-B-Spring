package com.nagornov.multimicroserviceproject.authservice.dto.auth;

import com.nagornov.multimicroserviceproject.authservice.dto.jwt.JwtResponse;
import com.nagornov.multimicroserviceproject.authservice.dto.user.response.UserResponse;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private UserResponse user;
    private JwtResponse tokens;

}
