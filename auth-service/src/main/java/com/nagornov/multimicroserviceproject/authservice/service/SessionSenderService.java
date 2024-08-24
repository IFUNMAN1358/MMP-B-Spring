package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.broker.SessionSender;
import com.nagornov.multimicroserviceproject.authservice.dto.rabbit.SessionMessage;
import com.nagornov.multimicroserviceproject.authservice.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionSenderService {

    private final SessionSender sessionSender;

    public void logout(String service, Session session) {
        processSession(service, "logout", session);
    }

    private void processSession(String service, String operation, Session session) {
        SessionMessage message = new SessionMessage(service, operation, session);
        sessionSender.send(message.toString());
    }

}
