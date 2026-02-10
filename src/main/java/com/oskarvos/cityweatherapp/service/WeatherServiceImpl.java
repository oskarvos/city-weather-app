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
        City city = cityRepository.findByCityName(cityName);
        if (city != null) {
            long betweenHours = ChronoUnit.HOURS.between(city.getCreatedAt(), LocalDateTime.now());

            if (betweenHours > weatherConfig.getCacheDuration()) {
                WeatherApiResponse weatherApiResponse = weatherApiClient.getWeatherByCityName(cityName);

                city.setTemperature(weatherApiResponse.getTemperature());
                city.setCreatedAt(LocalDateTime.now());
                cityRepository.save(city);
                return city;
            } else {
                return city;
            }
        } else {
            WeatherApiResponse weatherApiResponse = weatherApiClient.getWeatherByCityName(cityName);
            City newCity = new City();
            newCity.setCityName(weatherApiResponse.getCityName());
            newCity.setTemperature(weatherApiResponse.getTemperature());
            cityRepository.save(newCity);
            return newCity;
        }
    }

}
