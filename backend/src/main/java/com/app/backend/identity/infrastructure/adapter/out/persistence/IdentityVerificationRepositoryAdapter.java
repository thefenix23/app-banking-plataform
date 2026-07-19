package com.app.backend.identity.infrastructure.adapter.out.persistence;

import com.app.backend.identity.domain.model.IdentityVerification;
import com.app.backend.identity.domain.port.out.IdentityVerificationRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class IdentityVerificationRepositoryAdapter implements IdentityVerificationRepositoryPort {

    private final IdentityVerificationJpaRepository jpaRepository;

    public IdentityVerificationRepositoryAdapter(IdentityVerificationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(IdentityVerification verification) {
        jpaRepository.save(IdentityVerificationMapper.toEntity(verification));
    }

    @Override
    public Optional<IdentityVerification> findLatestByUserId(UUID userId) {
        List<IdentityVerificationEntity> results =
                jpaRepository.findByUserIdOrderBySubmittedAtDesc(userId);

        return results.isEmpty()
                ? Optional.empty()
                : Optional.of(IdentityVerificationMapper.toDomain(results.get(0)));
    }
}
