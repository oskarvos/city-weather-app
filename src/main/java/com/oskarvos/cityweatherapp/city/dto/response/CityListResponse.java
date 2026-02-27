package com.oskarvos.cityweatherapp.city.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CityListResponse {

    private final List<CityResponse> favorite;
    private final List<CityResponse> noFavorite;

}
