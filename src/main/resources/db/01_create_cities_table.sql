CREATE TABLE IF NOT EXISTS cities (
    id          BIGSERIAL PRIMARY KEY,
    city_name   VARCHAR(20) NOT NULL UNIQUE,
    temperature DECIMAL     NOT NULL
);