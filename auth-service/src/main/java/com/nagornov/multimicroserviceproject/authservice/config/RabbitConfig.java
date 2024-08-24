package com.nagornov.multimicroserviceproject.authservice.config;

import com.nagornov.multimicroserviceproject.authservice.config.properties.RabbitProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    private final RabbitProperties rabbitProperties;

    //
    // General
    //

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.setConnectionTimeout(rabbitProperties.getConnectionTimeout());
        connectionFactory.setRequestedHeartBeat(rabbitProperties.getRequestedHeartbeat());
        return connectionFactory;
    }

    //
    // User
    //

    @Bean
    public Queue userRequestQueue() { return new Queue(rabbitProperties.getUserRequestQueue(), true); }

    @Bean
    public Queue userResponseQueue() { return new Queue(rabbitProperties.getUserResponseQueue(), true); }

    @Bean
    public TopicExchange userExchange() { return new TopicExchange(rabbitProperties.getUserExchange()); }

    @Bean
    public Binding userRequestBinding() {
        return BindingBuilder.bind(userRequestQueue()).to(userExchange()).with(rabbitProperties.getUserRequestRoutingKey());
    }

    @Bean
    public Binding userResponseBinding() {
        return BindingBuilder.bind(userResponseQueue()).to(userExchange()).with(rabbitProperties.getUserResponseRoutingKey());
    }

    @Bean
    @Qualifier("userRabbitTemplate")
    public RabbitTemplate userRabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setExchange(rabbitProperties.getUserExchange());
        template.setRoutingKey(rabbitProperties.getUserRequestRoutingKey());
        template.setReplyTimeout(rabbitProperties.getTemplateReplyTimeout());
        return template;
    }

    //
    // Session
    //

    @Bean
    public Queue sessionRequestQueue() {
        return new Queue(rabbitProperties.getSessionRequestQueue(), true);
    }

    @Bean
    @Qualifier("sessionRabbitTemplate")
    public RabbitTemplate sessionRabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate();
        template.setConnectionFactory(connectionFactory());
        return template;
    }

}
