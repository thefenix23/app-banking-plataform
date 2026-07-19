package com.app.backend.identity.infrastructure.adapter.out.messaging;

public record IdentityVerificationSubmittedEvent(
        String userId,
        String verificationId
) {
}
