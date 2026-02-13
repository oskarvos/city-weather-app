package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.client.WeatherApiClient;
import com.oskarvos.cityweatherapp.dto.external.WeatherApiResponse;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.exception.DatabaseException;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import com.oskarvos.cityweatherapp.validation.date.DateValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WeatherService {

    private final CityRepository cityRepository;
    private final WeatherApiClient weatherApiClient;
    private final DateValidator dateValidator;

    public WeatherService(CityRepository cityRepository,
                          WeatherApiClient weatherApiClient,
                          DateValidator dateValidator) {
        this.cityRepository = cityRepository;
        this.weatherApiClient = weatherApiClient;
        this.dateValidator = dateValidator;
    }

    public City getActualWeather(String cityName) {
        City dbCity = getCityFromDb(cityName);

        if (dbCity == null) {
            return saveCityInDb(cityName);
        } else if (dateValidator.validate(dbCity.getCreatedAt())) {
            return updateCityDb(dbCity, cityName);
        }

        return dbCity;
    }

    private City getCityFromDb(String cityName) {
        return cityRepository.findByCityName(cityName);
    }

    private City saveCityInDb(String cityName) {
        WeatherApiResponse response = weatherApiClient.getWeatherByCityName(cityName);

        try {
            City city = new City(cityName, response.getTemperature());
            return cityRepository.save(city);
        } catch (Exception e) {
            throw new DatabaseException("Не удалось сохранить город в БД город", e);
        }
    }

    private City updateCityDb(City city, String cityName) {
        WeatherApiResponse response = weatherApiClient.getWeatherByCityName(cityName);

        try {
            city.setTemperature(response.getTemperature());
            city.setCreatedAt(LocalDateTime.now());
            return cityRepository.save(city);
        } catch (Exception e) {
            throw new DatabaseException("Не удалось обновить данные погоды для города!", e);
        }
    }

}
