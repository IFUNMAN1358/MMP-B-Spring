package com.nagornov.multimicroserviceproject.authservice.config.security.manager;

import com.nagornov.multimicroserviceproject.authservice.repository.SessionRepository;
import com.nagornov.multimicroserviceproject.authservice.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class SessionAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final SessionRepository sessionRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        HttpServletRequest request = object.getRequest();

        String token = JwtUtils.getTokenFromRequest(request);

        if (!sessionRepository.existsByAccessToken(token)) {
            return new AuthorizationDecision(false);
        }
        return new AuthorizationDecision(true);
    }

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationDecision decision = check(authentication, object);
        if (!decision.isGranted()) {
            throw new org.springframework.security.access.AccessDeniedException("Session does not exist");
        }
    }

}
