CREATE TABLE IF NOT EXISTS weather_request_log
(
    id           BIGSERIAL PRIMARY KEY,
    city_name    VARCHAR(50)      NOT NULL,
    temperature  DOUBLE PRECISION NOT NULL,
    requested_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);