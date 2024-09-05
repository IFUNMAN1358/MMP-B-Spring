package com.nagornov.multimicroserviceproject.authservice.service;

import com.nagornov.multimicroserviceproject.authservice.config.properties.ClientProperties;
import com.nagornov.multimicroserviceproject.authservice.config.properties.ServiceProperties;
import com.nagornov.multimicroserviceproject.authservice.dto.session.DeleteSessionRequest;
import com.nagornov.multimicroserviceproject.authservice.dto.session.SessionRequest;
import com.nagornov.multimicroserviceproject.authservice.exception.session.IncorrectServiceNameException;
import com.nagornov.multimicroserviceproject.authservice.exception.session.SessionNotFoundException;
import com.nagornov.multimicroserviceproject.authservice.exception.user.UserNotFoundException;
import com.nagornov.multimicroserviceproject.authservice.mapper.SessionMapper;
import com.nagornov.multimicroserviceproject.authservice.model.Session;
import com.nagornov.multimicroserviceproject.authservice.model.User;
import com.nagornov.multimicroserviceproject.authservice.repository.JwtRepository;
import com.nagornov.multimicroserviceproject.authservice.repository.SessionRepository;
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

    private final ServiceProperties serviceProperties;
    private final ClientProperties clientProperties;

    private final UserSenderService userSenderService;
    private final SessionRepository sessionRepository;
    private final JwtRepository jwtRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final SessionMapper sessionMapper;
    private final SessionSenderService sessionSenderService;

    public Session getSession(String userId, String serviceName) throws SessionNotFoundException, IncorrectServiceNameException {
        if (!clientProperties.clientsNameList().contains(serviceName)) {
            throw new IncorrectServiceNameException();
        }

        Optional<Session> optSession = sessionRepository.findByUserIdAndServiceName(UUID.fromString(userId), serviceName);
        if (optSession.isEmpty()) {
            throw new SessionNotFoundException();
        }
        return optSession.get();
    }

    public List<Session> getSessions(String userId) {
        return sessionRepository.getSessionsByUserId(UUID.fromString(userId));
    }

    @Transactional
    public void createSession(String userId, SessionRequest req) {
        Session session = sessionMapper.toSession(req);
        session.setUserId(UUID.fromString(userId));

        if (session.getServiceName().equals(serviceProperties.getServiceName()) || clientProperties.clientsNameList().contains(session.getServiceName())) {
            Optional<Session> optSession =
                    sessionRepository.findByServiceNameAndUserIdAndDeviceName(session.getServiceName(), session.getUserId(), session.getDeviceName());

            optSession.ifPresent(sessionRepository::delete);
            sessionRepository.save(session);
        }
    }

    @Transactional
    public Session updateSession(String userId, SessionRequest req) throws SessionNotFoundException, UserNotFoundException {

        Session session = sessionMapper.toSession(req);
        session.setUserId(UUID.fromString(userId));

        Optional<Session> optSession =
                sessionRepository.findByServiceNameAndUserIdAndDeviceName(session.getServiceName(), session.getUserId(), session.getDeviceName());
        if (optSession.isEmpty()) {
            throw new SessionNotFoundException();
        }
        Session existedSession = optSession.get();

        Optional<User> optUser = userSenderService.getUser(new User(existedSession.getUserId()));
        if (optUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        String newAccessToken = jwtRepository.generateAccessToken(optUser.get());
        existedSession.setAccessToken(newAccessToken);
        sessionRepository.save(existedSession);

        return existedSession;
    }

    @Transactional
    public void deleteSession(DeleteSessionRequest req, String userId) {
        Session session = sessionRepository.getSessionBySessionIdAndUserId(req.getSessionId(), UUID.fromString(userId));

        if (session.getServiceName().equals(serviceProperties.getServiceName())) {
            messagingTemplate.convertAndSend("/session", session);
        } else {
            sessionSenderService.logout(session.getServiceName(), session);
        }
        sessionRepository.delete(session);
    }

}
