package com.nagornov.multimicroserviceproject.authservice.config.security.manager;

import com.nagornov.multimicroserviceproject.authservice.dto.jwt.JwtAuthentication;
import com.nagornov.multimicroserviceproject.authservice.repository.JwtRepository;
import com.nagornov.multimicroserviceproject.authservice.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class RefreshTokenAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final JwtRepository jwtRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        HttpServletRequest request = object.getRequest();

        String refreshToken = request.getHeader("Refresh-Token");

        if (refreshToken != null && jwtRepository.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtRepository.getRefreshClaims(refreshToken);
            final JwtAuthentication jwtInfoToken = JwtUtils.generateRefreshInfo(claims);
            jwtInfoToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
            return new AuthorizationDecision(true);
        }
        return new AuthorizationDecision(false);
    }

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationDecision decision = check(authentication, object);
        if (!decision.isGranted()) {
            throw new org.springframework.security.access.AccessDeniedException("Refresh token invalid");
        }
    }
}
