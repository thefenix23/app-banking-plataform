package com.app.backend.identity.domain.port.out;

public interface EventPublisherPort {
    void publishUserRegistered(String userId, String email);
    void publishIdentitySubmittedForReview(String userId, String verificationId);
}
