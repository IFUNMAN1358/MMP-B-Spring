package com.nagornov.multimicroserviceproject.userprofileservice.controller;

import com.nagornov.multimicroserviceproject.userprofileservice.config.security.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.userprofileservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JwtService jwtService;

    @PostMapping("/api/test")
    public ResponseEntity<?> test() {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        return ResponseEntity.status(HttpStatus.OK).body(authInfo);
    }

}
