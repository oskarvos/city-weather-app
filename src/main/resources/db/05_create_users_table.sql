CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    last_name  VARCHAR(30) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    email      VARCHAR(30) NOT NULL UNIQUE,
    role       VARCHAR(30) NOT NULL,
    login      VARCHAR(30) NOT NULL UNIQUE,
    password   VARCHAR(60) NOT NULL
);