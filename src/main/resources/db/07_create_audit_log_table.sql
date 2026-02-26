CREATE TABLE IF NOT EXISTS audit_log
(
    id          BIGSERIAL PRIMARY KEY,
    last_name   VARCHAR(30) NOT NULL,
    first_name  VARCHAR(30) NOT NULL,
    role        VARCHAR(30) NOT NULL,
    login       VARCHAR(30) NOT NULL,
    action_name VARCHAR(128),
    parameters  TEXT,
    success     BOOLEAN,
    timestamp   TIMESTAMP
);