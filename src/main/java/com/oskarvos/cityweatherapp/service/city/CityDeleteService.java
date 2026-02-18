package com.oskarvos.cityweatherapp.service.city;

import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.exception.CityNotFoundException;
import com.oskarvos.cityweatherapp.exception.DatabaseException;
import com.oskarvos.cityweatherapp.service.mapper.CityMapper;
import com.oskarvos.cityweatherapp.service.persistence.CityPersistenceService;
import com.oskarvos.cityweatherapp.service.validation.CityNameValidationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CityDeleteService {

    private final CityMapper cityMapper;
    private final CityNameValidationService cityNameValidationService;
    private final CityPersistenceService cityPersistenceService;

    public CityDeleteService(CityMapper cityMapper,
                             CityNameValidationService cityNameValidationService,
                             CityPersistenceService cityPersistenceService) {
        this.cityMapper = cityMapper;
        this.cityNameValidationService = cityNameValidationService;
        this.cityPersistenceService = cityPersistenceService;
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
        City city = cityPersistenceService.getCityFromDb(cityName);
        if (city == null) {
            throw new CityNotFoundException("Город с таким названием не найден");
        }
        return city;
    }

    private CityResponse deleteCityFromDb(City city) {
        try {
            cityPersistenceService.deleteCityDb(city.getCityName());
            return cityMapper.buildWithMessage(city, "Город из БД удален");
        } catch (Exception e) {
            throw new DatabaseException("Ошибка при удалении города из БД", e);
        }
    }

}
