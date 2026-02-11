package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.model.dto.CityListResponse;
import com.oskarvos.cityweatherapp.model.dto.CityResponse;
import com.oskarvos.cityweatherapp.model.dto.external.BuildCityResponse;
import com.oskarvos.cityweatherapp.model.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import com.oskarvos.cityweatherapp.validation.date.DateValidator;
import com.oskarvos.cityweatherapp.validation.name.CityNameValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final WeatherService weatherService;
    private final DateValidator dateValidator;
    private final BuildCityResponse buildCityResponse;
    private final CityNameValidator cityNameValidator;

    public CityServiceImpl(CityRepository cityRepository,
                           WeatherService weatherService,
                           DateValidator dateValidator,
                           BuildCityResponse buildCityResponse,
                           CityNameValidator cityNameValidator) {
        this.cityRepository = cityRepository;
        this.weatherService = weatherService;
        this.dateValidator = dateValidator;
        this.buildCityResponse = buildCityResponse;
        this.cityNameValidator = cityNameValidator;
    }

    @Override
    public CityResponse getCityByName(String cityName) {
        cityNameValidator.validate(cityName); // проверяем входные данные

        City city = weatherService.getActualWeather(cityName); // получаем город от сервера погоды

        if (dateValidator.validate(city.getCreatedAt())) { // проверяем устаревшие ли данные (если устаревшая)
            return buildCityResponse.buildWithWarning(city); // выбрасываем старые данные, с пометкой "устаревшие"
        }
        return buildCityResponse.buildValid(city);
    }

    @Override
    @Transactional
    public CityResponse deleteCityByName(String cityName) {
        cityNameValidator.validate(cityName);
        City city = cityRepository.findByCityName(cityName);
        try {
            cityRepository.delete(city);
            return buildCityResponse.buildDeleteCity(city);
        } catch (Exception e) {
            throw new RuntimeException("В БД нет такого города!", e);
        }
    }

    @Override
    public CityListResponse getAllCities() {
        List<City> favoriteCities = cityRepository.findFavoriteCitiesOrderByCreatedDateDesc();
        List<City> nonFavoriteCities = cityRepository.findNonFavoriteCitiesOrderByCreatedDateDesc();
        return new CityListResponse(favoriteCities, nonFavoriteCities);
    }

}
