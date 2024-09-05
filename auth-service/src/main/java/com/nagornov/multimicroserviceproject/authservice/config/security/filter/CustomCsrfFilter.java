package com.nagornov.multimicroserviceproject.authservice.config.security.filter;

import com.nagornov.multimicroserviceproject.authservice.config.properties.CsrfProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomCsrfFilter extends GenericFilterBean {

    private final CsrfProperties securityProperties;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        CsrfToken csrfToken = csrfTokenRepository().loadToken(request);

        if (csrfToken == null) {
            csrfToken = csrfTokenRepository().generateToken(request);
            csrfTokenRepository().saveToken(csrfToken, request, response);
        }

        String csrfTokenFromCookie = getCsrfTokenFromCookie(request);

        if (csrfTokenFromCookie == null || !csrfTokenFromCookie.equals(csrfToken.getToken())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token missing or incorrect.");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private CookieCsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository repository = new CookieCsrfTokenRepository();

        repository.setHeaderName(securityProperties.getCsrfHeaderName());
        repository.setCookieName(securityProperties.getCsrfCookieName());

        repository.setCookieCustomizer(cookieBuilder ->
            cookieBuilder
                .domain(securityProperties.getCsrfCookieDomain())
                .path(securityProperties.getCsrfCookiePath())
                .httpOnly(securityProperties.getCsrfCookieHttpOnly())
                .secure(securityProperties.getCsrfCookieSecure())
                .sameSite(securityProperties.getCsrfCookieSameSite())
        );

        return repository;
    }

    private String getCsrfTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (securityProperties.getCsrfCookieName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}