package com.app.backend.identity.application.dto;

public record LoginUserCommand(
        String email,
        String rawPassword
) {
}
