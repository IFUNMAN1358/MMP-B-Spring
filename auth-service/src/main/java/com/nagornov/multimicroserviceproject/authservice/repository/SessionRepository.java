package com.nagornov.multimicroserviceproject.authservice.repository;

import com.nagornov.multimicroserviceproject.authservice.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Boolean existsByServiceAndUserIdAndDevice(String service, UUID userId, String device);
    Boolean existsByAccessToken(String token);

    Session getSessionByServiceAndUserIdAndDevice(String service, UUID userId, String device);
    Session getSessionByUserIdAndRefreshToken(UUID userId, String token);
    Session getSessionBySessionIdAndUserId(Long sessionId, UUID userId);

    Session getSessionByRefreshToken(String refreshToken);
    List<Session> getSessionsByUserId(UUID userId);

    void deleteSessionByRefreshToken(String token);
}
