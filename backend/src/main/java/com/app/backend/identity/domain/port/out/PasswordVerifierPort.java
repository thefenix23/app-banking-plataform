package com.app.backend.identity.domain.port.out;

public interface PasswordVerifierPort {
    boolean matches(String rawPassword, String passwordHash);
}
