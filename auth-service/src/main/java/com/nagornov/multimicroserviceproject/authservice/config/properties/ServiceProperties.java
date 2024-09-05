package com.nagornov.multimicroserviceproject.authservice.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServiceProperties {

    @Value("${service.name}")
    private String serviceName;

    @Value("${service.back-url}")
    private String serviceBackUrl;

    @Value("${service.front-url}")
    private String serviceFrontUrl;

}
