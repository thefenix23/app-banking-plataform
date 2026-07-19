package com.app.backend.identity.domain.port.in;

import com.app.backend.identity.application.dto.OnboardingStatusResult;

import java.util.UUID;

public interface GetOnboardingStatusUseCase {
    OnboardingStatusResult getStatus(UUID userId);
}
