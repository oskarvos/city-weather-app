package com.oskarvos.cityweatherapp.service;

import com.oskarvos.cityweatherapp.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.repository.CityRepository;
import com.oskarvos.cityweatherapp.service.mapper.CityListResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityListingService {

    private final CityRepository cityRepository;
    private final CityListResponseMapper cityListResponseMapper;

    public CityListingService(CityRepository cityRepository,
                              CityListResponseMapper cityListResponseMapper) {
        this.cityRepository = cityRepository;
        this.cityListResponseMapper = cityListResponseMapper;
    }

    public CityListResponse getAllCities() {
        List<City> favoriteCities = cityRepository.findFavoriteCitiesOrderByCreatedDateDesc();
        List<City> nonFavoriteCities = cityRepository.findNonFavoriteCitiesOrderByCreatedDateDesc();

        return cityListResponseMapper.buildValidList(favoriteCities, nonFavoriteCities);
    }

    public CityListResponse getFavoriteCities() {
        List<City> favoriteCities = cityRepository.findFavoriteCitiesOrderByCreatedDateDesc();

        return cityListResponseMapper.buildValidList(favoriteCities, List.of());
    }

}
