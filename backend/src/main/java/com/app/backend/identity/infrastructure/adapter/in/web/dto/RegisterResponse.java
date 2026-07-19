package com.app.backend.identity.infrastructure.adapter.in.web.dto;

public record RegisterResponse(
        String userId,
        String email,
        String onboardingStatus
) {
}
