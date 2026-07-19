package com.app.backend.identity.application.service;

import com.app.backend.identity.application.dto.VerifyIdentityCommand;
import com.app.backend.identity.domain.exception.UserNotFoundException;
import com.app.backend.identity.domain.exception.VerificationAlreadySubmittedException;
import com.app.backend.identity.domain.model.IdentityVerification;
import com.app.backend.identity.domain.model.OnboardingStatus;
import com.app.backend.identity.domain.model.User;
import com.app.backend.identity.domain.model.VerificationStatus;
import com.app.backend.identity.domain.port.out.EventPublisherPort;
import com.app.backend.identity.domain.port.out.IdentityVerificationRepositoryPort;
import com.app.backend.identity.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VerifyIdentityServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private IdentityVerificationRepositoryPort verificationRepository;

    @Mock
    private EventPublisherPort eventPublisher;

    @InjectMocks
    private VerifyIdentityService verifyIdentityService;

    private User existingUser;
    private VerifyIdentityCommand validCommand;

    @BeforeEach
    void setUp() {
        existingUser = new User(
                UUID.randomUUID(),
                "edi@example.com",
                "hashSimulado",
                OnboardingStatus.PENDING,
                LocalDateTime.now()
        );

        validCommand = new VerifyIdentityCommand(
                existingUser.getId(),
                "INE",
                "1234567890",
                "https://storage.example.com/doc.jpg",
                "https://storage.example.com/selfie.jpg"
        );
    }

    @Test
    void shouldSubmitVerificationWhenUserExistsAndHashNoPendingVerification() {
        when(userRepository.findById(existingUser.getId()))
                .thenReturn(Optional.of(existingUser));

        when(verificationRepository.findLatestByUserId(existingUser.getId()))
                .thenReturn(Optional.empty());

        IdentityVerification result = verifyIdentityService.verify(validCommand);

        assertThat(result.getStatus()).isEqualTo(VerificationStatus.IN_REVIEW);
        assertThat(result.getUserId()).isEqualTo(existingUser.getId());
        assertThat(existingUser.getOnboardingStatus()).isEqualTo(OnboardingStatus.IN_REVIEW);

        verify(verificationRepository).save(any(IdentityVerification.class));
        verify(userRepository).save(existingUser);
        verify(eventPublisher).publishIdentitySubmittedForReview(eq(existingUser.getId().toString()), any());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        when(userRepository.findById(existingUser.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> verifyIdentityService.verify(validCommand))
                .isInstanceOf(UserNotFoundException.class);

        verify(verificationRepository, never()).save(any());
        verify(eventPublisher, never()).publishIdentitySubmittedForReview(any(), any());
    }

    @Test
    void shouldThrowExceptionWhenVerificationAlreadyInReview() {
        IdentityVerification existingVerification = new IdentityVerification(
                UUID.randomUUID(),
                existingUser.getId(),
                "INE",
                "1234567890",
                "httpas://storage.example.com/doc-old.jpg",
                "httpas://storage.example.com/selfie-old.jpg",
                VerificationStatus.IN_REVIEW,
                LocalDateTime.now()
        );

        when(userRepository.findById(existingUser.getId()))
                .thenReturn(Optional.of(existingUser));

        when(verificationRepository.findLatestByUserId(existingUser.getId()))
                .thenReturn(Optional.of(existingVerification));

        assertThatThrownBy(() -> verifyIdentityService.verify(validCommand))
                .isInstanceOf(VerificationAlreadySubmittedException.class);

        verify(verificationRepository, never()).save(any());
        verify(eventPublisher, never()).publishIdentitySubmittedForReview(any(), any());
    }
}
