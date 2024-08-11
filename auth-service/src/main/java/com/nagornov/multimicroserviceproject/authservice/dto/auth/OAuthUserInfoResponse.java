package com.nagornov.multimicroserviceproject.authservice.dto.auth;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OAuthUserInfoResponse {

    private String id;
    private String login;
    private String displayName;
    private String name;
    private String firstName;
    private String lastName;
    private String sex;
    private String birthday;
    private String avatar;
    private String locale;
    private String email;
    private List<String> emails;
    private String phoneId;
    private String phoneNumber;
    private String psuid;
    private Boolean isAvatarEmpty;
    private Boolean emailVerified;

    public static OAuthUserInfoResponse fromString(String object) throws JsonProcessingException {
        OAuthUserInfoResponse res = new OAuthUserInfoResponse();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(object);

        if (json.has("sub")) {
            res.setId(json.get("sub").asText());
        }
        if (json.has("id")) {
            res.setId(json.get("id").asText());
        }

        if (json.has("login")) {
            res.setLogin(json.get("login").asText());
        }
        if (json.has("display_name")) {
            res.setDisplayName(json.get("display_name").asText());
        }

        if (json.has("name")) {
            res.setName(json.get("name").asText());
            String[] nameParts = json.get("name").asText().split(" ");
            if (nameParts.length == 1) {
                res.setFirstName(nameParts[0]);
            } else if (nameParts.length > 1) {
                res.setFirstName(nameParts[0]);
                res.setLastName(nameParts[nameParts.length - 1]);
            }
        }

        if (json.has("real_name")) {
            res.setName(json.get("real_name").asText());
            String[] nameParts = json.get("real_name").asText().split(" ");
            if (nameParts.length == 1) {
                res.setFirstName(nameParts[0]);
            } else if (nameParts.length > 1) {
                res.setFirstName(nameParts[0]);
                res.setLastName(nameParts[nameParts.length - 1]);
            }
        }

        if (json.has("given_name")) {
            res.setFirstName(json.get("given_name").asText());
        }
        if (json.has("first_name")) {
            res.setFirstName(json.get("first_name").asText());
        }

        if (json.has("family_name")) {
            res.setLastName(json.get("family_name").asText());
        }
        if (json.has("last_name")) {
            res.setLastName(json.get("last_name").asText());
        }

        if (json.has("sex")) {
            res.setSex(json.get("sex").asText());
        }
        if (json.has("birthday")) {
            res.setBirthday(json.get("birthday").asText());
        }

        if (json.has("picture")) {
            res.setAvatar(json.get("picture").asText());
        }
        if (json.has("default_avatar_id")) {
            res.setAvatar(json.get("default_avatar_id").asText());
        }

        if (json.has("locale")) {
            res.setLocale(json.get("locale").asText());
        }

        if (json.has("email")) {
            res.setEmail(json.get("email").asText());
        }
        if (json.has("default_email")) {
            res.setEmail(json.get("default_email").asText());
        }
        if (json.has("emails")) {
            JsonNode emailsNode = json.get("emails");
            if (emailsNode.isArray()) {
                List<String> emails = new ArrayList<>();
                for (JsonNode emailNode : emailsNode) {
                    emails.add(emailNode.asText());
                }
                res.setEmails(emails);
            }
        }

        if (json.has("default_phone")) {
            JsonNode phoneNode = json.get("default_phone");
            if (phoneNode.has("id")) {
                res.setPhoneId(phoneNode.get("id").asText());
            }
            if (phoneNode.has("number")) {
                res.setPhoneNumber(phoneNode.get("number").asText());
            }
        }

        if (json.has("psuid")) {
            res.setPsuid(json.get("psuid").asText());
        }

        if (json.has("email_verified")) {
            res.setEmailVerified(json.get("email_verified").asBoolean());
        }
        if (json.has("is_avatar_empty")) {
            res.setIsAvatarEmpty(json.get("is_avatar_empty").asBoolean());
        }

        return res;
    }
}
