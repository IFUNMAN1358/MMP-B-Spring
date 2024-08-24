package com.nagornov.multimicroserviceproject.authservice.broker;

import com.nagornov.multimicroserviceproject.authservice.util.CustomLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSender {

    private final RabbitTemplate userRabbitTemplate;

    public String send(String message) {
        try {
            return (String) userRabbitTemplate.convertSendAndReceive(message);
        } catch (Exception e) {
            CustomLogger.error("Error sending user message: " + e.getMessage());
        }
        return "";
    }

}
