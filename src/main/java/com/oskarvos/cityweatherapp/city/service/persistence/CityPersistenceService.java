package com.oskarvos.cityweatherapp.city.service.persistence;

import com.oskarvos.cityweatherapp.city.entity.City;
import com.oskarvos.cityweatherapp.city.repository.CityRepository;
import com.oskarvos.cityweatherapp.common.exception.DatabaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CityPersistenceService {

    private final CityRepository cityRepository;

    public Optional<City> getCityFromDb(String cityName) {
        log.debug("Начинает поиск города {} в БД", cityName);
        return cityRepository.findByCityName(cityName)
                .map(city -> {
                    log.debug("Город {} найден в БД", cityName);
                    return city;
                });
    }

    @Transactional
    public City saveCityInDb(String cityName, Double temperature) {
        log.debug("Начинает сохранение города {} в БД", cityName);
        try {
            City city = new City(cityName, temperature);
            City newCity = cityRepository.save(city);
            log.debug("Город {} успешно сохранен в БД", cityName);
            return newCity;
        } catch (Exception e) {
            log.error("Ошибка при сохранении города {} в БД: {}", cityName, e.getMessage());
            throw new DatabaseException("Не удалось сохранить город в БД город", e);
        }
    }

    @Transactional
    public City updateCityDb(City city, Double temperature) {
        log.debug("Начинает обновление города {} в БД", city.getCityName());
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
        log.debug("Начинает удалять города {} в БД", cityName);
        try {
            Optional<City> city = cityRepository.findByCityName(cityName);
            cityRepository.delete(city.get());
            log.debug("Город {} успешно удален", city.get().getCityName());
        } catch (Exception e) {
            log.error("Ошибка при удалении города в БД {}: {}", cityName, e.getMessage());
            throw new DatabaseException("Не удалось удалить город!", e);
        }
    }

}
