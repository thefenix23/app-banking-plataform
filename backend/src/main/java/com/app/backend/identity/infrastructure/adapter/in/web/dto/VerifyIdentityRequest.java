package com.app.backend.identity.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyIdentityRequest(
        @NotBlank String userId,
        @NotBlank String documentType,
        @NotBlank String documentNumber,
        @NotBlank String documentImageUrl,
        @NotBlank String selfieImageUrl
) {
}
