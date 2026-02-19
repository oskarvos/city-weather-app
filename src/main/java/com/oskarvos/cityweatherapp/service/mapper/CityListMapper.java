package com.oskarvos.cityweatherapp.service.mapper;

import com.oskarvos.cityweatherapp.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.entity.City;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CityListMapper {

    private final CityMapper cityMapper;

    public CityListMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    public CityListResponse buildValidList(List<City> favorite, List<City> cities) {
        List<CityResponse> favoriteDto = mappingCityList(favorite);
        List<CityResponse> citiesDto = mappingCityList(cities);

        return new CityListResponse(favoriteDto, citiesDto);
    }

    private List<CityResponse> mappingCityList(List<City> cityList) {
        return cityList.stream()
                .map(cityMapper::buildValid)
                .toList();
    }

}
