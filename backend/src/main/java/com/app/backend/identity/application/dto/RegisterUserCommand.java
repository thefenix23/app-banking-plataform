package com.app.backend.identity.application.dto;

public record RegisterUserCommand(
        String email,
        String rawPassword
) {
}
