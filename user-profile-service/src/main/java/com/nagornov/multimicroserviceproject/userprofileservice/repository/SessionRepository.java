package com.nagornov.multimicroserviceproject.userprofileservice.repository;

import com.nagornov.multimicroserviceproject.userprofileservice.dto.session.SessionRequest;
import com.nagornov.multimicroserviceproject.userprofileservice.model.Session;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class SessionRepository {

    @Value("${feign-client.auth.url}")
    private String authUrl;

    public ResponseEntity<Session> getSession(HttpServletRequest servletRequest, String serviceName, String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Cookie", servletRequest.getHeader("Cookie"));
        headers.add("Refresh-Token", refreshToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = UriComponentsBuilder.fromHttpUrl(authUrl)
                .path("/api/session")
                .queryParam("serviceName", serviceName)
                .toUriString();

        return restTemplate.exchange(url, HttpMethod.GET, entity, Session.class);
    }

    public ResponseEntity<Session> updateSession(HttpServletRequest servletRequest, SessionRequest request, String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Cookie", servletRequest.getHeader("Cookie"));
        headers.add("Refresh-Token", refreshToken);

        HttpEntity<SessionRequest> entity = new HttpEntity<>(request, headers);

        String url = UriComponentsBuilder.fromHttpUrl(authUrl)
                .path("/api/session/update")
                .toUriString();

        return restTemplate.exchange(url, HttpMethod.POST, entity, Session.class);
    }

}
