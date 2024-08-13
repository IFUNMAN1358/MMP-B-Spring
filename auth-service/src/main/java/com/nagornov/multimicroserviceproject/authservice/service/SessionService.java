package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.dto.jwt.JwtResponse;
import com.nagornov.multimicroserviceproject.authservice.dto.session.DeleteSessionRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.session.UpdateSessionRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.session.CreateSessionRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.session.WsSessionDto;
import com.nagornov.multimicroserviceproject.authservice.mapper.SessionMapper;
import com.nagornov.multimicroserviceproject.authservice.model.Session;
import com.nagornov.multimicroserviceproject.authservice.model.User;
import com.nagornov.multimicroserviceproject.authservice.repository.JwtRepository;
import com.nagornov.multimicroserviceproject.authservice.repository.SessionRepository;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserSenderService userSenderService;
    private final SessionRepository sessionRepository;
    private final JwtRepository jwtRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final SessionMapper sessionMapper;

    @Transactional
    public void createSession(CreateSessionRequest req) {
        try {
            if (sessionRepository.existsByServiceAndUserIdAndDevice(req.getService(), req.getUserId(), req.getDevice())) {
                Session existingSession = sessionRepository.getSessionByServiceAndUserIdAndDevice(req.getService(), req.getUserId(), req.getDevice());
                sessionRepository.delete(existingSession);
            }
            Session session = sessionMapper.toSession(req);
            sessionRepository.save(session);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create session", e);
        }
    }

    @Transactional
    public Optional<JwtResponse> updateSession(UpdateSessionRequest req) {
        try {
            if (!jwtRepository.validateRefreshToken(req.getRefreshToken())) {
                sessionRepository.deleteSessionByRefreshToken(req.getRefreshToken());
                return Optional.empty();
            }

            Claims claims = jwtRepository.getRefreshClaims(req.getRefreshToken());
            String userId = claims.getSubject();

            Session session = sessionRepository.getSessionByUserIdAndRefreshToken(UUID.fromString(userId), req.getRefreshToken());
            if (session == null) {
                return Optional.empty();
            }

            User user = new User();
            user.setUserId(session.getUserId());
            Optional<User> userFromService = userSenderService.getUser(user);

            String accessToken = jwtRepository.generateAccessToken(userFromService.get());
            session.setAccessToken(accessToken);
            sessionRepository.save(session);

            return Optional.of(new JwtResponse(accessToken, req.getRefreshToken()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to update session", e);
        }
    }

    public List<Session> getSessions(String service, String userId) {
        return sessionRepository.getSessionsByServiceAndUserId(service, UUID.fromString(userId));
    }

    @Transactional
    public void deleteSession(DeleteSessionRequest req, String userId) {
        Session session = sessionRepository.getSessionBySessionIdAndUserId(req.getSessionId(), UUID.fromString(userId));

        WsSessionDto sessionInfo = new WsSessionDto();
        sessionInfo.setRefreshToken(session.getRefreshToken());

        messagingTemplate.convertAndSend("/session", sessionInfo);
        sessionRepository.delete(session);
    }

    public Boolean hasByAccessToken(String accessToken) {
        return sessionRepository.existsByAccessToken(accessToken);
    }
}
