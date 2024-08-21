package com.nagornov.multimicroserviceproject.userprofileservice.client;

import com.nagornov.multimicroserviceproject.userprofileservice.dto.session.SessionRequest;
import com.nagornov.multimicroserviceproject.userprofileservice.model.Session;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@FeignClient(name = "auth-service", url = "http://localhost:8000")
public interface AuthClient {

    @GetMapping("/api/session")
    ResponseEntity<Session> getSession(@RequestParam String refreshToken);

    @PostMapping("/api/session/update")
    ResponseEntity<Session> updateSession(@RequestBody SessionRequest req);

    @GetMapping("/api/session/has-by-access-token")
    ResponseEntity<Boolean> hasByAccessToken(@RequestParam String accessToken);

}
