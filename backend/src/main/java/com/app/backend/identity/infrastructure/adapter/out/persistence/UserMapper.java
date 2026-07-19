package com.app.backend.identity.infrastructure.adapter.out.persistence;

import com.app.backend.identity.domain.model.OnboardingStatus;
import com.app.backend.identity.domain.model.User;

public class UserMapper {

    public static UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                OnboardingStatus.valueOf(user.getOnboardingStatus().name()),
                user.getCreatedAt()
        );
    }

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getPasswordHash(),
                OnboardingStatus.valueOf(entity.getOnboardingStatus().name()),
                entity.getCreatedAt()
        );
    }
}
