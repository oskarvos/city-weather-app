package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.dto.response.ValidationError;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.exception.CityNotFoundException;
import com.oskarvos.cityweatherapp.exception.CityValidationException;
import com.oskarvos.cityweatherapp.exception.DatabaseException;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import com.oskarvos.cityweatherapp.service.mapper.CityResponseMapper;
import com.oskarvos.cityweatherapp.validation.date.DateValidator;
import com.oskarvos.cityweatherapp.validation.name.CityNameValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final WeatherService weatherService;
    private final DateValidator dateValidator;
    private final CityResponseMapper cityResponseMapper;
    private final List<CityNameValidator> cityNameValidators;

    public CityService(CityRepository cityRepository,
                       WeatherService weatherService,
                       DateValidator dateValidator,
                       CityResponseMapper cityResponseMapper,
                       List<CityNameValidator> cityNameValidators) {
        this.cityRepository = cityRepository;
        this.weatherService = weatherService;
        this.dateValidator = dateValidator;
        this.cityResponseMapper = cityResponseMapper;
        this.cityNameValidators = cityNameValidators;
    }

    public CityResponse getCityByName(String cityName) {
        List<ValidationError> errors = collectValidationErrors(cityName);
        if (!errors.isEmpty()) {
            throw new CityValidationException(errors);
        }

        City city = weatherService.getActualWeather(cityName); // получаем город от сервера погоды

        if (dateValidator.validate(city.getCreatedAt())) { // проверяем устаревшие ли данные (если устаревшая)
            return cityResponseMapper.buildWithWarning(city); // выбрасываем старые данные, с пометкой "устаревшие"
        }
        return cityResponseMapper.buildValid(city);
    }

    @Transactional
    public CityResponse deleteCityByName(String cityName) {
        List<ValidationError> errors = collectValidationErrors(cityName);
        if (!errors.isEmpty()) {
            throw new CityValidationException(errors);
        }

        City city = cityRepository.findByCityName(cityName);
        if (city == null) {
            throw new CityNotFoundException("Город с таким названием не найден");
        }

        try {
            cityRepository.delete(city);
            return cityResponseMapper.buildDeleteCity(city);
        } catch (Exception e) {
            throw new DatabaseException("Ошибка при удалении города из БД", e);
        }
    }

    public CityListResponse getAllCities() {
        List<City> favoriteCities = cityRepository.findFavoriteCitiesOrderByCreatedDateDesc();
        List<City> nonFavoriteCities = cityRepository.findNonFavoriteCitiesOrderByCreatedDateDesc();
        return new CityListResponse(favoriteCities, nonFavoriteCities);
    }

    private List<ValidationError> collectValidationErrors(String cityName) {
        return cityNameValidators.stream()
                .map(v -> v.validate(cityName))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

}
