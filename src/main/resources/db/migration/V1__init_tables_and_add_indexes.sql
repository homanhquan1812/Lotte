CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(100) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(20)  NOT NULL,

    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT chk_users_role CHECK (role IN ('ADMIN', 'STAFF'))
);

CREATE TABLE documents (
    id           BIGSERIAL PRIMARY KEY,
    code         VARCHAR(50)   NOT NULL,
    title        VARCHAR(255)  NOT NULL,
    description  TEXT,
    category     VARCHAR(100)  NOT NULL,
    status       VARCHAR(20)   NOT NULL,
    created_at   TIMESTAMP     NOT NULL DEFAULT now(),
    created_by   BIGINT        NOT NULL,
    file_name    VARCHAR(255),

    CONSTRAINT uq_documents_code UNIQUE (code),
    CONSTRAINT chk_documents_status CHECK (status IN ('DRAFT', 'SUBMITTED', 'APPROVED', 'REJECTED')),
    CONSTRAINT fk_documents_created_by FOREIGN KEY (created_by)
        REFERENCES users (id) ON DELETE RESTRICT
);

CREATE TABLE audit_log (
    id            BIGSERIAL PRIMARY KEY,
    action        VARCHAR(50) NOT NULL,
    target_id     BIGINT,
    audit_status  VARCHAR(20) NOT NULL,
    message       TEXT,
    created_at    TIMESTAMP   NOT NULL DEFAULT now(),

    CONSTRAINT chk_audit_log_status CHECK (audit_status IN ('SUCCESS', 'FAILURE'))
);

CREATE INDEX idx_documents_status ON documents (status);
CREATE INDEX idx_documents_category ON documents (category);
CREATE INDEX idx_documents_created_at ON documents (created_at);
CREATE INDEX idx_documents_created_by ON documents (created_by);

CREATE INDEX idx_audit_log_target_id ON audit_log (target_id);
CREATE INDEX idx_audit_log_created_at ON audit_log (created_at);