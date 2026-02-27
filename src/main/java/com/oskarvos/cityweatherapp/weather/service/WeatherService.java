package com.oskarvos.cityweatherapp.weather.service;

import com.oskarvos.cityweatherapp.weather.dto.external.WeatherApiResponse;
import com.oskarvos.cityweatherapp.city.entity.City;
import com.oskarvos.cityweatherapp.common.exception.DatabaseException;
import com.oskarvos.cityweatherapp.common.exception.WeatherApiConnectionException;
import com.oskarvos.cityweatherapp.city.service.persistence.CityPersistenceService;
import com.oskarvos.cityweatherapp.common.validation.date.OutdatedChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    private final OutdatedChecker outdatedChecker;
    private final CityPersistenceService cityPersistenceService;
    private final WeatherClientService weatherClientService;

    public WeatherService(OutdatedChecker outdatedChecker,
                          CityPersistenceService cityPersistenceService,
                          WeatherClientService weatherClientService) {
        this.outdatedChecker = outdatedChecker;
        this.cityPersistenceService = cityPersistenceService;
        this.weatherClientService = weatherClientService;
    }

    public City getActualWeather(String cityName) {
        log.info("Получение погоды для города: {}", cityName);
        City dbCity = cityPersistenceService.getCityFromDb(cityName);

        if (dbCity == null) {
            log.debug("Город {} не найден в БД", cityName);
            return saveNewCity(cityName);
        }

        if (outdatedChecker.isOutdated(dbCity.getUpdatedAt())) {
            log.info("Обновление устаревших данных для города: {}", cityName);
            return updateCityInDb(dbCity, cityName);
        }

        log.debug("Город {} найден в БД, данные актуальны", cityName);
        return dbCity;
    }

    private City saveNewCity(String cityName) {
        WeatherApiResponse weatherApiResponse = weatherClientService.sendRequestWeatherApiClient(cityName);
        if (weatherApiResponse != null) {
            return cityPersistenceService.saveCityInDb(cityName, weatherApiResponse.getTemperature());
        }
        return null;
    }

    private City updateCityInDb(City dbCity, String cityName) {
        try {
            WeatherApiResponse weatherApiResponse = weatherClientService.sendRequestWeatherApiClient(cityName);
            if (weatherApiResponse != null) {
                return cityPersistenceService.updateCityDb(dbCity, weatherApiResponse.getTemperature());
            }
            return dbCity;
        } catch (WeatherApiConnectionException e) {
            log.warn("Не удалось обновить данные для города {}, возвращаем устаревшие: {}", cityName, e.getMessage());
            return dbCity;
        } catch (DatabaseException e) {
            log.warn("Ошибка сохранения обновлённых данных для {}, возвращаем устаревшие: {}", cityName, e.getMessage());
            return dbCity;
        }
    }

}
