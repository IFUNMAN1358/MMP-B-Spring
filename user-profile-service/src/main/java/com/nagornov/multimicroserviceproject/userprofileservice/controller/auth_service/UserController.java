package com.nagornov.multimicroserviceproject.userprofileservice.controller.auth_service;

import com.nagornov.multimicroserviceproject.userprofileservice.dto.user.request.UserRequest;
import com.nagornov.multimicroserviceproject.userprofileservice.dto.user.request.UserRegisterDataRequest;
import com.nagornov.multimicroserviceproject.userprofileservice.mapper.UserMapper;
import com.nagornov.multimicroserviceproject.userprofileservice.model.User;
import com.nagornov.multimicroserviceproject.userprofileservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @PostMapping("/api/find-user")
    public ResponseEntity<?> findUser(@RequestBody UserRequest req) {
        Optional<User> user = userService.findUser(req.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toUserResponse(user.get()));
    }

    @PostMapping("/api/get-user")
    public ResponseEntity<?> getUser(@RequestBody UserRequest req) {
        User user = userService.getUser(req.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toUserResponse(user));
    }

    @PostMapping("/api/verify-user")
    public ResponseEntity<?> verifyUser(@RequestBody UserRequest req) {

        User user = userService.getUser(req.getUser());

        if (!passwordEncoder.matches(req.getUser().getPassword(), user.getPassword())) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password mismatch");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userMapper.toUserResponse(user));
    }

    @PostMapping("/api/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserRegisterDataRequest request) {

        try {
            User userFromRequest = userMapper.toUser(request);
            userService.createUser(userFromRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserResponse(userFromRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User already exists");
        }
    }

}
