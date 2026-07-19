package com.app.backend.identity.infrastructure.security;

import com.app.backend.identity.infrastructure.adapter.out.security.JwtTokenValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenValidator tokenValidator;

    public JwtAuthenticationFilter(JwtTokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(BEARER_PREFIX.length());

        try {
            Claims claims = tokenValidator.validateAndExtractClaims(token);
            String userId = claims.getSubject();

            var authentication = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    List.of()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            /**
             * Token inválido, expirado o manipulado: no se autentica.
             * No se lanza excepción aquí, Spring Security se encarga de
             * rechazar la petición más adelante si el endpoint requiere autenticación.
             */
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
