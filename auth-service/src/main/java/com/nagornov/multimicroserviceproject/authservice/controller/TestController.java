package com.nagornov.multimicroserviceproject.authservice.controller;

import com.nagornov.multimicroserviceproject.authservice.dto.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JwtService jwtService;

    @GetMapping("/api/test")
    public ResponseEntity<?> test() {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

}
