package com.app.backend.identity.application.service;

import com.app.backend.identity.application.dto.OnboardingStatusResult;
import com.app.backend.identity.domain.exception.UserNotFoundException;
import com.app.backend.identity.domain.model.OnboardingStatus;
import com.app.backend.identity.domain.model.User;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetOnboardingStatusServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private GetOnboardingStatusService getOnboardingStatusService;

    private User existingUser;

    @BeforeEach
    public void setUp() {
        existingUser = new User(
                UUID.randomUUID(),
                "edi@example.com",
                "hashSimulado",
                OnboardingStatus.APPROVED,
                LocalDateTime.now()
        );
    }

    @Test
    void shouldReturnStatusWhenUserExists() {
        when(userRepository.findById(existingUser.getId()))
                .thenReturn(Optional.of(existingUser));

        OnboardingStatusResult result = getOnboardingStatusService.getStatus(existingUser.getId());

        assertThat(result.userId()).isEqualTo(existingUser.getId().toString());
        assertThat(result.email()).isEqualTo("edi@example.com");
        assertThat(result.onboardingStatus()).isEqualTo("APPROVED");
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        UUID nonExistingUserId = UUID.randomUUID();

        when(userRepository.findById(nonExistingUserId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getOnboardingStatusService.getStatus(nonExistingUserId))
                .isInstanceOf(UserNotFoundException.class);
    }
}
