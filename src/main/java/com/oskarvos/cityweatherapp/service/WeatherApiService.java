package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.client.WeatherApiClient;
import com.oskarvos.cityweatherapp.dto.external.WeatherApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WeatherApiService {

    private static final Logger log = LoggerFactory.getLogger(WeatherApiService.class);

    private final WeatherApiClient weatherApiClient;

    public WeatherApiService(WeatherApiClient weatherApiClient) {
        this.weatherApiClient = weatherApiClient;
    }

    public WeatherApiResponse sendRequestWeatherApiClient(String cityName) {
        log.debug("Запрос к API погоды для нового города: {}", cityName);
        WeatherApiResponse response = weatherApiClient.getWeatherByCityName(cityName);
        log.debug("Получен ответ от API погоды для города: {}, температура {}", cityName, response.getTemperature());
        return response;
    }

}
