package com.oskarvos.cityweatherapp.client;

import com.oskarvos.cityweatherapp.config.WeatherConfig;
import com.oskarvos.cityweatherapp.dto.external.WeatherApiResponse;
import com.oskarvos.cityweatherapp.exception.WeatherApiCityNotFoundException;
import com.oskarvos.cityweatherapp.exception.WeatherApiConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherApiClient { // идет на сервер openweathermap.org и возвращает JSON

    private static final Logger log = LoggerFactory.getLogger(WeatherApiClient.class);

    private final RestTemplate restTemplate;
    private final WeatherConfig weatherConfig;

    public WeatherApiClient(RestTemplate restTemplate, WeatherConfig weatherConfig) {
        this.restTemplate = restTemplate;
        this.weatherConfig = weatherConfig;
    }

    public WeatherApiResponse getWeatherByCityName(String cityName) {
        log.debug("Запрос к API погоды для города '{}'", cityName);

        try {
            String url = String.format("%s?q=%s&appid=%s&units=%s",
                    weatherConfig.getUrl(),
                    cityName,
                    weatherConfig.getKey(),
                    weatherConfig.getUnits());

            return restTemplate.getForObject(url, WeatherApiResponse.class);
        } catch (HttpClientErrorException.NotFound e) {  // Город не найден в API
            throw new WeatherApiCityNotFoundException("Город не найден в API", e);
        } catch (Exception e) { // Другие ошибки (таймаут, проблемы с сетью и т.д.)
            throw new WeatherApiConnectionException("Ошибка при запросе к API погоды (таймаут, проблемы с сетью и т.д.)", e);
        }
    }

}
