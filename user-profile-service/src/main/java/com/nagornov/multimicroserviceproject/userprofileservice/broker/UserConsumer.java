package com.nagornov.multimicroserviceproject.userprofileservice.broker;

import com.nagornov.multimicroserviceproject.userprofileservice.model.User;
import com.nagornov.multimicroserviceproject.userprofileservice.service.UserConsumerService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class UserConsumer {

    private final UserConsumerService userConsumerService;

    @Bean
    public Function<Message<String>, Message<String>> processUserRequest() {
        return message -> {
            String payload = message.getPayload();
            String[] parts = payload.split(":", 3);

            String messageId = parts[0];
            String operation = parts[1];
            String user = parts[2];

            User u = userConsumerService.distributor(user, operation);

            String response = "%s:%s:%s".formatted(messageId, operation, u);
            return MessageBuilder.withPayload(response).build();
        };
    }
}
