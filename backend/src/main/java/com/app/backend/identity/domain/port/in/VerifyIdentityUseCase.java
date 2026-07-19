package com.app.backend.identity.domain.port.in;

import com.app.backend.identity.application.dto.VerifyIdentityCommand;
import com.app.backend.identity.domain.model.IdentityVerification;

public interface VerifyIdentityUseCase {
    IdentityVerification verify(VerifyIdentityCommand command);
}
