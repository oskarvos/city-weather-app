package com.oskarvos.cityweatherapp.weather.service;

import com.oskarvos.cityweatherapp.city.entity.City;
import com.oskarvos.cityweatherapp.city.service.persistence.CityPersistenceService;
import com.oskarvos.cityweatherapp.common.exception.DatabaseException;
import com.oskarvos.cityweatherapp.common.exception.WeatherApiConnectionException;
import com.oskarvos.cityweatherapp.common.validation.date.OutdatedChecker;
import com.oskarvos.cityweatherapp.weather.dto.external.WeatherApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final OutdatedChecker outdatedChecker;
    private final CityPersistenceService cityPersistenceService;
    private final WeatherClientService weatherClientService;

    public City getActualWeather(String cityName) {
        log.info("Получение погоды для города: {}", cityName);

        return cityPersistenceService.getCityFromDb(cityName)
                .map(city -> {
                    if (outdatedChecker.isOutdated(city.getUpdatedAt())) {
                        log.info("Обновление устаревших данных для города: {}", cityName);
                        return updateCityInDb(city, cityName);
                    }
                    log.debug("Город {} найден в БД, данные актуальны", cityName);
                    return city;
                })
                .orElseGet(() -> {
                    log.debug("Город {} не найден в БД", cityName);
                    return saveNewCity(cityName);
                });

//        City dbCity = cityPersistenceService.getCityFromDb(cityName);
//
//        if (dbCity == null) {
//        }
//
//        if (outdatedChecker.isOutdated(dbCity.getUpdatedAt())) {
//            log.info("Обновление устаревших данных для города: {}", cityName);
//            return updateCityInDb(dbCity, cityName);
//        }
//
//        log.debug("Город {} найден в БД, данные актуальны", cityName);
//        return dbCity;
    }

    private City saveNewCity(String cityName) {
        WeatherApiResponse weatherApiResponse = weatherClientService.sendRequestWeatherApiClient(cityName);
        return cityPersistenceService.saveCityInDb(cityName, weatherApiResponse.getTemperature());
    }

    private City updateCityInDb(City dbCity, String cityName) {
        try {
            WeatherApiResponse weatherApiResponse = weatherClientService.sendRequestWeatherApiClient(cityName);
            return cityPersistenceService.updateCityDb(dbCity, weatherApiResponse.getTemperature());
        } catch (WeatherApiConnectionException e) {
            log.warn("Не удалось обновить данные для города {}, возвращаем устаревшие: {}", cityName, e.getMessage());
            return dbCity;
        } catch (DatabaseException e) {
            log.warn("Ошибка сохранения обновлённых данных для {}, возвращаем устаревшие: {}", cityName, e.getMessage());
            return dbCity;
        }
    }

}
