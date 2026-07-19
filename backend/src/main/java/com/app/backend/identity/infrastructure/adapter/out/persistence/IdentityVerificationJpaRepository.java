package com.app.backend.identity.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IdentityVerificationJpaRepository extends JpaRepository<IdentityVerificationEntity, UUID> {
    List<IdentityVerificationEntity> findByUserIdOrderBySubmittedAtDesc(UUID userId);
}
