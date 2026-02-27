package com.oskarvos.cityweatherapp.common.util;

import org.springframework.stereotype.Component;

@Component
public class CityNameNormalizer {

    public String normalizer(String cityName) {
        if (cityName == null) return null;
        return cityName.trim().replaceAll("\\s+", " ");
    }

}
