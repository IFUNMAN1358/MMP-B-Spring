package com.nagornov.multimicroserviceproject.userprofileservice.broker;

import com.nagornov.multimicroserviceproject.userprofileservice.service.SessionConsumerService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionConsumer {

    private final SessionConsumerService sessionConsumerService;

    @Value("${service.name}")
    private String service;

    @RabbitListener(queues = "${rabbit.session.queue}")
    public void processSessionRequest(Message message, Channel channel) throws Exception {

        String payload = new String(message.getBody());
        String[] parts = payload.split(":", 3);

        String service = parts[0];
        String operation = parts[1];
        String session = parts[2];

        if (this.service.equals(service)) {
            sessionConsumerService.distributor(session, operation);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

}
