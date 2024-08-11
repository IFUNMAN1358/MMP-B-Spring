package com.nagornov.multimicroserviceproject.userprofileservice.service;

import com.nagornov.multimicroserviceproject.userprofileservice.model.Role;
import com.nagornov.multimicroserviceproject.userprofileservice.model.User;
import com.nagornov.multimicroserviceproject.userprofileservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public Optional<User> findUser(User user) {
        if (user.getUserId() != null && !user.getUsername().isEmpty()) {
            Optional<User> searchUser = userRepository.findUserByUserId(user.getUserId());
            if (searchUser.isPresent()) { return searchUser; }
        }
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            Optional<User> searchUser = userRepository.findUserByUsername(user.getUsername());
            if (searchUser.isPresent()) { return searchUser; }
        }
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            Optional<User> searchUser = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
            if (searchUser.isPresent()) { return searchUser; }
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            Optional<User> searchUser = userRepository.findUserByEmail(user.getEmail());
            if (searchUser.isPresent()) { return searchUser; }
        }
        return Optional.empty();
    }

    public User getUser(User user) {
        if (user.getUserId() != null && !String.valueOf(user.getUserId()).isEmpty()) {
            Optional<User> searchUser = userRepository.findUserByUserId(user.getUserId());
            if (searchUser.isPresent()) { return searchUser.get(); }
        }
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            Optional<User> searchUser = userRepository.findUserByUsername(user.getUsername());
            if (searchUser.isPresent()) { return searchUser.get(); }
        }
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            Optional<User> searchUser = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
            if (searchUser.isPresent()) { return searchUser.get(); }
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            Optional<User> searchUser = userRepository.findUserByEmail(user.getEmail());
            if (searchUser.isPresent()) { return searchUser.get(); }

        }
        return null;
    }

    @Transactional
    public void createUser(User user) {
        Role userRole = roleService.getRoleByName("ROLE_USER");

        Set<Role> userRoles = user.getRoles();
        userRoles.add(userRole);

        user.setRoles(userRoles);
        user.setLastLogin();

        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
            user.setIsPhoneVerified(true);
        } else if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            user.setIsEmailVerified(true);
        }
        userRepository.save(user);
    }

}
