package com.oskarvos.cityweatherapp.service.persistence;

import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityPersistenceServiceTest {

//    should[ОжидаемоеПоведение]When[Сценарий]

    @Mock
    CityRepository cityRepository;

    @InjectMocks
    CityPersistenceService cityPersistenceService;

    private City city;

    @BeforeEach
    void setUp() {
        city = new City();
        city.setId(1L);
        city.setCityName("Minsk");
        city.setTemperature(22.3);
        city.setUpdatedAt(LocalDateTime.of(2026, 2, 19, 20, 30, 45, 123456789));
    }

    @Test
    @DisplayName("Должен вызвать репозиторий города при получении города один раз")
    void shouldCallCityRepositoryOnceWhenGettingCity() {
        cityPersistenceService.getCityFromDb(city.getCityName());

        verify(cityRepository, times(1)).findByCityName(city.getCityName());
    }

    @Test
    @DisplayName("Должен вернуть null из БД, так как ничего не нашел")
    void shouldReturnNullCallWhenCityNotFound() {
        when(cityPersistenceService.getCityFromDb(city.getCityName())).thenReturn(null);
        City result = cityPersistenceService.getCityFromDb(city.getCityName());

        assertNull(result);
    }

    @Test
    @DisplayName("Должен вернуть из БД город с данными")
    void shouldReturnCity() {
        when(cityPersistenceService.getCityFromDb(city.getCityName())).thenReturn(city);
        City result = cityPersistenceService.getCityFromDb(city.getCityName());

        assertEquals("Minsk", result.getCityName());
    }


    @Test
    void saveCityInDb() {
    }

    @Test
    void updateCityDb() {
    }

    @Test
    void deleteCityDb() {
    }

}
