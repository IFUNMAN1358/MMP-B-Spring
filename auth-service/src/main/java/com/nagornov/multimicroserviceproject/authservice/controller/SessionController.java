package com.nagornov.multimicroserviceproject.authservice.controller;

import com.nagornov.multimicroserviceproject.authservice.config.security.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.authservice.dto.session.DeleteSessionRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.session.SessionRequest;
import com.nagornov.multimicroserviceproject.authservice.model.Session;
import com.nagornov.multimicroserviceproject.authservice.service.JwtService;
import com.nagornov.multimicroserviceproject.authservice.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final JwtService jwtService;

    @PostMapping("/api/session")
    public ResponseEntity<?> createSession(@RequestBody SessionRequest request) {
        sessionService.createSession(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Session has been created");
    }

    @GetMapping("/api/session")
    public ResponseEntity<?> getSession(@RequestParam String refreshToken) {
        Session session = sessionService.getSession(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(session);
    }

    @GetMapping("/api/sessions")
    public ResponseEntity<?> getSessions() {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        List<Session> data = sessionService.getSessions(authInfo.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/api/session/has-by-access-token")
    public ResponseEntity<?> hasByAccessToken(@RequestParam String accessToken) {
        Boolean result = sessionService.hasByAccessToken(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/api/session/update")
    public ResponseEntity<?> updateSession(@RequestBody SessionRequest request) {
        try {
            Session session = sessionService.updateSession(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(session);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating session");
        }
    }

    @PostMapping("/api/session/delete")
    public ResponseEntity<?> deleteSession(@RequestBody DeleteSessionRequest request) {
        JwtAuthentication authInfo = jwtService.getAuthInfo();

        sessionService.deleteSession(request, authInfo.getUserId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
