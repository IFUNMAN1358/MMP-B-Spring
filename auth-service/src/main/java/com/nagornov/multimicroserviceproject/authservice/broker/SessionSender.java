package com.nagornov.multimicroserviceproject.authservice.broker;

import com.nagornov.multimicroserviceproject.authservice.model.Session;
import com.nagornov.multimicroserviceproject.authservice.util.CustomLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbit.session.queue}")
    private String sessionQueue;

    public void sendSessionRequest(String service, String operation, Session session) {

        String request = "%s:%s:%s".formatted(service, operation, session);

        try {
            rabbitTemplate.convertAndSend(sessionQueue, request);
        } catch (Exception e) {
            CustomLogger.error("Error sending session request: " + e.getMessage());
        }
    }

}
