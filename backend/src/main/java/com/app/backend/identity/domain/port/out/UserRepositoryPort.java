package com.app.backend.identity.domain.port.out;

import com.app.backend.identity.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {

    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
}
