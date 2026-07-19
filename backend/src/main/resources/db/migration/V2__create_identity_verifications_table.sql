CREATE TABLE identity_verifications (
    id RAW(16) NOT NULL ,
    user_id RAW(16) NOT NULL ,
    document_type VARCHAR2(30) NOT NULL ,
    document_number VARCHAR2(50) NOT NULL ,
    document_image_url VARCHAR2(500) NOT NULL ,
    selfie_image_url VARCHAR2(500) NOT NULL ,
    status VARCHAR2(20) NOT NULL ,
    submitted_at TIMESTAMP NOT NULL ,
    CONSTRAINT pk_identity_verifications PRIMARY KEY (id) ,
    CONSTRAINT fk_identity_verifications_user
                                    FOREIGN KEY (user_id) REFERENCES users (id) ,
    CONSTRAINT ck_identity_verifications_status
                                    CHECK ( status IN ('SUBMITTED', 'IN_REVIEW', 'APPROVED', 'REJECTED'))
);

CREATE INDEX idx_identity_verifications_user_id ON identity_verifications (user_id);
CREATE INDEX idx_identity_verifications_status ON identity_verifications (status);