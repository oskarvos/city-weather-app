package com.oskarvos.cityweatherapp.controller;

import com.oskarvos.cityweatherapp.model.dto.CityRequest;
import com.oskarvos.cityweatherapp.model.entity.City;
import com.oskarvos.cityweatherapp.service.CityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities/name/{cityName}")
    public City getCityName(@PathVariable String cityName) {
        return cityService.getCityByName(cityName);
    }

    @GetMapping("/cities")
    public List<City> getCities() {
        return cityService.getAllCities();
    }

    @PostMapping("/cities")
    public City createCity(@RequestBody CityRequest request) {
        return cityService.createCity(request);
    }

    @DeleteMapping("/cities/delete/{cityName}")
    public void deleteCity(@PathVariable String cityName) {
        cityService.deleteCityByName(cityName);
    }

}
