package com.nagornov.multimicroserviceproject.authservice.controller;

import com.nagornov.multimicroserviceproject.authservice.model.User;
import com.nagornov.multimicroserviceproject.authservice.rabbit.UserSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserSenderService userSenderService;

    @PostMapping("/api/test")
    public ResponseEntity<?> test(@RequestParam String mail) {

        User user = new User();
        user.setEmail(mail);

        Optional<User> optU = userSenderService.getUser(user);
        if (optU.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not found");
        }
        return ResponseEntity.ok(optU.get());
    }

}
