package com.nagornov.multimicroserviceproject.userprofileservice.controller;

import com.nagornov.multimicroserviceproject.userprofileservice.dto.session.SessionRequest;
import com.nagornov.multimicroserviceproject.userprofileservice.model.Session;
import com.nagornov.multimicroserviceproject.userprofileservice.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/api/session")
    public ResponseEntity<?> getSession(HttpServletRequest servletRequest,
                                        @RequestParam("serviceName") String serviceName,
                                        @RequestHeader("Refresh-Token") String refreshToken) {
        try {
            Session session = sessionService.getSession(servletRequest, serviceName, refreshToken);
            return ResponseEntity.status(HttpStatus.OK).body(session);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Session getting error");
        }
    }

    @PostMapping("/api/session/update")
    public ResponseEntity<?> updateSession(HttpServletRequest servletRequest,
                                           @RequestBody SessionRequest request,
                                           @RequestHeader("Refresh-Token") String refreshToken) {
        try {
            Session session = sessionService.updateSession(servletRequest, request, refreshToken);
            return ResponseEntity.status(HttpStatus.CREATED).body(session);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating session");
        }
    }

}
