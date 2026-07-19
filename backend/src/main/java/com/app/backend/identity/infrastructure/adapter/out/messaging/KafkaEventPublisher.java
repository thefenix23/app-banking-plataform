package com.app.backend.identity.infrastructure.adapter.out.messaging;

import com.app.backend.identity.domain.port.out.EventPublisherPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class KafkaEventPublisher implements EventPublisherPort {

    private static final String USER_REGISTERED_TOPIC = "identity.user-registered";
    private static final String IDENTITY_VERIFICATION_TOPIC = "identity.verification-submitted";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaEventPublisher(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publishUserRegistered(String userId, String email) {
        try {
            UserRegisteredEvent event = new UserRegisteredEvent(userId, email);
            kafkaTemplate.send(USER_REGISTERED_TOPIC, userId, event);
        } catch (Exception e) {
            System.err.println("Error publishing UserRegistered event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void publishIdentitySubmittedForReview(String userId, String verificationId) {
        try {
            IdentityVerificationSubmittedEvent event = new IdentityVerificationSubmittedEvent(userId, verificationId);
            kafkaTemplate.send(IDENTITY_VERIFICATION_TOPIC, userId, event);
        } catch (Exception e) {
            System.err.println("Error publishing IdentityVerificationSubmitted event: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
