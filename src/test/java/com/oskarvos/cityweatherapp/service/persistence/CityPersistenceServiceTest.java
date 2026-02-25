package com.oskarvos.cityweatherapp.service.persistence;

import com.oskarvos.cityweatherapp.city.entity.City;
import com.oskarvos.cityweatherapp.common.exception.DatabaseException;
import com.oskarvos.cityweatherapp.city.repository.CityRepository;
import com.oskarvos.cityweatherapp.city.service.persistence.CityPersistenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityPersistenceServiceTest {

    @Mock
    CityRepository cityRepository;

    @InjectMocks
    CityPersistenceService cityPersistenceService;

    private City city;
    private static final Long ID = 1L;
    private static final String CITY_NAME = "Minsk";
    private static final Double TEMPERATURE = 22.3;
    private static final LocalDateTime UPDATE_AT = LocalDateTime.of(
            2026, 2, 19, 20, 30, 45, 123456789);

    @BeforeEach
    void setUp() {
        city = new City();
        city.setId(ID);
        city.setCityName(CITY_NAME);
        city.setTemperature(TEMPERATURE);
        city.setUpdatedAt(UPDATE_AT);
    }

    @Test
    @DisplayName("Должен вернуть null, если город не найден в БД")
    void shouldReturnNullWhenCityNotFound() {
        when(cityPersistenceService.getCityFromDb(city.getCityName())).thenReturn(null);

        City result = cityPersistenceService.getCityFromDb(city.getCityName());

        assertNull(result, "Для несуществующего города должен вернуться Null");

        verify(cityRepository, times(1)).findByCityName(city.getCityName());
        verifyNoMoreInteractions(cityRepository); // Убеждаемся, что других вызовов репозитория не было
    }

    @Test
    @DisplayName("Должен вернуть город с данными, если он найден в БД")
    void shouldReturnCityWhenFound() {
        when(cityPersistenceService.getCityFromDb(city.getCityName())).thenReturn(city);

        City result = cityPersistenceService.getCityFromDb(city.getCityName());

        assertAll("Проверка возвращаемого города",
                () -> assertNotNull(result, "Город не должен быть null"),
                () -> assertEquals(ID, result.getId(), "Id города не совпадает"),
                () -> assertEquals(CITY_NAME, result.getCityName(), "Название города не совпадает"),
                () -> assertEquals(22.3, result.getTemperature(), "Температура города не совпадает"),
                () -> assertEquals(UPDATE_AT, result.getUpdatedAt(), "Время обновления не совпадает"));

        verify(cityRepository, times(1)).findByCityName(city.getCityName());
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    @DisplayName("Должен обрабатывать случай, когда репозиторий возвращает null")
    void shouldReturnNullFromRepository() {
        when(cityPersistenceService.getCityFromDb(CITY_NAME)).thenReturn(null);

        City result = cityPersistenceService.getCityFromDb(CITY_NAME);

        assertNull(result);

        verify(cityRepository).findByCityName(CITY_NAME);
    }

    @Test
    @DisplayName("Должен пробрасывать исключение от репозитория при попытке сохранения в БД")
    void shouldPropagateRepositoryExceptionWhenSaveCityDb() {
        when(cityPersistenceService.getCityFromDb(city.getCityName()))
                .thenThrow(new DatabaseException("Database error", new RuntimeException()));

        DatabaseException exception = assertThrows(
                DatabaseException.class,
                () -> cityPersistenceService.getCityFromDb(CITY_NAME));

        assertEquals("Database error", exception.getMessage());

        verify(cityRepository).findByCityName(CITY_NAME);
    }


    @Test
    @DisplayName("Должен вернуть null, если город не сохранился в БД")
    void shouldResultNullWhenRepositorySaveReturnsNull() {
        when(cityRepository.save(any(City.class))).thenReturn(null);

        City result = cityPersistenceService.saveCityInDb(CITY_NAME, TEMPERATURE);

        assertNull(result);

        verify(cityRepository, times(1)).save(any(City.class));
        verifyNoMoreInteractions(cityRepository);
    }

    @Test
    @DisplayName("Должен вернуть город с данными, если сохранил город в БД")
    void shouldReturnCityWhenSavedCityInDb() {
        when(cityRepository.save(any(City.class))).thenReturn(city);

        City result = cityPersistenceService.saveCityInDb(CITY_NAME, TEMPERATURE);

        assertAll("Должен вернуть данные города сохранившего в БД",
                () -> assertNotNull(result),
                () -> assertEquals(ID, result.getId()),
                () -> assertEquals(CITY_NAME, result.getCityName()),
                () -> assertEquals(TEMPERATURE, result.getTemperature()),
                () -> assertEquals(UPDATE_AT, result.getUpdatedAt()));

        verify(cityRepository, times(1)).save(any(City.class));
        verifyNoMoreInteractions(cityRepository);
    }

}
