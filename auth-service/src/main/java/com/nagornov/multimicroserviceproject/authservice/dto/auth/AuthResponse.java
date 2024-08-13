package com.nagornov.multimicroserviceproject.authservice.dto.auth;

import com.nagornov.multimicroserviceproject.authservice.dto.jwt.JwtResponse;
import com.nagornov.multimicroserviceproject.authservice.dto.user.UserResponse;
import com.nagornov.multimicroserviceproject.authservice.mapper.UserMapper;
import com.nagornov.multimicroserviceproject.authservice.model.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private UserResponse user;
    private JwtResponse tokens;

}
