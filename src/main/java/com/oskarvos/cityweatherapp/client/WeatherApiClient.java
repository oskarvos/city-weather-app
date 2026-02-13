package com.oskarvos.cityweatherapp.client;

import com.oskarvos.cityweatherapp.config.WeatherConfig;
import com.oskarvos.cityweatherapp.dto.external.WeatherApiResponse;
import com.oskarvos.cityweatherapp.exception.WeatherApiException;
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
            throw new WeatherApiException("Город не найден в API", e);
        } catch (Exception e) { // Другие ошибки (таймаут, проблемы с сетью и т.д.)
            throw new WeatherApiException("Ошибка при запросе к API погоды (таймаут, проблемы с сетью и т.д.)", e);
        }
    }

}
