package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.dto.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.authservice.dto.jwt.JwtResponse;
import com.nagornov.multimicroserviceproject.authservice.model.User;
import com.nagornov.multimicroserviceproject.authservice.repository.JwtRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtRepository jwtRepository;

    public JwtResponse getAccessToken(User user) {
        final String accessToken = jwtRepository.generateAccessToken(user);
        return new JwtResponse(accessToken, null);
    }

    public JwtResponse getAuthTokens(User user) {
        final String accessToken = jwtRepository.generateAccessToken(user);
        final String refreshToken = jwtRepository.generateRefreshToken(user);
        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
