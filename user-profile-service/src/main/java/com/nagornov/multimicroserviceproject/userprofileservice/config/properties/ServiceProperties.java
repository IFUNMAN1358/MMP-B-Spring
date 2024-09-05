package com.nagornov.multimicroserviceproject.userprofileservice.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
