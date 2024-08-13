package com.nagornov.multimicroserviceproject.userprofileservice.service;

import com.nagornov.multimicroserviceproject.userprofileservice.model.Role;
import com.nagornov.multimicroserviceproject.userprofileservice.model.User;
import com.nagornov.multimicroserviceproject.userprofileservice.repository.UserRepository;
import com.nagornov.multimicroserviceproject.userprofileservice.specification.UserSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public User getUser(User user) {
        Specification<User> spec = Specification
                .where(UserSpecifications.hasUserId(user.getUserId()))
                .or(UserSpecifications.hasUsername(user.getUsername()))
                .or(UserSpecifications.hasEmail(user.getEmail()))
                .or(UserSpecifications.hasPhoneNumber(user.getPhoneNumber()));

        return userRepository.findOne(spec).orElse(null);
    }

    @Transactional
    public void createUser(User user) {
        Role userRole = roleService.getRoleByName("ROLE_USER");

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        user.setRoles(userRoles);
        user.setLastLogin();

        userRepository.save(user);
    }

}
