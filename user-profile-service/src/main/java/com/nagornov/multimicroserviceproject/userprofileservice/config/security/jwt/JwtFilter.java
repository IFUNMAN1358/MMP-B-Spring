package com.nagornov.multimicroserviceproject.userprofileservice.config.security.jwt;

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

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter  { //extends GenericFilterBean

    private static final String AUTHORIZATION = "Authorization";

//    private final JwtRepository jwtRepository;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
//            throws IOException, ServletException {
//        final String token = getTokenFromRequest((HttpServletRequest) request);
//
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String requestURI = httpRequest.getRequestURI();
//
//        if (token != null && jwtRepository.validateAccessToken(token)) {
//
//            if (!sessionSkippedRequestUri().contains(requestURI)) {
//                if (!sessionService.hasByAccessToken(token)) {
//                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//                    return;
//                }
//            }
//
//            final Claims claims = jwtRepository.getAccessClaims(token);
//            final JwtAuthentication jwtInfoToken = JwtUtil.generate(claims);
//            jwtInfoToken.setAuthenticated(true);
//            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
//        }
//        fc.doFilter(request, response);
//    }
//
//    private String getTokenFromRequest(HttpServletRequest request) {
//        final String bearer = request.getHeader(AUTHORIZATION);
//        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
//            return bearer.substring(7);
//        }
//        return null;
//    }

}
