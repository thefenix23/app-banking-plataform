package com.app.backend.identity.application.service;

import com.app.backend.identity.application.dto.VerifyIdentityCommand;
import com.app.backend.identity.domain.exception.UserNotFoundException;
import com.app.backend.identity.domain.exception.VerificationAlreadySubmittedException;
import com.app.backend.identity.domain.model.IdentityVerification;
import com.app.backend.identity.domain.model.OnboardingStatus;
import com.app.backend.identity.domain.model.User;
import com.app.backend.identity.domain.model.VerificationStatus;
import com.app.backend.identity.domain.port.in.VerifyIdentityUseCase;
import com.app.backend.identity.domain.port.out.EventPublisherPort;
import com.app.backend.identity.domain.port.out.IdentityVerificationRepositoryPort;
import com.app.backend.identity.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerifyIdentityService implements VerifyIdentityUseCase {

    private final UserRepositoryPort userRepository;
    private final IdentityVerificationRepositoryPort verificationRepository;
    private final EventPublisherPort eventPublisher;

    public VerifyIdentityService(UserRepositoryPort userRepository, IdentityVerificationRepositoryPort verificationRepository, EventPublisherPort eventPublisher) {
        this.userRepository = userRepository;
        this.verificationRepository = verificationRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public IdentityVerification verify(VerifyIdentityCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new UserNotFoundException(command.userId().toString()));

        verificationRepository.findLatestByUserId(command.userId())
                .filter(v -> v.getStatus() == VerificationStatus.IN_REVIEW
                        || v.getStatus() == VerificationStatus.SUBMITTED
                )
                .ifPresent(v -> {
                    throw new VerificationAlreadySubmittedException(command.userId().toString());
                });

        IdentityVerification verification = new IdentityVerification(
                UUID.randomUUID(),
                command.userId(),
                command.documentType(),
                command.documentNumber(),
                command.documentImageUrl(),
                command.selfieImageUrl(),
                VerificationStatus.SUBMITTED,
                LocalDateTime.now()
        );

        verification.markInReview();
        verificationRepository.save(verification);

       user.markInReview();
       userRepository.save(user);

       eventPublisher.publishIdentitySubmittedForReview(
               user.getId().toString(),
               verification.getId().toString()
       );

       return verification;
    }
}
