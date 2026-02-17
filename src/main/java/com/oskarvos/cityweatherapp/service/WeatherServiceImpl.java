package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.dto.external.WeatherApiResponse;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.exception.DatabaseException;
import com.oskarvos.cityweatherapp.exception.WeatherApiConnectionException;
import com.oskarvos.cityweatherapp.validation.date.OutdatedChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherServiceImpl.class);

    private final OutdatedChecker outdatedChecker;
    private final CityRepositoryService cityRepositoryService;
    private final WeatherApiService weatherApiService;

    public WeatherServiceImpl(OutdatedChecker outdatedChecker,
                              CityRepositoryService cityRepositoryService,
                              WeatherApiService weatherApiService) {
        this.outdatedChecker = outdatedChecker;
        this.cityRepositoryService = cityRepositoryService;
        this.weatherApiService = weatherApiService;
    }

    @Override
    public City getActualWeather(String cityName) {
        log.info("Получение погоды для города: {}", cityName);
        City dbCity = cityRepositoryService.getCityFromDb(cityName);

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

        WeatherApiResponse weatherApiResponse = weatherApiService.sendRequestWeatherApiClient(cityName);
        if (weatherApiResponse != null) {
            return cityRepositoryService.saveCityInDb(cityName, weatherApiResponse.getTemperature());
        }
        return null;
    }

    private City updateCityInDb(City dbCity, String cityName) {
        try {
            WeatherApiResponse weatherApiResponse = weatherApiService.sendRequestWeatherApiClient(cityName);
            if (weatherApiResponse != null) {
                return cityRepositoryService.updateCityDb(dbCity, weatherApiResponse.getTemperature());
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
