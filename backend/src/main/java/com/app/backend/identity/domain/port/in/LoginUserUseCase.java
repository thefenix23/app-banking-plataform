package com.app.backend.identity.domain.port.in;

import com.app.backend.identity.application.dto.LoginResult;
import com.app.backend.identity.application.dto.LoginUserCommand;

public interface LoginUserUseCase {
    LoginResult login(LoginUserCommand command);
}
