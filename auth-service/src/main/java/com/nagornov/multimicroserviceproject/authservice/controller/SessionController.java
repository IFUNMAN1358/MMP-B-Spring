package com.nagornov.multimicroserviceproject.authservice.controller;

import com.nagornov.multimicroserviceproject.authservice.dto.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.authservice.dto.session.DeleteSessionRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.session.SessionRequest;
import com.nagornov.multimicroserviceproject.authservice.exception.session.IncorrectServiceNameException;
import com.nagornov.multimicroserviceproject.authservice.exception.session.SessionNotFoundException;
import com.nagornov.multimicroserviceproject.authservice.exception.user.UserNotFoundException;
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
        try {
            JwtAuthentication authInfo = jwtService.getAuthInfo();
            sessionService.createSession(authInfo.getUserId(), request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Session has been created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating session");
        }
    }

    @GetMapping("/api/session")
    public ResponseEntity<?> getSession(@RequestParam String serviceName) throws Exception {
        try {
            JwtAuthentication authInfo = jwtService.getAuthInfo();
            Session session = sessionService.getSession(authInfo.getUserId(), serviceName);
            return ResponseEntity.status(HttpStatus.OK).body(session);
        } catch (IncorrectServiceNameException e) {
            throw new Exception("Incorrect service name");
        } catch (SessionNotFoundException e) {
            throw new Exception("Session not found");
        } catch (Exception e) {
            throw new Exception("Get session failed");
        }
    }

    @GetMapping("/api/sessions")
    public ResponseEntity<?> getSessions() {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        List<Session> sessions = sessionService.getSessions(authInfo.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(sessions);
    }

    @PostMapping("/api/session/update")
    public ResponseEntity<?> updateSession(@RequestBody SessionRequest request) {
        try {
            JwtAuthentication authInfo = jwtService.getAuthInfo();
            Session session = sessionService.updateSession(authInfo.getUserId(), request);
            return ResponseEntity.status(HttpStatus.CREATED).body(session);
        } catch (SessionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Session not found");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not found");
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
