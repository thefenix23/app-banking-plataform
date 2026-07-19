package com.app.backend.identity.domain.port.in;

import com.app.backend.identity.application.dto.RegisterUserCommand;
import com.app.backend.identity.domain.model.User;

public interface RegisterUserUseCase {
    User register(RegisterUserCommand command);
}
