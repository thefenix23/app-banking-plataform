package com.app.backend.identity.application.dto;

public record LoginResult(
        String token,
        String userId,
        String onboardingStatus
) {
}
