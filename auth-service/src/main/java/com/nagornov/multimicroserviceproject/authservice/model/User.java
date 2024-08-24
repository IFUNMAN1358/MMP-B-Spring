package com.nagornov.multimicroserviceproject.authservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nagornov.multimicroserviceproject.authservice.config.security.GrantedAuthorityDeserializer;
import com.nagornov.multimicroserviceproject.authservice.util.JsonUtils;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private UUID userId;
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    private String verificationCode;

    @JsonDeserialize(contentUsing = GrantedAuthorityDeserializer.class)
    private Set<Role> roles = Set.of(
        new Role("ROLE_UNREGISTERED")
    );

    public Map<Object, Object> toMap() {
        Map<Object, Object> map = new HashMap<>();
        map.put("userId", this.userId);
        map.put("firstName", this.firstName);
        map.put("lastName", this.lastName);
        map.put("phoneNumber", this.phoneNumber);
        map.put("email", this.email);
        map.put("username", this.username);
        map.put("password", this.password);
        map.put("verificationCode", this.verificationCode);
        map.put("roles", this.roles);
        return map;
    }

    public static User fromMap(Map<Object, Object> map) {
        User u = new User();
        u.setUserId((UUID) map.get("userId"));
        u.setFirstName((String) map.get("firstName"));
        u.setLastName((String) map.get("lastName"));
        u.setPhoneNumber((String) map.get("phoneNumber"));
        u.setEmail((String) map.get("email"));
        u.setUsername((String) map.get("username"));
        u.setPassword((String) map.get("password"));
        u.setVerificationCode((String) map.get("verificationCode"));
        u.setRoles((Set<Role>) map.get("roles"));
        return u;
    }

    @Override
    public String toString() {
        return JsonUtils.toJsonString(this);
    }

    public static User fromString(String str) {
        return JsonUtils.fromJsonString(str, User.class);
    }
}
