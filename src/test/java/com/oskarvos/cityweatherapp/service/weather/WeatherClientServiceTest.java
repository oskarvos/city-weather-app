package com.oskarvos.cityweatherapp.service.weather;

import com.oskarvos.cityweatherapp.client.WeatherApiClient;
import com.oskarvos.cityweatherapp.dto.external.WeatherApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherClientServiceTest {

    @Mock
    WeatherApiResponse weatherApiResponse;
    @Mock
    WeatherApiClient weatherApiClient;

    @InjectMocks
    WeatherClientService weatherClientService;

    private static final String CITY_NAME = "Minsk";
    private static final Double TEMPERATURE = 25.5;

    @Test
    @DisplayName("Должен вернуть положительный ответ существующего города в API погоды")
    void shouldReturnSuccessWhenActualApiCityName() {
        when(weatherApiResponse.getCityName()).thenReturn(CITY_NAME);
        when(weatherApiResponse.getTemperature()).thenReturn(TEMPERATURE);
        when(weatherApiClient.getWeatherByCityName(CITY_NAME)).thenReturn(weatherApiResponse);

        WeatherApiResponse result = weatherClientService.sendRequestWeatherApiClient(CITY_NAME);

        assertAll("Должен вернуть положительный ответ",
                () -> assertNotNull(result),
                () -> assertEquals(CITY_NAME, result.getCityName()),
                () -> assertEquals(TEMPERATURE, result.getTemperature())
        );

        verify(weatherApiClient, times(1)).getWeatherByCityName(CITY_NAME);
    }

    @Test
    @DisplayName("Должен вернуть null, город не существует в API")
    void shouldReturnNullWhenNonCityName() {
        when(weatherApiClient.getWeatherByCityName(CITY_NAME)).thenReturn(null);

        WeatherApiResponse result = weatherClientService.sendRequestWeatherApiClient(CITY_NAME);

        assertNull(result);

        verify(weatherApiClient, times(1)).getWeatherByCityName(CITY_NAME);
    }

}
