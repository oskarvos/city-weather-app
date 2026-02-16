package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.exception.DatabaseException;
import com.oskarvos.cityweatherapp.exception.WeatherApiConnectionException;
import com.oskarvos.cityweatherapp.validation.date.DateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);

    private final DateValidator dateValidator;
    private final CityRepositoryServiceImpl cityRepositoryService;

    public WeatherServiceImpl(DateValidator dateValidator,
                              CityRepositoryServiceImpl cityRepositoryService) {
        this.dateValidator = dateValidator;
        this.cityRepositoryService = cityRepositoryService;
    }

    @Override
    public City getActualWeather(String cityName) {
        log.info("Получение погоды для города: {}", cityName);
        City dbCity = cityRepositoryService.getCityFromDb(cityName);

        if (dbCity == null) {
            log.debug("Город {} не найден в БД, сохраняем новый", cityName);
            return cityRepositoryService.saveCityInDb(cityName);
        }

        if (dateValidator.validate(dbCity.getUpdatedAt())) {
            try {
                log.info("Обновление устаревших данных для города: {}", cityName);
                return cityRepositoryService.updateCityDb(dbCity);
            } catch (WeatherApiConnectionException e) {
                log.warn("Не удалось обновить данные для города {}, возвращаем устаревшие: {}", cityName, e.getMessage());
                return dbCity;
            } catch (DatabaseException e) {
                log.warn("Ошибка сохранения обновлённых данных для {}, возвращаем устаревшие: {}", cityName, e.getMessage());
                return dbCity;
            }
        }

        log.debug("Город {} найден в БД, данные актуальны", cityName);
        return dbCity;
    }

}
