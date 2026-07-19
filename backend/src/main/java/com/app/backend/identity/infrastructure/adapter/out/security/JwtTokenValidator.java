package com.app.backend.identity.infrastructure.adapter.out.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenValidator {

    private final SecretKey key;

    public JwtTokenValidator(@Value("${security.jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Válida el token y devuelve sus claims si es válido.
     * Lanza JwtException (o una subclase) si el token es inválido, está expirado o fue manipulado.
     */
    public Claims validateAndExtractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
