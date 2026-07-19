package com.app.backend.identity.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(
                UUID.randomUUID(),
                "edi@example.com",
                "hashSimulado",
                OnboardingStatus.PENDING,
                LocalDateTime.now()
        );
    }

    @Test
    void aNewUserStartsWithPendingStatus() {
        assertThat(user.getOnboardingStatus()).isEqualTo(OnboardingStatus.PENDING);
    }

    @Test
    void markInReviewChangesStatusToInReview() {
        user.markInReview();

        assertThat(user.getOnboardingStatus()).isEqualTo(OnboardingStatus.IN_REVIEW);
    }

    @Test
    void approveChangesStatusToApproved() {
        user.approve();

        assertThat(user.getOnboardingStatus()).isEqualTo(OnboardingStatus.APPROVED);
    }

    @Test
    void rejectChangesStatusToRejected() {
        user.reject();

        assertThat(user.getOnboardingStatus()).isEqualTo(OnboardingStatus.REJECTED);
    }

    @Test
    void userKeepsTheDataItWasCreatedWith() {
        assertThat(user.getEmail()).isEqualTo("edi@example.com");
        assertThat(user.getPasswordHash()).isEqualTo("hashSimulado");
    }
}
