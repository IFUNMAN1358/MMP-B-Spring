package com.nagornov.multimicroserviceproject.userprofileservice.service;

import com.nagornov.multimicroserviceproject.userprofileservice.dto.session.SessionRequest;
import com.nagornov.multimicroserviceproject.userprofileservice.model.Session;
import com.nagornov.multimicroserviceproject.userprofileservice.repository.SessionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SimpMessagingTemplate messagingTemplate;
    private final SessionRepository sessionRepository;

    public Session getSession(HttpServletRequest servletRequest, String serviceName, String refreshToken) {
        ResponseEntity<Session> response = sessionRepository.getSession(servletRequest, serviceName, refreshToken);
        return response.getBody();
    }

    public Session updateSession(HttpServletRequest servletRequest, SessionRequest request, String refreshToken) {
        ResponseEntity<Session> response = sessionRepository.updateSession(servletRequest, request, refreshToken);
        return response.getBody();
    }

    public void deleteSession(Session s) {
        messagingTemplate.convertAndSend("/session", s);
    }

}
