package com.oskarvos.cityweatherapp.service.mapper;

import com.oskarvos.cityweatherapp.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.entity.City;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CityListResponseMapper {

    private final CityResponseMapper cityResponseMapper;

    public CityListResponseMapper(CityResponseMapper cityResponseMapper) {
        this.cityResponseMapper = cityResponseMapper;
    }

    public CityListResponse buildValidList(List<City> favorite, List<City> cities) {
        List<CityResponse> favoriteDto = mappingCityList(favorite);
        List<CityResponse> citiesDto = mappingCityList(cities);

        return new CityListResponse(favoriteDto, citiesDto);
    }

    private List<CityResponse> mappingCityList(List<City> cityList) {
        return cityList.stream()
                .map(cityResponseMapper::buildValid)
                .toList();
    }

}
