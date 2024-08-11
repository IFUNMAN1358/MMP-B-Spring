package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.config.feign.UserProfileClient;
import com.nagornov.multimicroserviceproject.authservice.dto.user.request.UserRegisterDataRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.user.request.UserRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.user.response.UserResponse;
import com.nagornov.multimicroserviceproject.authservice.mapper.UserMapper;
import com.nagornov.multimicroserviceproject.authservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileClient userProfileClient;
    private final UserMapper userMapper;

    public Optional<UserResponse> findUser(User user) {
        UserRequest req = new UserRequest(user);
        ResponseEntity<Optional<UserResponse>> response = userProfileClient.findUser(req);

        if (Objects.requireNonNull(response.getBody()).isPresent()) {
            return response.getBody();
        }

        return Optional.empty();
    }

    public UserResponse getUser(User user) {
        UserRequest req = new UserRequest(user);
        ResponseEntity<UserResponse> response = userProfileClient.getUser(req);
        return response.getBody();
    }

    public UserResponse verifyUser(User user) {
        UserRequest req = new UserRequest(user);
        ResponseEntity<UserResponse> response = userProfileClient.verifyUser(req);
        return response.getBody();
    }

    public UserResponse createUser(User user) {
        UserRegisterDataRequest req = userMapper.toUserRegisterDataRequest(user);
        ResponseEntity<UserResponse> response = userProfileClient.createUser(req);
        return response.getBody();
    }

}
