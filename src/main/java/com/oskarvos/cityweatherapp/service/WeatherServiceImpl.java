package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.client.WeatherIntegrationService;
import com.oskarvos.cityweatherapp.exception.ValidationException;
import com.oskarvos.cityweatherapp.model.dto.external.WeatherApiResponse;
import com.oskarvos.cityweatherapp.model.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import com.oskarvos.cityweatherapp.validation.date.DateValidator;
import com.oskarvos.cityweatherapp.validation.name.CityNameValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final CityRepository cityRepository;
    private final WeatherIntegrationService weatherIntegrationService;
    private final CityNameValidator cityNameValidator;
    private final DateValidator dateValidator;

    public WeatherServiceImpl(CityRepository cityRepository,
                              WeatherIntegrationService weatherIntegrationService,
                              CityNameValidator cityNameValidator,
                              DateValidator dateValidator) {
        this.cityRepository = cityRepository;
        this.weatherIntegrationService = weatherIntegrationService;
        this.cityNameValidator = cityNameValidator;
        this.dateValidator = dateValidator;
    }

    @Override
    public City getActualWeather(String cityName) {
        cityNameValidator.validate(cityName);
        City dbCity = getCityFromDb(cityName);

        if (dbCity == null) {
            return saveCityInDb(cityName);
        } else if (dateValidator.validate(dbCity.getCreatedAt())) {
            return updateCityDb(dbCity, cityName);
        }

        return dbCity;
    }

    private City getCityFromDb(String cityName) {
        try {
            return cityRepository.findByCityName(cityName);
        } catch (Exception e) {
            throw new ValidationException("В БД города не существует!");
        }
    }

    private City saveCityInDb(String cityName) {
        try {
            WeatherApiResponse response = weatherIntegrationService.getWeatherByCityName(cityName);

            City city = new City(response.getCityName(), response.getTemperature());
            cityRepository.save(city);
            return city;
        } catch (Exception e) {
            throw new ValidationException("Не удалось сохранить город в БД город");
        }
    }

    private City updateCityDb(City city, String cityName) {
        try {
            WeatherApiResponse response = weatherIntegrationService.getWeatherByCityName(cityName);

            city.setTemperature(response.getTemperature());
            city.setCreatedAt(LocalDateTime.now());
            return cityRepository.save(city);
        } catch (Exception e) {
            throw new ValidationException("Не удалось обновить данные погоды для города!");
        }
    }

}
