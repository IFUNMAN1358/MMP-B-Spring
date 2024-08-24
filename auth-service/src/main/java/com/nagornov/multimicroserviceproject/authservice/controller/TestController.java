package com.nagornov.multimicroserviceproject.authservice.controller;

import com.nagornov.multimicroserviceproject.authservice.config.security.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JwtService jwtService;

    @GetMapping("/api/test")
    public ResponseEntity<?> Test() {
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping("/api/access-test")
    public ResponseEntity<?> accessTest() {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        return ResponseEntity.status(HttpStatus.OK).body(authInfo);
    }

    @GetMapping("/api/refresh-test")
    public ResponseEntity<?> refreshTest() {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        return ResponseEntity.status(HttpStatus.OK).body(authInfo);
    }

}
