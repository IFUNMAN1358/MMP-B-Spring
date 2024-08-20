package com.nagornov.multimicroserviceproject.userprofileservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.nagornov.multimicroserviceproject.userprofileservice.client")
public class FeignConfig {
}
