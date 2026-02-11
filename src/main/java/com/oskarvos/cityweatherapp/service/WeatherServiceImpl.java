package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.client.WeatherApiClient;
import com.oskarvos.cityweatherapp.config.WeatherConfig;
import com.oskarvos.cityweatherapp.model.dto.external.WeatherApiResponse;
import com.oskarvos.cityweatherapp.model.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
    public class WeatherServiceImpl implements WeatherService {

    private final CityRepository cityRepository;
    private final WeatherApiClient weatherApiClient;
    private final WeatherConfig weatherConfig;

    public WeatherServiceImpl(CityRepository cityRepository,
                              WeatherApiClient weatherApiClient,
                              WeatherConfig weatherConfig) {
        this.cityRepository = cityRepository;
        this.weatherApiClient = weatherApiClient;
        this.weatherConfig = weatherConfig;
    }

    @Override
    public City getActualWeather(String cityName) {
        City dbcity = cityRepository.findByCityName(cityName);

        if (dbcity == null) {
            return saveCityInDb(cityName);
        } else if (isCacheExpired(dbcity.getCreatedAt(), weatherConfig.getCacheDuration())) {
            return updateCityDb(dbcity, cityName);
        }
        return dbcity;
    }

    private City saveCityInDb(String cityName) {
        try {
            WeatherApiResponse response = weatherApiClient.getWeatherByCityName(cityName);

            City city = new City(response.getCityName(), response.getTemperature());
            cityRepository.save(city);
            return city;
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать город: " + cityName, e);
        }
    }

    private City updateCityDb(City city, String cityName) {
        try {
            WeatherApiResponse response = weatherApiClient.getWeatherByCityName(cityName);

            city.setTemperature(response.getTemperature());
            city.setCreatedAt(LocalDateTime.now());
            return cityRepository.save(city);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось обновить данные погоды для города: " + cityName, e);
        }
    }

    private boolean isCacheExpired(LocalDateTime createdAt, Long cacheDuration) {
        long betweenHours = ChronoUnit.HOURS.between(createdAt, LocalDateTime.now());
        return betweenHours > cacheDuration;
    }

}
