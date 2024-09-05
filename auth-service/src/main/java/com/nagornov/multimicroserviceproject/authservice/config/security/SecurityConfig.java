package com.nagornov.multimicroserviceproject.authservice.config.security;

import com.nagornov.multimicroserviceproject.authservice.config.security.exception.CustomAccessDeniedHandler;
import com.nagornov.multimicroserviceproject.authservice.config.security.exception.CustomAuthenticationEntryPoint;
import com.nagornov.multimicroserviceproject.authservice.config.security.filter.CustomCorsFilter;
import com.nagornov.multimicroserviceproject.authservice.config.security.filter.CustomCsrfFilter;
import com.nagornov.multimicroserviceproject.authservice.config.security.filter.CustomJwtFilter;
import com.nagornov.multimicroserviceproject.authservice.config.security.manager.RefreshTokenAuthorizationManager;
import com.nagornov.multimicroserviceproject.authservice.config.security.manager.SessionAuthorizationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;
import static org.springframework.security.authorization.AuthorizationManagers.allOf;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomCorsFilter corsFilter;
    private final CustomCsrfFilter csrfFilter;
    private final CustomJwtFilter jwtFilter;

    private final SessionAuthorizationManager hasSession;
    private final RefreshTokenAuthorizationManager hasRefreshToken;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          http
            .httpBasic(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
            .addFilterBefore(csrfFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth

                    // TEST
                    .requestMatchers("/api/test").hasRole("USER")

                    // WEBSOCKET
                    .requestMatchers("/ws/**").permitAll()

                    // AUTH
                    .requestMatchers("/api/auth/login").permitAll()
                    .requestMatchers("/api/auth/{providerName}/code").permitAll()

                    // REGISTRATION
                    .requestMatchers("/api/registration").permitAll()
                    .requestMatchers("/api/registration/verify").hasRole("UNREGISTERED")

                    // SESSION
                    .requestMatchers(HttpMethod.GET, "/api/session").access(hasRefreshToken)
                    .requestMatchers(HttpMethod.POST, "/api/session").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/api/sessions").access(allOf(hasRole("USER"), hasSession))
                    .requestMatchers("/api/session/update").access(hasRefreshToken)
                    .requestMatchers("/api/session/delete").access(allOf(hasRole("USER"), hasSession))

                    .anyRequest().authenticated()
            )
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
            );
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/resources/**");
    }
}
