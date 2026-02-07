package com.oskarvos.cityweatherapp.controller;

import com.oskarvos.cityweatherapp.model.entity.City;
import com.oskarvos.cityweatherapp.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/city/{cityName}")
    public City getCityName(@PathVariable String cityName) {
        return cityService.getCityByName(cityName)
                .orElse(null);
    }
}
