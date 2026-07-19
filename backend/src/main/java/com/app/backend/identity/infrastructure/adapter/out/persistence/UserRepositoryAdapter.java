package com.app.backend.identity.infrastructure.adapter.out.persistence;

import com.app.backend.identity.domain.model.User;
import com.app.backend.identity.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(User user) {
        jpaRepository.save(UserMapper.toEntity(user));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(UserMapper::toDomain);
    }
}
