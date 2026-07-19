-- Tabla principal de usuarios(Identidad/Onboarding)
CREATE TABLE users (
    id RAW(16) NOT NULL ,
    email VARCHAR2(150) NOT NULL ,
    password_hash VARCHAR2(255) NOT NULL ,
    onboarding_status VARCHAR2(20) NOT NULL ,
    created_at TIMESTAMP NOT NULL ,
    CONSTRAINT pk_users PRIMARY KEY (id) ,
    CONSTRAINT uq_users_email UNIQUE (email) ,
    CONSTRAINT ck_users_onboarding_status
                   CHECK ( onboarding_status IN ('PENDING', 'IN_REVIEW', 'APPROVED', 'REJECTED'))
);

-- Índice adicional por si se filtra por estatus
CREATE INDEX idx_users_onboarding_status ON users (onboarding_status);