package com.nagornov.multimicroserviceproject.userprofileservice.service;

import com.nagornov.multimicroserviceproject.userprofileservice.client.AuthClient;
import com.nagornov.multimicroserviceproject.userprofileservice.dto.session.SessionRequest;
import com.nagornov.multimicroserviceproject.userprofileservice.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final AuthClient authClient;
    private final SimpMessagingTemplate messagingTemplate;

    public Session getSession(String refreshToken) {
        ResponseEntity<Session> response = authClient.getSession(refreshToken);
        return response.getBody();
    }

    public Session updateSession(SessionRequest req) {
        ResponseEntity<Session> response = authClient.updateSession(req);
        return response.getBody();
    }

    public Boolean hasByAccessToken(String accessToken) {
        ResponseEntity<Boolean> response = authClient.hasByAccessToken(accessToken);
        return response.getBody();
    }

    public void deleteSession(Session s) {
        messagingTemplate.convertAndSend("/session", s);
    }

}
