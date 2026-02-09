CREATE TABLE IF NOT EXISTS favorite_cities
(
    id         BIGSERIAL PRIMARY KEY,
    city_id    BIGINT NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_favorite_city_city
        FOREIGN KEY (city_id)
            REFERENCES cities (id)
            ON DELETE CASCADE
);
