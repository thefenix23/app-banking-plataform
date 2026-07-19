package com.app.backend.identity.application.service;

import com.app.backend.identity.application.dto.RegisterUserCommand;
import com.app.backend.identity.domain.exception.EmailAlreadyRegisteredException;
import com.app.backend.identity.domain.exception.InvalidPasswordException;
import com.app.backend.identity.domain.model.OnboardingStatus;
import com.app.backend.identity.domain.model.User;
import com.app.backend.identity.domain.port.in.RegisterUserUseCase;
import com.app.backend.identity.domain.port.out.EventPublisherPort;
import com.app.backend.identity.domain.port.out.PasswordHasherPort;
import com.app.backend.identity.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$"
    );
    private final UserRepositoryPort userRepository;
    private final PasswordHasherPort passwordHasher;
    private final EventPublisherPort eventPublisher;

    public RegisterUserService(UserRepositoryPort userRepository, PasswordHasherPort passwordHasher, EventPublisherPort eventPublisher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public User register(RegisterUserCommand command) {
        validatePassword(command.rawPassword());

        userRepository.findByEmail(command.email())
                .ifPresent(user -> {
                    throw new EmailAlreadyRegisteredException(command.email());
                });

        String hashedPassword = passwordHasher.hash(command.rawPassword());

        User newUser = new User(
                UUID.randomUUID(),
                command.email(),
                hashedPassword,
                OnboardingStatus.PENDING,
                LocalDateTime.now()
        );

        userRepository.save(newUser);
        eventPublisher.publishUserRegistered(newUser.getId().toString(), newUser.getEmail());

        return newUser;
    }

    private void validatePassword(String rawPassword) {
        if (rawPassword == null || !PASSWORD_PATTERN.matcher(rawPassword).matches()) {
            throw new InvalidPasswordException(
                    "La contraseña debe tener mínimo 8 caracteres, una mayúscula, una minúscula y un número."
            );
        }
    }
}
