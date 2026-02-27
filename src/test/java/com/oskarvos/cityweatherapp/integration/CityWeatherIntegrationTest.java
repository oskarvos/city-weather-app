package com.oskarvos.cityweatherapp.integration;

import com.oskarvos.cityweatherapp.city.entity.City;
import com.oskarvos.cityweatherapp.city.service.persistence.CityPersistenceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Интеграционный тест: работа с БД")
public class CityWeatherIntegrationTest {

    @Autowired
    CityPersistenceService cityPersistenceService;

    @Test
    @DisplayName("Сохраняем город в БД")
    void shouldSaveCityAndFindCity() {

        City saved = cityPersistenceService.saveCityInDb("Minsk", 28.2);

        City result = cityPersistenceService.getCityFromDb("Minsk");

        assertThat(result.getCityName()).isEqualTo("Minsk");
        assertThat(result.getTemperature()).isEqualTo(28.2);
    }

}
