package com.nagornov.multimicroserviceproject.userprofileservice.service;

import com.nagornov.multimicroserviceproject.userprofileservice.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionConsumerService {

    private final SessionService sessionService;

    public void distributor(String session, String operation) {
        Session s = Session.fromString(session);

         switch (operation) {
            case "logout" -> logout(s);
        };
    }

    private void logout(Session s) {
        sessionService.deleteSession(s);
    }

}
