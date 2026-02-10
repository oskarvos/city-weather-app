package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.model.dto.CityListResponse;
import com.oskarvos.cityweatherapp.model.dto.CityResponse;
import com.oskarvos.cityweatherapp.model.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final WeatherServiceImpl weatherServiceImpl;

    public CityServiceImpl(CityRepository cityRepository, WeatherServiceImpl weatherServiceImpl) {
        this.cityRepository = cityRepository;
        this.weatherServiceImpl = weatherServiceImpl;
    }

    @Override
    public CityResponse getCityByName(String cityName) {
        if (cityName == null) {
            return new CityResponse("Необходимо ввести название города!");
        }

        City city = weatherServiceImpl.getActualWeather(cityName);
        if (city.getCityName() == null) {
            new CityResponse(
                    "Город не найден в openweathermap.org, введите корректный город");
        }
        return new CityResponse(city.getId(), city.getCityName(), city.getTemperature());
    }

    @Override
    public CityListResponse getAllCities() {
        List<City> favoriteCities = cityRepository.findFavoriteCitiesOrderByCreatedDateDesc();
        List<City> nonFavoriteCities = cityRepository.findNonFavoriteCitiesOrderByCreatedDateDesc();
        return new CityListResponse(favoriteCities, nonFavoriteCities);
    }

    @Override
    @Transactional
    public CityResponse deleteCityByName(String cityName) {
        if (cityName == null) {
            return new CityResponse("Необходимо ввести название города!");
        }

        City city = cityRepository.findByCityName(cityName);
        if (city == null) {
            return new CityResponse(String.format("Город %s не найден!", cityName));
        }

        cityRepository.delete(city);
        return new CityResponse(String.format("Город %s удален из списка!", cityName));
    }

}
