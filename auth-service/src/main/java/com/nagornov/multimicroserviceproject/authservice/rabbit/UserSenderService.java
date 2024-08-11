package com.nagornov.multimicroserviceproject.authservice.rabbit;

import com.nagornov.multimicroserviceproject.authservice.broker.UserSender;
import com.nagornov.multimicroserviceproject.authservice.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSenderService {

    private final UserSender userSender;

    public Optional<User> getUser(User user) {
        return processUser(user, "getUser");
    }

    public Optional<User> createUser(User user) {
        return processUser(user, "createUser");
    }

    private Optional<User> processUser(User user, String operation) {
        try {
            String response = userSender.sendUserRequest(user, operation);
            return Optional.ofNullable(User.fromString(response));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
