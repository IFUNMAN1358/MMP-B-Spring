package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.broker.SessionSender;
import com.nagornov.multimicroserviceproject.authservice.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionSenderService {

    private final SessionSender sessionSender;

    public void logout(String service, Session session) {
        sessionSender.sendSessionRequest(service, "logout", session);
    }

}
