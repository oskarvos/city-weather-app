package com.oskarvos.cityweatherapp.controller;

import com.oskarvos.cityweatherapp.model.dto.CityListResponse;
import com.oskarvos.cityweatherapp.model.dto.CityRequest;
import com.oskarvos.cityweatherapp.model.dto.CityResponse;
import com.oskarvos.cityweatherapp.service.CityService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities/name/{cityName}")
    public CityResponse getCityName(@PathVariable String cityName) {
        return cityService.getCityByName(cityName);
    }

    @GetMapping("/cities")
    public CityListResponse getCities() {
        return cityService.getAllCities();
    }

    @PostMapping("/cities")
    public CityResponse createCity(@RequestBody CityRequest request) {
        return cityService.createCity(request);
    }

    @DeleteMapping("/cities/delete/{cityName}")
    public CityResponse deleteCity(@PathVariable String cityName) {
        return cityService.deleteCityByName(cityName);
    }

}
