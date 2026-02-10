package com.oskarvos.cityweatherapp.client;

import com.oskarvos.cityweatherapp.config.WeatherConfig;
import com.oskarvos.cityweatherapp.model.dto.external.WeatherApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherApiClient { // идет на сервер openweathermap.org и возвращает JSON

    private final RestTemplate restTemplate;
    private final WeatherConfig weatherConfig;

    public WeatherApiClient(RestTemplate restTemplate, WeatherConfig weatherConfig) {
        this.restTemplate = restTemplate;
        this.weatherConfig = weatherConfig;
    }

    public WeatherApiResponse getWeatherByCityName(String cityName) {
        try {
            String url = String.format("%s?q=%s&appid=%s&units=%s",
                    weatherConfig.getUrl(),
                    cityName,
                    weatherConfig.getKey(),
                    weatherConfig.getUnits());

            return restTemplate.getForObject(url, WeatherApiResponse.class);
        } catch (HttpClientErrorException.NotFound e) {  // Город не найден в API
            return null;
        } catch (Exception e) { // Другие ошибки (таймаут, проблемы с сетью и т.д.)
            throw new RuntimeException("Ошибка при запросе к API погоды", e);
        }
    }

}
