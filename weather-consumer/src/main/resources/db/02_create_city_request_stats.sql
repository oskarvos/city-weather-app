CREATE TABLE IF NOT EXISTS city_request_stats
(
    id BIGSERIAL PRIMARY KEY,
    city_name VARCHAR(50) UNIQUE NOT NULL,
    request_count INTEGER NOT NULL
)