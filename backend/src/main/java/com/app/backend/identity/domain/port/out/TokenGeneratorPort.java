package com.app.backend.identity.domain.port.out;

public interface TokenGeneratorPort {
    String generate(String userId, String email);
}
