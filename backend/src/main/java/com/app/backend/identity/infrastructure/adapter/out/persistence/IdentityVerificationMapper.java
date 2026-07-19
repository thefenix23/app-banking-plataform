package com.app.backend.identity.infrastructure.adapter.out.persistence;

import com.app.backend.identity.domain.model.IdentityVerification;
import com.app.backend.identity.domain.model.VerificationStatus;

public class IdentityVerificationMapper {

    public static IdentityVerificationEntity toEntity(IdentityVerification verification) {
        return new IdentityVerificationEntity(
                verification.getId(),
                verification.getUserId(),
                verification.getDocumentType(),
                verification.getDocumentNumber(),
                verification.getDocumentImageUrl(),
                verification.getSelfieImageUrl(),
                VerificationStatus.valueOf(verification.getStatus().name()),
                verification.getSubmittedAt()
        );
    }

    public static IdentityVerification toDomain(IdentityVerificationEntity entity) {
        return new IdentityVerification(
                entity.getId(),
                entity.getUserId(),
                entity.getDocumentType(),
                entity.getDocumentNumber(),
                entity.getDocumentImageUrl(),
                entity.getSelfieImageUrl(),
                VerificationStatus.valueOf(entity.getStatus().name()),
                entity.getSubmittedAt()
        );
    }
}
