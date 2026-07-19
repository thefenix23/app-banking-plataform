package com.app.backend.identity.application.dto;

public record OnboardingStatusResult(
        String userId,
        String email,
        String onboardingStatus
) {
}
