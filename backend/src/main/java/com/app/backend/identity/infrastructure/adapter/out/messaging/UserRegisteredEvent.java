package com.app.backend.identity.infrastructure.adapter.out.messaging;

public record UserRegisteredEvent(
        String userId,
        String email
) {
}
