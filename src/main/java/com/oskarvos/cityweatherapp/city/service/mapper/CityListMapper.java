package com.oskarvos.cityweatherapp.city.service.mapper;

import com.oskarvos.cityweatherapp.city.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.city.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.city.entity.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CityListMapper {

    private final CityMapper cityMapper;

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
