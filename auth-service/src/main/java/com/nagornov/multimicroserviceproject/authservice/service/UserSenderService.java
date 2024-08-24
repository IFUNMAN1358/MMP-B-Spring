package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.broker.UserSender;
import com.nagornov.multimicroserviceproject.authservice.dto.rabbit.UserMessage;
import com.nagornov.multimicroserviceproject.authservice.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserSenderService {

    private final UserSender userSender;

    public Optional<User> getUser(User user) {
        return processUser("getUser", user);
    }

    public Optional<User> createUser(User user) {
        return processUser("createUser",  user);
    }

    private Optional<User> processUser(String operation, User user) {
        try {
            UserMessage message = new UserMessage(operation, user);

            String stringUser = userSender.send(message.toString());

            return Optional.ofNullable(User.fromString(stringUser));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
