package com.nagornov.multimicroserviceproject.authservice.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class ClientProperties {

    public List<String> clientsNameList() {
        return List.of(clientUserName);
    }

    public List<String> clientsBackUrlList() {
        return List.of(clientUserBackUrl);
    }

    // User

    @Value("${client.user.name}")
    private String clientUserName;

    @Value("${client.user.back-url}")
    private String clientUserBackUrl;

}
