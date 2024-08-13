package com.nagornov.multimicroserviceproject.authservice.controller;

import com.nagornov.multimicroserviceproject.authservice.dto.auth.AuthResponse;
import com.nagornov.multimicroserviceproject.authservice.dto.auth.LoginFormRequest;
import com.nagornov.multimicroserviceproject.authservice.exception.user.UserNotFoundException;
import com.nagornov.multimicroserviceproject.authservice.exception.validation.IncorrectPasswordException;
import com.nagornov.multimicroserviceproject.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginFormRequest request) {
        try {
            AuthResponse data = userService.login(request);
            return ResponseEntity.status(HttpStatus.OK).body(data);

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not found");
        } catch (IncorrectPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password mismatch");
        }
    }

}
