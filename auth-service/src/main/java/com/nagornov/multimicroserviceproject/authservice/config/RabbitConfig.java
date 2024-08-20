package com.nagornov.multimicroserviceproject.authservice.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${spring.cloud.stream.rabbit.binder.addresses}")
    private String rabbitAddress;

    @Value("${rabbit.session.queue}")
    private String sessionQueue;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public Queue sessionQueue() {
        return new Queue(sessionQueue, true);
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        String[] addressParts = rabbitAddress.split(":");
        String host = addressParts[0];
        int port = addressParts.length > 1 ? Integer.parseInt(addressParts[1]) : 5672;

        CachingConnectionFactory factory = new CachingConnectionFactory(host);
        factory.setPort(port);

        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

}
