package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import com.oskarvos.cityweatherapp.service.mapper.CityResponseMapper;
import com.oskarvos.cityweatherapp.validation.date.DateValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityQueryService {

    private final WeatherServiceImpl weatherService;
    private final DateValidator dateValidator;
    private final CityResponseMapper cityResponseMapper;
    private final CityNameValidationService cityNameValidationService;
    private final CityRepository cityRepository;

    public CityQueryService(WeatherServiceImpl weatherService,
                            DateValidator dateValidator,
                            CityResponseMapper cityResponseMapper,
                            CityNameValidationService cityNameValidationService,
                            CityRepository cityRepository) {
        this.weatherService = weatherService;
        this.dateValidator = dateValidator;
        this.cityResponseMapper = cityResponseMapper;
        this.cityNameValidationService = cityNameValidationService;
        this.cityRepository = cityRepository;
    }

    public CityResponse getCityByName(String cityName) {
        String normalizeCityName = cityNameValidationService.normalizeAndValidate(cityName);

        City city = weatherService.getActualWeather(normalizeCityName); // получаем город от сервера погоды

        if (dateValidator.validate(city.getUpdatedAt())) { // проверяем устаревшие ли данные (если устаревшая)
            return cityResponseMapper.buildWithWarning(city); // выбрасываем старые данные, с пометкой "устаревшие"
        }
        return cityResponseMapper.buildValid(city);
    }

    public CityListResponse getAllCities() {
        List<City> favoriteCities = cityRepository.findFavoriteCitiesOrderByCreatedDateDesc();
        List<City> nonFavoriteCities = cityRepository.findNonFavoriteCitiesOrderByCreatedDateDesc();
        return new CityListResponse(favoriteCities, nonFavoriteCities);
    }

    public CityListResponse getFavoriteCities() {
        return new CityListResponse(cityRepository.findFavoriteCitiesOrderByCreatedDateDesc(), List.of());
    }

}
