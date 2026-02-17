package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.service.mapper.CityResponseMapper;
import com.oskarvos.cityweatherapp.validation.date.OutdatedChecker;
import org.springframework.stereotype.Service;

@Service
public class CityQueryService {

    private final WeatherService weatherService;
    private final OutdatedChecker outdatedChecker;
    private final CityResponseMapper cityResponseMapper;
    private final CityNameValidationService cityNameValidationService;

    public CityQueryService(WeatherService weatherService,
                            OutdatedChecker outdatedChecker,
                            CityResponseMapper cityResponseMapper,
                            CityNameValidationService cityNameValidationService) {
        this.weatherService = weatherService;
        this.outdatedChecker = outdatedChecker;
        this.cityResponseMapper = cityResponseMapper;
        this.cityNameValidationService = cityNameValidationService;
    }

    public CityResponse getCityByName(String cityName) {
        String normalizeCityName = normalizeCityName(cityName);
        City city = getActualWeather(normalizeCityName); // получаем город от сервера погоды
        return outdateChecker(city);
    }

    private String normalizeCityName(String cityName) {
        return cityNameValidationService.normalizeAndValidate(cityName);
    }

    private City getActualWeather(String cityName) {
        return weatherService.getActualWeather(cityName);
    }

    private CityResponse outdateChecker(City city) {
        return outdatedChecker.isOutdated(city.getUpdatedAt())
                ? cityResponseMapper.buildWithWarning(city)
                : cityResponseMapper.buildValid(city);
    }

}
