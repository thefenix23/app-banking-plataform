package com.app.backend.identity.infrastructure.adapter.in.web.dto;

public record VerifyIdentityResponse(
        String verificationId,
        String userId,
        String status
) {
}
