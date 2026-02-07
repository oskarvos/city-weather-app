package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.model.dto.CityRequest;
import com.oskarvos.cityweatherapp.model.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City getCityByName(String cityName) {
        return cityRepository.findByCityName(cityName);
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAllOrderByIdDesc();
    }

    @Override
    @Transactional
    public City createCity(CityRequest request) {
        City city = new City();
        city.setCityName(request.getCityName());
        city.setTemperature(request.getTemperature());

        return cityRepository.save(city);
    }

    @Override
    @Transactional
    public void deleteCityByName(String cityName) {
        City city = cityRepository.findByCityName(cityName);
        cityRepository.delete(city);
    }

}
