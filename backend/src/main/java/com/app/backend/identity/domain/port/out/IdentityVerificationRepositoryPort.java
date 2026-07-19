package com.app.backend.identity.domain.port.out;

import com.app.backend.identity.domain.model.IdentityVerification;

import java.util.Optional;
import java.util.UUID;

public interface IdentityVerificationRepositoryPort {
    void save(IdentityVerification verification);
    Optional<IdentityVerification> findLatestByUserId(UUID userId);
}
