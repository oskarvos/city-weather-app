package com.oskarvos.cityweatherapp.weather.service;

import com.oskarvos.cityweatherapp.weather.client.WeatherApiClient;
import com.oskarvos.cityweatherapp.weather.dto.external.WeatherApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherClientService {

    private final WeatherApiClient weatherApiClient;

    public WeatherApiResponse sendRequestWeatherApiClient(String cityName) {
        log.debug("Запрос к API погоды для нового города: {}", cityName);
        WeatherApiResponse response = weatherApiClient.getWeatherByCityName(cityName);
        log.debug("Получен ответ от API погоды для города: {}", cityName);
        return response;
    }

}
