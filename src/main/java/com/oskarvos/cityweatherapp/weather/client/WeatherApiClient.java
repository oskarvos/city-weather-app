package com.oskarvos.cityweatherapp.weather.client;

import com.oskarvos.cityweatherapp.common.config.WeatherConfig;
import com.oskarvos.cityweatherapp.common.exception.WeatherApiCityNotFoundException;
import com.oskarvos.cityweatherapp.common.exception.WeatherApiConnectionException;
import com.oskarvos.cityweatherapp.weather.dto.external.WeatherApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Component
public class WeatherApiClient {

    private final RestTemplate restTemplate;
    private final WeatherConfig weatherConfig;

    public WeatherApiResponse getWeatherByCityName(String cityName) {
        log.debug("Запрос к API погоды для города '{}'", cityName);

        try {
            String url = String.format("%s?q=%s&appid=%s&units=%s",
                    weatherConfig.getUrl(),
                    cityName,
                    weatherConfig.getKey(),
                    weatherConfig.getUnits());

            return restTemplate.getForObject(url, WeatherApiResponse.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new WeatherApiCityNotFoundException("Город не найден в API", e);
        } catch (Exception e) {
            throw new WeatherApiConnectionException("Ошибка при запросе к API погоды (таймаут, проблемы с сетью и т.д.)", e);
        }
    }

}
