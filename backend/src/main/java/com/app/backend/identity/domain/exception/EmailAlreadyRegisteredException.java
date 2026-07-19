package com.app.backend.identity.domain.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String email) {
        super("El correo ya esta registrado: " + email);
    }
}
