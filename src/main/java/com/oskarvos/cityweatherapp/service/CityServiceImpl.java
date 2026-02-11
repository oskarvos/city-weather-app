package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.config.WeatherConfig;
import com.oskarvos.cityweatherapp.exception.NotFoundException;
import com.oskarvos.cityweatherapp.exception.ValidationException;
import com.oskarvos.cityweatherapp.model.dto.CityListResponse;
import com.oskarvos.cityweatherapp.model.dto.CityResponse;
import com.oskarvos.cityweatherapp.model.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final WeatherService weatherService;
    private final WeatherConfig weatherConfig;

    public CityServiceImpl(CityRepository cityRepository,
                           WeatherService weatherService,
                           WeatherConfig weatherConfig) {
        this.cityRepository = cityRepository;
        this.weatherService = weatherService;
        this.weatherConfig = weatherConfig;
    }

    @Override
    public CityResponse getCityByName(String cityName) {
        validateCityName(cityName); // проверяем входные данные

        try {
            City city = getValidatedCity(cityName); // получаем с сервера openweathermap.org
            // и проверяем получен ли валидный ответ

            if (isWeatherStale(city)) { // проверяем устаревшие ли данные
                return buildResponse(city);
            }
            return buildResponse(city);
        } catch (Exception e) {
            e.printStackTrace();
            return new CityResponse("Город '" + cityName + "' не найден!"); // выводим сообщение клиенту,
            // что на сервере такого города не существует
        }
    }

    @Override
    @Transactional
    public CityResponse deleteCityByName(String cityName) {
        validateCityName(cityName);
        City city = cityRepository.findByCityName(cityName);
        validationCityAndName(city);
        cityRepository.delete(city);

        return buildResponse(city);
    }

    @Override
    public CityListResponse getAllCities() {
        List<City> favoriteCities = cityRepository.findFavoriteCitiesOrderByCreatedDateDesc();
        List<City> nonFavoriteCities = cityRepository.findNonFavoriteCitiesOrderByCreatedDateDesc();
        return new CityListResponse(favoriteCities, nonFavoriteCities);
    }

    private void validateCityName(String cityName) {
        if (cityName == null || cityName.trim().isEmpty()) {
            throw new ValidationException("Необходимо ввести название города!");
        }
    }

    private City getValidatedCity(String cityName) {
        City city = weatherService.getActualWeather(cityName);
        validationCityAndName(city);
        return city;
    }

    private void validationCityAndName(City city) {
        if (city == null || city.getCityName() == null) {
            throw new NotFoundException("Город не найден!");
        }
    }

    private boolean isWeatherStale(City city) {
        if (city.getCreatedAt() == null) return true;

        long hoursBetween = ChronoUnit.HOURS.between(
                city.getCreatedAt(),
                LocalDateTime.now());
        return hoursBetween > weatherConfig.getCacheDuration();
    }

    private CityResponse buildResponse(City city) {
        if (city == null) return null;
        if (isWeatherStale(city)) {
            return new CityResponse(
                    city.getId(),
                    city.getCityName(),
                    city.getTemperature(),
                    city.getCreatedAt(),
                    "Данные температуры не актуальны"
            );
        }
        return new CityResponse(city.getId(), city.getCityName(), city.getTemperature());
    }

}
