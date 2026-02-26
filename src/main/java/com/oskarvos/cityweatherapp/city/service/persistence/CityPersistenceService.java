package com.oskarvos.cityweatherapp.city.service.persistence;

import com.oskarvos.cityweatherapp.city.entity.City;
import com.oskarvos.cityweatherapp.common.exception.DatabaseException;
import com.oskarvos.cityweatherapp.city.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CityPersistenceService {

    private static final Logger log = LoggerFactory.getLogger(CityPersistenceService.class);

    private final CityRepository cityRepository;

    public CityPersistenceService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getCityFromDb(String cityName) {
        log.debug("Начинает поиск города {} в БД", cityName);
        City city = cityRepository.findByCityName(cityName);

        if (city != null) {
            log.debug("Город {} найден в БД", cityName);
        }
        return city;
    }

    @Transactional
    public City saveCityInDb(String cityName, Double temperature) {
        try {
            City city = new City(cityName, temperature);
            log.info("Город {} успешно сохранен в БД", cityName);
            return cityRepository.save(city);
        } catch (Exception e) {
            log.error("Ошибка при сохранении города {} в БД: {}", cityName, e.getMessage());
            throw new DatabaseException("Не удалось сохранить город в БД город", e);
        }
    }

    @Transactional
    public City updateCityDb(City city, Double temperature) {
        try {
            city.setTemperature(temperature);
            city.setUpdatedAt(LocalDateTime.now());
            City updateCity = cityRepository.save(city);
            log.info("Данные для города {} успешно обновлены", city.getCityName());
            return updateCity;
        } catch (Exception e) {
            log.error("Ошибка при обновлении данных в БД для города {}: {}", city.getCityName(), e.getMessage());
            throw new DatabaseException("Не удалось обновить данные погоды для города!", e);
        }
    }

    @Transactional
    public void deleteCityDb(String cityName) {
        try {
            City city = cityRepository.findByCityName(cityName);
            cityRepository.delete(city);
            log.info("Город {} успешно удален", city.getCityName());
        } catch (Exception e) {
            log.error("Ошибка при удалении города в БД {}: {}", cityName, e.getMessage());
            throw new DatabaseException("Не удалось удалить город!", e);
        }
    }

}
