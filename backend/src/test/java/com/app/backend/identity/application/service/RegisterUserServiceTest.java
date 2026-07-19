package com.app.backend.identity.application.service;

import com.app.backend.identity.application.dto.RegisterUserCommand;
import com.app.backend.identity.domain.exception.EmailAlreadyRegisteredException;
import com.app.backend.identity.domain.exception.InvalidPasswordException;
import com.app.backend.identity.domain.model.OnboardingStatus;
import com.app.backend.identity.domain.model.User;
import com.app.backend.identity.domain.port.out.EventPublisherPort;
import com.app.backend.identity.domain.port.out.PasswordHasherPort;
import com.app.backend.identity.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterUserServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private PasswordHasherPort passwordHasher;

    @Mock
    private EventPublisherPort eventPublisher;

    @InjectMocks
    private RegisterUserService registerUserService;

    private RegisterUserCommand validCommand;

    @BeforeEach
    public void setUp() {
        validCommand = new RegisterUserCommand("edi@example.com", "Password123");
    }

    @Test
    void shouldRegisterUserWhenDataIsValid() {
        when(userRepository.findByEmail(validCommand.email())).thenReturn(Optional.empty());
        when(passwordHasher.hash(validCommand.rawPassword())).thenReturn("hashSimulado");

        User result = registerUserService.register(validCommand);

        assertThat(result.getEmail()).isEqualTo("edi@example.com");
        assertThat(result.getPasswordHash()).isEqualTo("hashSimulado");
        assertThat(result.getOnboardingStatus()).isEqualTo(OnboardingStatus.PENDING);

        verify(userRepository).save(any(User.class));
        verify(eventPublisher).publishUserRegistered(any(), eq("edi@example.com"));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        User existingUser = mock(User.class);
        when(userRepository.findByEmail(validCommand.email()))
        .thenReturn(Optional.of(existingUser));

        assertThatThrownBy(() -> registerUserService.register(validCommand))
                .isInstanceOf(EmailAlreadyRegisteredException.class);

        verify(userRepository, never()).save(any());
        verify(eventPublisher, never()).publishUserRegistered(any(), any());
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsWeak() {
        RegisterUserCommand invalidCommand =
                new RegisterUserCommand("edi@example.com", "123");

        assertThatThrownBy(() -> registerUserService.register(invalidCommand))
                .isInstanceOf(InvalidPasswordException.class);

        verify(userRepository, never()).save(any());
    }
}
