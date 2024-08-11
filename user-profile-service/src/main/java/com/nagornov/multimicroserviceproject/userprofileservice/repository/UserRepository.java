package com.nagornov.multimicroserviceproject.userprofileservice.repository;

import com.nagornov.multimicroserviceproject.userprofileservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsUserByUserId(UUID userId);
    boolean existsUserByUsername(String username);
    boolean existsUserByEmail(String email);
    boolean existsUserByPhoneNumber(String phoneNumber);

    Optional<User> findUserByUserId(UUID userId);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhoneNumber(String phoneNumber);


}
