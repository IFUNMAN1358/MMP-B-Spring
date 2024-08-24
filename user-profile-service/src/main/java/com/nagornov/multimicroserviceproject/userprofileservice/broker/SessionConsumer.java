package com.nagornov.multimicroserviceproject.userprofileservice.broker;

import com.nagornov.multimicroserviceproject.userprofileservice.config.properties.RabbitProperties;
import com.nagornov.multimicroserviceproject.userprofileservice.dto.rabbit.SessionMessage;
import com.nagornov.multimicroserviceproject.userprofileservice.service.SessionConsumerService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class SessionConsumer {

    private final SessionConsumerService sessionConsumerService;
    private final RabbitProperties rabbitProperties;

    @Value("${service.name}")
    private String service;

    @RabbitListener(queues = "#{rabbitProperties.getSessionRequestQueue()}")
    public void receiveMessage(Message message, Channel channel) throws Exception {

        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        SessionMessage sessionMessage = SessionMessage.fromString(messageBody);

        if (this.service.equals(sessionMessage.getService())) {
            sessionConsumerService.distributor(sessionMessage);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

}
