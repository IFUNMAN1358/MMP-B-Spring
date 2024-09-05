package com.nagornov.multimicroserviceproject.authservice.repository;

import com.nagornov.multimicroserviceproject.authservice.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByServiceNameAndUserIdAndDeviceName(String service, UUID userId, String device);
    Optional<Session> findByUserIdAndServiceName(UUID userId, String serviceName);

    Boolean existsByAccessToken(String token);

    Session getSessionBySessionIdAndUserId(Long sessionId, UUID userId);

    Session getSessionByRefreshToken(String refreshToken);
    List<Session> getSessionsByUserId(UUID userId);

    void deleteSessionByRefreshToken(String token);
}
