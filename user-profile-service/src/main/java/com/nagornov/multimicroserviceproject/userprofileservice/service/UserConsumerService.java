package com.nagornov.multimicroserviceproject.userprofileservice.service;

import com.nagornov.multimicroserviceproject.userprofileservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConsumerService {

    private final UserService userService;

    public User distributor(String user, String operation) {
        User u = User.fromString(user);

        return switch (operation) {
            case "getUser" -> getUser(u);
            case "createUser" -> createUser(u);
            default -> null;
        };
    }

    private User getUser(User user) {
        try {
            return userService.getUser(user);
        } catch (Exception e) {
            return null;
        }
    }

    private User createUser(User user) {
        try {
            userService.createUser(user);
            return userService.getUser(user);
        } catch (Exception e) {
            return null;
        }
    }

}
