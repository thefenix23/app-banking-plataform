package com.app.backend.identity.application.service;

import com.app.backend.identity.application.dto.LoginResult;
import com.app.backend.identity.application.dto.LoginUserCommand;
import com.app.backend.identity.domain.exception.InvalidCredentialsException;
import com.app.backend.identity.domain.model.OnboardingStatus;
import com.app.backend.identity.domain.model.User;
import com.app.backend.identity.domain.port.out.PasswordVerifierPort;
import com.app.backend.identity.domain.port.out.TokenGeneratorPort;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginUserServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private PasswordVerifierPort passwordVerifier;

    @Mock
    private TokenGeneratorPort tokenGenerator;

    @InjectMocks
    private LoginUserService loginUserService;

    private User existingUser;
    private LoginUserCommand validCommand;

    @BeforeEach
    void setUp() {
        existingUser = new User(
                UUID.randomUUID(),
                "edi@example.com",
                "hashSimulado",
                OnboardingStatus.PENDING,
                LocalDateTime.now()
        );

        validCommand = new LoginUserCommand("edi@example.com", "Password123");
    }

    @Test
    void shouldReturnTokenWhenCredentialsAreValid() {
        when(userRepository.findByEmail(validCommand.email()))
                .thenReturn(Optional.of(existingUser));

        when(passwordVerifier.matches(validCommand.rawPassword(), existingUser.getPasswordHash()))
                .thenReturn(true);

        when(tokenGenerator.generate(existingUser.getId().toString(), existingUser.getEmail()))
                .thenReturn("jwtSimulado");

        LoginResult result = loginUserService.login(validCommand);

        assertThat(result.token()).isEqualTo("jwtSimulado");
        assertThat(result.userId()).isEqualTo(existingUser.getId().toString());
        assertThat(result.onboardingStatus()).isEqualTo("PENDING");
    }

    @Test
    void shouldThrowExceptionWhenEmailDoesNotExist() {
        when(userRepository.findByEmail(validCommand.email()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> loginUserService.login(validCommand))
                .isInstanceOf(InvalidCredentialsException.class);

        verify(tokenGenerator, never()).generate(any(), any());
    }

    @Test
    void shouldThrowExceptionWhenPasswordDoesNotMatch() {
        when(userRepository.findByEmail(validCommand.email()))
                .thenReturn(Optional.of(existingUser));

        when(passwordVerifier.matches(validCommand.rawPassword(), existingUser.getPasswordHash()))
                .thenReturn(false);

        assertThatThrownBy(() -> loginUserService.login(validCommand))
                .isInstanceOf(InvalidCredentialsException.class);

        verify(tokenGenerator, never()).generate(any(), any());
    }
}
