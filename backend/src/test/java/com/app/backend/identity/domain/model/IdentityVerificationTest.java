package com.app.backend.identity.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IdentityVerificationTest {

    private IdentityVerification verification;

    @BeforeEach
    void setUp() {
        verification = new IdentityVerification(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "INE",
                "1234567890",
                "https://storage.example.com/doc.jpg",
                "https://storage.example.com/selfie.jpg",
                VerificationStatus.SUBMITTED,
                LocalDateTime.now()
        );
    }

    @Test
    void aNewVerificationStartsWithSubmittedStatus() {
        assertThat(verification.getStatus()).isEqualTo(VerificationStatus.SUBMITTED);
    }

    @Test
    void verificationKeepsTheDataItWasCreatedWith() {
        assertThat(verification.getDocumentType()).isEqualTo("INE");
        assertThat(verification.getDocumentNumber()).isEqualTo("1234567890");
        assertThat(verification.getSelfieImageUrl())
                .isEqualTo("https://storage.example.com/selfie.jpg");
    }
}
