package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.model.dto.CityListResponse;
import com.oskarvos.cityweatherapp.model.dto.CityRequest;
import com.oskarvos.cityweatherapp.model.dto.CityResponse;
import com.oskarvos.cityweatherapp.model.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public CityResponse getCityByName(String cityName) {
        if (cityName == null) {
            return new CityResponse("Необходимо ввести название города!");
        }

        City city = cityRepository.findByCityName(cityName);
        if (city == null) {
            return new CityResponse(String.format("Город %s не найден!", cityName));
        }

        return new CityResponse(city.getId(), city.getCityName(), city.getTemperature());
    }


    @Override
    public CityListResponse getAllCities() {
        List<City> favoriteCities = cityRepository.findFavoriteCitiesOrderByCreatedDateDesc();
        List<City> nonFavoriteCities = cityRepository.findNonFavoriteCitiesOrderByCreatedDateDesc();
        List<City> result = Stream.concat(favoriteCities.stream(), nonFavoriteCities.stream())
                .toList();
        return new CityListResponse(result);
    }

    @Override
    @Transactional
    public CityResponse createCity(CityRequest request) {
        City city = cityRepository.findByCityName(request.getCityName());
        if (city == null) {
            city = new City(request.getCityName(), request.getTemperature());
            cityRepository.save(city);
            return new CityResponse(city.getId(), city.getCityName(), city.getTemperature());
        } else {
            return new CityResponse(String.format(
                    "Город %s не был добавлен, так как существует в списке!", request.getCityName()));
        }
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
