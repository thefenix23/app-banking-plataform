package com.app.backend.identity.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Correo o contraseña incorrectos.");
    }
}
