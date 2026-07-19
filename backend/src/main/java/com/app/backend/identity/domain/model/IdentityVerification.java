package com.app.backend.identity.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class IdentityVerification {

    private final UUID id;
    private final UUID userId;
    private final String documentType;
    private final String documentNumber;
    private final String documentImageUrl;
    private final String selfieImageUrl;
    private VerificationStatus status;
    private final LocalDateTime submittedAt;

    public IdentityVerification(UUID id, UUID userId, String documentType, String documentNumber, String documentImageUrl, String selfieImageUrl, VerificationStatus status, LocalDateTime submittedAt) {
        this.id = id;
        this.userId = userId;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.documentImageUrl = documentImageUrl;
        this.selfieImageUrl = selfieImageUrl;
        this.status = status;
        this.submittedAt = submittedAt;
    }

    public void markInReview() {
        this.status = VerificationStatus.IN_REVIEW;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getDocumentImageUrl() {
        return documentImageUrl;
    }

    public String getSelfieImageUrl() {
        return selfieImageUrl;
    }

    public VerificationStatus getStatus() {
        return status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }
}
