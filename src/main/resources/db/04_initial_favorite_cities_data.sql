INSERT INTO favorite_cities(city_id)
VALUES (4)
ON CONFLICT (city_id) DO NOTHING;

INSERT INTO favorite_cities(city_id)
VALUES (3)
ON CONFLICT (city_id) DO NOTHING;

INSERT INTO favorite_cities(city_id)
VALUES (6)
ON CONFLICT (city_id) DO NOTHING;