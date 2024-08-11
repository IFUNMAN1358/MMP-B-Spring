package com.nagornov.multimicroserviceproject.authservice.config.security;

import com.nagornov.multimicroserviceproject.authservice.config.security.jwt.JwtFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomCorsFilter corsFilter;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          http
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(corsFilter, SessionManagementFilter.class)
            .authorizeHttpRequests(auth -> auth

                    // TEST
                    .requestMatchers("/api/test").permitAll()

                    // WEBSOCKET
                    .requestMatchers("/ws/**").permitAll()

                    // SESSION
                    .requestMatchers(HttpMethod.POST, "/api/session").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/session").hasRole("USER")
                    .requestMatchers("/api/session/update").permitAll()
                    .requestMatchers("/api/session/delete").hasRole("USER")

                    // AUTH
                    .requestMatchers("/api/auth/login").permitAll()
                    .requestMatchers("/api/auth/{providerName}/code").permitAll()

                    // REGISTRATION
                    .requestMatchers("/api/registration").permitAll()
                    .requestMatchers("/api/registration/verify").hasRole("UNREGISTERED")

                    .anyRequest().authenticated()
            )
            .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/resources/**");
    }
}
