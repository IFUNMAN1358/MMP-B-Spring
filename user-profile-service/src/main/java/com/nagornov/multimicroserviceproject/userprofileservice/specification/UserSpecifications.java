package com.nagornov.multimicroserviceproject.userprofileservice.specification;

import com.nagornov.multimicroserviceproject.userprofileservice.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class UserSpecifications {

    public static Specification<User> hasUserId(UUID userId) {
        return (root, query, criteriaBuilder) ->
                userId == null ? null :
                        criteriaBuilder.equal(root.get("userId"), userId);
    }

    public static Specification<User> hasUsername(String username) {
        return (root, query, criteriaBuilder) ->
                username == null ? null :
                        criteriaBuilder.equal(root.get("username"), username);
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                email == null ? null :
                        criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<User> hasPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) ->
                phoneNumber == null ? null :
                        criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber);
    }

}
