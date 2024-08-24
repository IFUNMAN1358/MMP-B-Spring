package com.nagornov.multimicroserviceproject.authservice.config.security.jwt;

import com.nagornov.multimicroserviceproject.authservice.repository.JwtRepository;
import com.nagornov.multimicroserviceproject.authservice.service.SessionService;
import com.nagornov.multimicroserviceproject.authservice.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtRepository jwtRepository;
    private final SessionService sessionService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String token = getTokenFromRequest(httpRequest);
        String requestUri = httpRequest.getRequestURI();

        if (token != null && jwtRepository.validateAccessToken(token)) {
            accessTokenFilter(token, requestUri, httpResponse);
        } else if (token != null && jwtRepository.validateRefreshToken(token)) {
            refreshTokenFilter(token, requestUri, httpResponse);
        }
        fc.doFilter(request, response);
    }

    private void accessTokenFilter(String token, String requestUri, HttpServletResponse httpResponse) {
        if (!sessionSkippedRequestUri().contains(requestUri)) {
            if (!sessionService.hasByAccessToken(token)) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        final Claims claims = jwtRepository.getAccessClaims(token);
        final JwtAuthentication jwtInfoToken = JwtUtils.generateAccessInfo(claims);
        jwtInfoToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
    }

    private void refreshTokenFilter(String token, String requestUri, HttpServletResponse httpResponse) {
        if (!sessionSkippedRequestUri().contains(requestUri)) {
            if (!sessionService.hasByRefreshToken(token)) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        final Claims claims = jwtRepository.getRefreshClaims(token);
        final JwtAuthentication jwtInfoToken = JwtUtils.generateRefreshInfo(claims);
        jwtInfoToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
    }

    private List<String> sessionSkippedRequestUri() {
        return List.of("/api/registration/verify");
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}
