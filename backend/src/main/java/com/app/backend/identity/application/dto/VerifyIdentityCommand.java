package com.app.backend.identity.application.dto;

import java.util.UUID;

public record VerifyIdentityCommand(
        UUID userId,
        String documentType,
        String documentNumber,
        String documentImageUrl,
        String selfieImageUrl
) {
}
