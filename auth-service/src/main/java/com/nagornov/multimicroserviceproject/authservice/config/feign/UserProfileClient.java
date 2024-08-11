package com.nagornov.multimicroserviceproject.authservice.config.feign;

import com.nagornov.multimicroserviceproject.authservice.dto.user.request.UserRegisterDataRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.user.request.UserRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.user.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
@FeignClient(name = "user-profile-service", url = "http://localhost:8010")
public interface UserProfileClient {

    @PostMapping("/api/find-user")
    ResponseEntity<Optional<UserResponse>> findUser(@RequestBody UserRequest req);

    @PostMapping("/api/get-user")
    ResponseEntity<UserResponse> getUser(@RequestBody UserRequest req);

    @PostMapping("/api/verify-user")
    ResponseEntity<UserResponse> verifyUser(@RequestBody UserRequest req);

    @PostMapping("/api/create-user")
    ResponseEntity<UserResponse> createUser(@RequestBody UserRegisterDataRequest req);

}
