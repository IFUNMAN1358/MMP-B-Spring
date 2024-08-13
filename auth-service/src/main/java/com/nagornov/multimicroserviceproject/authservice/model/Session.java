package com.nagornov.multimicroserviceproject.authservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sessions")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "session_id", unique = true, nullable = false, insertable = false, updatable = false)
    private Long sessionId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "access_token", length = 400, nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false, updatable = false)
    private String refreshToken;

    @Column(name = "device", nullable = false, updatable = false)
    private String device;

    @Column(name = "service", nullable = false, updatable = false)
    private String service;

    @Column(name = "os", nullable = false, updatable = false)
    private String os;

    @Column(name = "location", nullable = false, updatable = false)
    private String location;

    @Column(name = "last_activity", nullable = false)
    private LocalDateTime lastActivity;

    @PrePersist
    private void onCreate() {
        this.lastActivity = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.lastActivity = LocalDateTime.now();
    }

}
