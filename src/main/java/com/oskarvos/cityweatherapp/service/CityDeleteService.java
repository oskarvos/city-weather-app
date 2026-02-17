package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.exception.CityNotFoundException;
import com.oskarvos.cityweatherapp.exception.DatabaseException;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import com.oskarvos.cityweatherapp.service.mapper.CityResponseMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CityDeleteService {

    private final CityRepository cityRepository;
    private final CityResponseMapper cityResponseMapper;
    private final CityNameValidationService cityNameValidationService;

    public CityDeleteService(CityRepository cityRepository,
                             CityResponseMapper cityResponseMapper,
                             CityNameValidationService cityNameValidationService) {
        this.cityRepository = cityRepository;
        this.cityResponseMapper = cityResponseMapper;
        this.cityNameValidationService = cityNameValidationService;
    }

    @Transactional
    public CityResponse deleteCityByName(String cityName) {
        String normalizeCityName = normalizeCityName(cityName);
        City city = findCityFromDb(normalizeCityName);
        return deleteCityFromDb(city);
    }

    private String normalizeCityName(String cityName) {
        return cityNameValidationService.normalizeAndValidate(cityName);
    }

    private City findCityFromDb(String cityName) {
        City city = cityRepository.findByCityName(cityName);
        if (city == null) {
            throw new CityNotFoundException("Город с таким названием не найден");
        }
        return city;
    }

    private CityResponse deleteCityFromDb(City city) {
        try {
            cityRepository.delete(city);
            return cityResponseMapper.buildDeleteCity(city);
        } catch (Exception e) {
            throw new DatabaseException("Ошибка при удалении города из БД", e);
        }
    }

}
