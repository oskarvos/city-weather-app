package com.oskarvos.cityweatherapp.city.service;

import com.oskarvos.cityweatherapp.city.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.city.entity.City;
import com.oskarvos.cityweatherapp.city.repository.CityRepository;
import com.oskarvos.cityweatherapp.city.service.mapper.CityListMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityListingService {

    private final CityRepository cityRepository;
    private final CityListMapper cityListMapper;

    public CityListingService(CityRepository cityRepository,
                              CityListMapper cityListMapper) {
        this.cityRepository = cityRepository;
        this.cityListMapper = cityListMapper;
    }

    public CityListResponse getAllCities() {
        List<City> favoriteCities = cityRepository.findFavoriteCitiesOrderByCreatedDateDesc();
        List<City> nonFavoriteCities = cityRepository.findNonFavoriteCitiesOrderByCreatedDateDesc();

        return cityListMapper.buildValidList(favoriteCities, nonFavoriteCities);
    }

    public CityListResponse getFavoriteCities() {
        List<City> favoriteCities = cityRepository.findFavoriteCitiesOrderByCreatedDateDesc();

        return cityListMapper.buildValidList(favoriteCities, List.of());
    }

}
