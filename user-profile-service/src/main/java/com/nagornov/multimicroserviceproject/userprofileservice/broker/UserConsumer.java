package com.nagornov.multimicroserviceproject.userprofileservice.broker;

import com.nagornov.multimicroserviceproject.userprofileservice.config.properties.RabbitProperties;
import com.nagornov.multimicroserviceproject.userprofileservice.dto.rabbit.UserMessage;
import com.nagornov.multimicroserviceproject.userprofileservice.model.User;
import com.nagornov.multimicroserviceproject.userprofileservice.service.UserConsumerService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserConsumer {

    private final UserConsumerService userConsumerService;
    private final RabbitProperties rabbitProperties;

    @RabbitListener(queues = "#{rabbitProperties.getUserRequestQueue()}")
    @SendTo("#{rabbitProperties.getUserResponseQueue()}")
    public String receiveMessage(String message) {

        UserMessage userMessage = UserMessage.fromString(message);
        User user = userConsumerService.distributor(userMessage);

        return user.toString();
    }
}
