package com.app.backend.identity.infrastructure.adapter.out.persistence;

import com.app.backend.identity.domain.model.VerificationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "identity_verifications")
public class IdentityVerificationEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @Column(name = "document_image_url", nullable = false)
    private String documentImageUrl;

    @Column(name = "selfie_image_url", nullable = false)
    private String selfieImageUrl;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private VerificationStatus status;

    @Column(name = "submitted_at", nullable = false, updatable = false)
    private LocalDateTime submittedAt;

    protected IdentityVerificationEntity() {}

    public IdentityVerificationEntity(UUID id, UUID userId, String documentType, String documentNumber, String documentImageUrl, String selfieImageUrl, VerificationStatus status, LocalDateTime submittedAt) {
        this.id = id;
        this.userId = userId;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.documentImageUrl = documentImageUrl;
        this.selfieImageUrl = selfieImageUrl;
        this.status = status;
        this.submittedAt = submittedAt;
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
