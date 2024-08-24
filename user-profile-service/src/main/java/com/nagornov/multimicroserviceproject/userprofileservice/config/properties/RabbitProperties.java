package com.nagornov.multimicroserviceproject.userprofileservice.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RabbitProperties {

    //
    // General
    //

    @Value("${spring.rabbitmq.stream.host}")
    private String host;

    @Value("${spring.rabbitmq.stream.port}")
    private Integer port;

    @Value("${spring.rabbitmq.stream.username}")
    private String username;

    @Value("${spring.rabbitmq.stream.password}")
    private String password;

    @Value("${spring.rabbitmq.connection-timeout}")
    private Integer connectionTimeout;

    @Value("${spring.rabbitmq.requested-heartbeat}")
    private Integer requestedHeartbeat;

    //
    // Template
    //

    @Value("${spring.rabbitmq.template.reply-timeout}")
    private Integer templateReplyTimeout;

    @Value("${spring.rabbitmq.template.retry.max-attempts}")
    private Integer templateMaxAttempts;

    @Value("${spring.rabbitmq.template.retry.initial-interval}")
    private Integer templateInitialInterval;

    @Value("${spring.rabbitmq.template.retry.max-interval}")
    private Integer templateMaxInterval;

    //
    // Listener
    //

    @Value("${spring.rabbitmq.listener.direct.retry.max-attempts}")
    private Integer directMaxAttempts;

    @Value("${spring.rabbitmq.listener.direct.retry.initial-interval}")
    private Integer directInitialInterval;

    @Value("${spring.rabbitmq.listener.direct.retry.max-interval}")
    private Integer directMaxInterval;

    //
    // Queue
    //

    // User

    @Value("${spring.rabbitmq.user.request.queue}")
    private String userRequestQueue;

    @Value("${spring.rabbitmq.user.response.queue}")
    private String userResponseQueue;

    @Value("${spring.rabbitmq.user.exchange}")
    private String userExchange;

    @Value("${spring.rabbitmq.user.request.routing-key}")
    private String userRequestRoutingKey;

    @Value("${spring.rabbitmq.user.response.routing-key}")
    private String userResponseRoutingKey;

    // Session

    @Value("${spring.rabbitmq.session.request.queue}")
    private String sessionRequestQueue;
}
