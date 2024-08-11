package com.nagornov.multimicroserviceproject.userprofileservice.config.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.nagornov.multimicroserviceproject.userprofileservice.model.User;

import java.io.IOException;
import java.util.UUID;

public class UserDeserializer extends JsonDeserializer<User> {

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        User user = new User();

        if (node.has("userId")) {
            JsonNode userIdNode = node.get("userId");
            if (!userIdNode.isNull()) {
                user.setUserId(UUID.fromString(userIdNode.asText()));
            }
        }

        if (node.has("username")) {
            JsonNode usernameNode = node.get("username");
            if (!usernameNode.isNull()) {
                user.setUsername(usernameNode.asText());
            }
        }

        if (node.has("phoneNumber")) {
            JsonNode phoneNumberNode = node.get("phoneNumber");
            if (!phoneNumberNode.isNull()) {
                user.setPhoneNumber(phoneNumberNode.asText());
            }
        }

        if (node.has("email")) {
            JsonNode emailNode = node.get("email");
            if (!emailNode.isNull()) {
                user.setEmail(emailNode.asText());
            }
        }

        if (node.has("password")) {
            JsonNode passwordNode = node.get("password");
            if (!passwordNode.isNull()) {
                user.setPassword(passwordNode.asText());
            }
        }

        if (node.has("firstName")) {
            JsonNode firstNameNode = node.get("firstName");
            if (!firstNameNode.isNull()) {
                user.setFirstName(firstNameNode.asText());
            }
        }

        if (node.has("lastName")) {
            JsonNode lastNameNode = node.get("lastName");
            if (!lastNameNode.isNull()) {
                user.setLastName(lastNameNode.asText());
            }
        }

        return user;
    }
}
