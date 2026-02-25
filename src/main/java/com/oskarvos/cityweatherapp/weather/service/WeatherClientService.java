package com.oskarvos.cityweatherapp.weather.service;

import com.oskarvos.cityweatherapp.weather.client.WeatherApiClient;
import com.oskarvos.cityweatherapp.weather.dto.external.WeatherApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WeatherClientService {

    private static final Logger log = LoggerFactory.getLogger(WeatherClientService.class);

    private final WeatherApiClient weatherApiClient;

    public WeatherClientService(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    public WeatherApiResponse sendRequestWeatherApiClient(String cityName) {
        log.debug("Запрос к API погоды для нового города: {}", cityName);
        WeatherApiResponse response = weatherApiClient.getWeatherByCityName(cityName);
        log.debug("Получен ответ от API погоды для города: {}", cityName);
        return response;
    }

}
