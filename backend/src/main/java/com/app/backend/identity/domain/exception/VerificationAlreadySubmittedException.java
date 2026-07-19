package com.app.backend.identity.domain.exception;

public class VerificationAlreadySubmittedException extends RuntimeException {
    public VerificationAlreadySubmittedException(String userId) {
        super("Ya existe una verificación en proceso para el usuario: "  + userId);
    }
}
