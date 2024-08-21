package com.nagornov.multimicroserviceproject.userprofileservice.controller;

import com.nagornov.multimicroserviceproject.userprofileservice.dto.session.SessionRequest;
import com.nagornov.multimicroserviceproject.userprofileservice.model.Session;
import com.nagornov.multimicroserviceproject.userprofileservice.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/api/session")
    public ResponseEntity<?> getSession(@RequestParam String refreshToken) {
        try {
            Session session = sessionService.getSession(refreshToken);
            return ResponseEntity.status(HttpStatus.OK).body(session);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sessions getting error");
        }
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

}
