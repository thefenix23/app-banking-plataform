package com.app.backend.identity.domain.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private final UUID id;
    private final String email;
    private final String passwordHash;
    private OnboardingStatus onboardingStatus;
    private final LocalDateTime createdAt;

    public User(UUID id, String email, String passwordHash,OnboardingStatus onboardingStatus, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.onboardingStatus = onboardingStatus;
        this.createdAt = createdAt;
    }

    public void approve() {
        this.onboardingStatus = OnboardingStatus.APPROVED;
    }

    public void markInReview() {
        this.onboardingStatus = OnboardingStatus.IN_REVIEW;
    }

    public void reject() {
        this.onboardingStatus = OnboardingStatus.REJECTED;
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
}
