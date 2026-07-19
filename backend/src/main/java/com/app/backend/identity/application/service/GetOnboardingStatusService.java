package com.app.backend.identity.application.service;

import com.app.backend.identity.application.dto.OnboardingStatusResult;
import com.app.backend.identity.domain.exception.UserNotFoundException;
import com.app.backend.identity.domain.model.OnboardingStatus;
import com.app.backend.identity.domain.model.User;
import com.app.backend.identity.domain.port.in.GetOnboardingStatusUseCase;
import com.app.backend.identity.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetOnboardingStatusService implements GetOnboardingStatusUseCase {

    private final UserRepositoryPort userRepository;

    public GetOnboardingStatusService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OnboardingStatusResult getStatus(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        return new OnboardingStatusResult(
                user.getId().toString(),
                user.getEmail(),
                user.getOnboardingStatus().name()
        );
    }
}
