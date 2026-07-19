package com.app.backend.identity.infrastructure.adapter.out.persistence;

import com.app.backend.identity.domain.model.OnboardingStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "onboarding_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OnboardingStatus onboardingStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected UserEntity() {}

    public UserEntity(UUID id, String email, String passwordHash, OnboardingStatus onboardingStatus, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.onboardingStatus = onboardingStatus;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public OnboardingStatus getOnboardingStatus() {
        return onboardingStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setOnboardingStatus(OnboardingStatus onboardingStatus) {
        this.onboardingStatus = onboardingStatus;
    }
}


