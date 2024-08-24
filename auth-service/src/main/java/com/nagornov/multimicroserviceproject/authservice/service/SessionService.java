package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.dto.session.DeleteSessionRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.session.SessionRequest;
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
    private final SessionSenderService sessionSenderService;

    @Transactional
    public void createSession(SessionRequest req) {
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
    public Session updateSession(SessionRequest req) throws Exception {
        try {
            if (!jwtRepository.validateRefreshToken(req.getRefreshToken())) {
                sessionRepository.deleteSessionByRefreshToken(req.getRefreshToken());
                return null;
            }

            Claims claims = jwtRepository.getRefreshClaims(req.getRefreshToken());
            String userId = claims.getSubject();

            Session session = sessionRepository.getSessionByUserIdAndRefreshToken(UUID.fromString(userId), req.getRefreshToken());
            if (session == null) {
                throw new Exception("Session not found");
            }

            User user = new User();
            user.setUserId(session.getUserId());
            Optional<User> userFromService = userSenderService.getUser(user);

            String accessToken = jwtRepository.generateAccessToken(userFromService.get());
            session.setAccessToken(accessToken);
            session.setOs(req.getOs());
            session.setLocation(req.getLocation());
            sessionRepository.save(session);

            return session;
        } catch (Exception e) {
            throw new Exception("Failed to update session", e);
        }
    }

    public Session getSession(String refreshToken) {
        return sessionRepository.getSessionByRefreshToken(refreshToken);
    }

    public List<Session> getSessions(String userId) {
        return sessionRepository.getSessionsByUserId(UUID.fromString(userId));
    }

    @Transactional
    public void deleteSession(DeleteSessionRequest req, String userId) {
        Session session = sessionRepository.getSessionBySessionIdAndUserId(req.getSessionId(), UUID.fromString(userId));

        if (session.getService().equals("AuthService")) {
            messagingTemplate.convertAndSend("/session", session);
        } else {
            sessionSenderService.logout(session.getService(), session);
        }
        sessionRepository.delete(session);
    }

    public Boolean hasByAccessToken(String accessToken) {
        return sessionRepository.existsByAccessToken(accessToken);
    }

    public Boolean hasByRefreshToken(String refreshToken) {
        return sessionRepository.existsByRefreshToken(refreshToken);
    }
}
