package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.dto.external.WeatherApiResponse;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.exception.DatabaseException;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CityRepositoryServiceImpl implements CityRepositoryService {

    private static final Logger log = LoggerFactory.getLogger(CityRepositoryServiceImpl.class);

    private final CityRepository cityRepository;
    private final WeatherApiService weatherApiService;

    public CityRepositoryServiceImpl(CityRepository cityRepository,
                                     WeatherApiService weatherApiService) {
        this.cityRepository = cityRepository;
        this.weatherApiService = weatherApiService;
    }

    @Override
    public City getCityFromDb(String cityName) {
        log.debug("Начинает поиск города {} в БД", cityName);
        City city = cityRepository.findByCityName(cityName);

        if (city != null) {
            log.debug("Город {} найден в БД", cityName);
        }
        return city;
    }

    @Override
    @Transactional
    public City saveCityInDb(String cityName) {
        WeatherApiResponse response = weatherApiService.sendRequestWeatherApiClient(cityName);

        try {
            City city = new City(cityName, response.getTemperature());
            log.info("Город {} успешно сохранен в БД", cityName);
            return cityRepository.save(city);
        } catch (Exception e) {
            log.error("Ошибка при сохранении города {} в БД: {}", cityName, e.getMessage());
            throw new DatabaseException("Не удалось сохранить город в БД город", e);
        }
    }

    @Override
    @Transactional
    public City updateCityDb(City city) {
        WeatherApiResponse response = weatherApiService.sendRequestWeatherApiClient(city.getCityName());

        try {
            city.setTemperature(response.getTemperature());
            city.setUpdatedAt(LocalDateTime.now());
            City updateCity = cityRepository.save(city);
            log.info("Данные для города {} успешно обновлены", city.getCityName());
            return updateCity;
        } catch (Exception e) {
            log.error("Ошибка при обновлении данных в БД для города {}: {}", city.getCityName(), e.getMessage());
            throw new DatabaseException("Не удалось обновить данные погоды для города!", e);
        }
    }

}
