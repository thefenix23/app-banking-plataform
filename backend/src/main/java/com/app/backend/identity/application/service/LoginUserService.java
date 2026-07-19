package com.app.backend.identity.application.service;

import com.app.backend.identity.application.dto.LoginResult;
import com.app.backend.identity.application.dto.LoginUserCommand;
import com.app.backend.identity.domain.exception.InvalidCredentialsException;
import com.app.backend.identity.domain.model.User;
import com.app.backend.identity.domain.port.in.LoginUserUseCase;
import com.app.backend.identity.domain.port.out.PasswordVerifierPort;
import com.app.backend.identity.domain.port.out.TokenGeneratorPort;
import com.app.backend.identity.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService implements LoginUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordVerifierPort passwordVerifier;
    private final TokenGeneratorPort tokenGenerator;

    public LoginUserService(UserRepositoryPort userRepository, PasswordVerifierPort passwordVerifier, TokenGeneratorPort tokenGenerator) {
        this.userRepository = userRepository;
        this.passwordVerifier = passwordVerifier;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public LoginResult login(LoginUserCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(InvalidCredentialsException::new);

        boolean passwordMatches = passwordVerifier.matches(
                command.rawPassword(), user.getPasswordHash()
        );

        if (!passwordMatches) throw new InvalidCredentialsException();

        String token = tokenGenerator.generate(
                user.getId().toString(),
                user.getEmail()
        );

        return new LoginResult(
                token,
                user.getId().toString(),
                user.getOnboardingStatus().name()
        );
    }
}
