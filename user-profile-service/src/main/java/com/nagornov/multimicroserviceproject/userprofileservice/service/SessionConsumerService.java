package com.nagornov.multimicroserviceproject.userprofileservice.service;

import com.nagornov.multimicroserviceproject.userprofileservice.dto.rabbit.SessionMessage;
import com.nagornov.multimicroserviceproject.userprofileservice.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionConsumerService {

    private final SessionService sessionService;

    public void distributor(SessionMessage message) {

         switch (message.getOperation()) {
            case "logout" -> logout(message.getSession());
        };
    }

    private void logout(Session session) {
        sessionService.deleteSession(session);
    }

}
