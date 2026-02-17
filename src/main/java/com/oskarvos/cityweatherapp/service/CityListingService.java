package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityListingService {

    private final CityRepository cityRepository;

    public CityListingService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public CityListResponse getAllCities() {
        List<City> favoriteCities = cityRepository.findFavoriteCitiesOrderByCreatedDateDesc();
        List<City> nonFavoriteCities = cityRepository.findNonFavoriteCitiesOrderByCreatedDateDesc();
        return new CityListResponse(favoriteCities, nonFavoriteCities);
    }

    public CityListResponse getFavoriteCities() {
        return new CityListResponse(cityRepository.findFavoriteCitiesOrderByCreatedDateDesc(), List.of());
    }

}
