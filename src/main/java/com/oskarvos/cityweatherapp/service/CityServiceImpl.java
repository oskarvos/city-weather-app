package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.model.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Optional<City> getCityByName(String cityName) {
        return cityRepository.findByCityName(cityName);
    }

}
