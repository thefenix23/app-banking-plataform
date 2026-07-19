package com.app.backend.identity.infrastructure.adapter.in.web;

import com.app.backend.identity.application.dto.*;
import com.app.backend.identity.domain.model.IdentityVerification;
import com.app.backend.identity.domain.model.User;
import com.app.backend.identity.domain.port.in.GetOnboardingStatusUseCase;
import com.app.backend.identity.domain.port.in.LoginUserUseCase;
import com.app.backend.identity.domain.port.in.RegisterUserUseCase;
import com.app.backend.identity.domain.port.in.VerifyIdentityUseCase;
import com.app.backend.identity.infrastructure.adapter.in.web.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/identity")
public class IdentityController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final VerifyIdentityUseCase verifyIdentityUseCase;
    private final GetOnboardingStatusUseCase getOnboardingStatusUseCase;

    public IdentityController(RegisterUserUseCase registerUserUseCase, LoginUserUseCase loginUserUseCase, VerifyIdentityUseCase verifyIdentityUseCase, GetOnboardingStatusUseCase getOnboardingStatusUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
        this.verifyIdentityUseCase = verifyIdentityUseCase;
        this.getOnboardingStatusUseCase = getOnboardingStatusUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = registerUserUseCase.register(
                new RegisterUserCommand(
                        request.email(),
                        request.password()
                )
        );

        RegisterResponse response = new RegisterResponse(
                user.getId().toString(),
                user.getEmail(),
                user.getOnboardingStatus().name()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResult> login(@Valid @RequestBody LoginRequest request) {
        LoginResult result = loginUserUseCase.login(
                new LoginUserCommand(
                        request.email(),
                        request.password()
                )
        );

        return ResponseEntity.ok(result);
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyIdentityResponse> verify(@Valid @RequestBody VerifyIdentityRequest request) {
        IdentityVerification verification = verifyIdentityUseCase.verify(
                new VerifyIdentityCommand(
                        UUID.fromString(request.userId()),
                        request.documentType(),
                        request.documentNumber(),
                        request.documentImageUrl(),
                        request.selfieImageUrl()
                )
        );

        VerifyIdentityResponse response = new VerifyIdentityResponse(
                verification.getId().toString(),
                verification.getUserId().toString(),
                verification.getStatus().name()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/onboarding-status/{userId}")
    public ResponseEntity<OnboardingStatusResult> getOnboardingStatus(@PathVariable String userId) {
        OnboardingStatusResult result = getOnboardingStatusUseCase.getStatus(UUID.fromString(userId));

        return ResponseEntity.ok(result);
    }
}
