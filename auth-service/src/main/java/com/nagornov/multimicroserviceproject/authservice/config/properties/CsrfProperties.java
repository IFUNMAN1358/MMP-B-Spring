package com.nagornov.multimicroserviceproject.authservice.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CsrfProperties {

    @Value("${csrf.header-name}")
    private String csrfHeaderName;

    @Value("${csrf.cookie.name}")
    private String csrfCookieName;

    @Value("${csrf.cookie.domain}")
    private String csrfCookieDomain;

    @Value("${csrf.cookie.path}")
    private String csrfCookiePath;

    @Value("${csrf.cookie.http-only}")
    private Boolean csrfCookieHttpOnly;

    @Value("${csrf.cookie.secure}")
    private Boolean csrfCookieSecure;

    @Value("${csrf.cookie.same-site}")
    private String csrfCookieSameSite;

}
