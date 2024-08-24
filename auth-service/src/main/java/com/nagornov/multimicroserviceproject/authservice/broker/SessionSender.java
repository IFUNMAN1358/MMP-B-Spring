package com.nagornov.multimicroserviceproject.authservice.broker;

import com.nagornov.multimicroserviceproject.authservice.config.properties.RabbitProperties;
import com.nagornov.multimicroserviceproject.authservice.util.CustomLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionSender {

    private final RabbitTemplate sessionRabbitTemplate;
    private final RabbitProperties rabbitProperties;

    public void send(String message) {
        try {
            sessionRabbitTemplate.convertAndSend(rabbitProperties.getSessionRequestQueue(), message);
        } catch (Exception e) {
            CustomLogger.error("Error sending session request: " + e.getMessage());
        }
    }

}
