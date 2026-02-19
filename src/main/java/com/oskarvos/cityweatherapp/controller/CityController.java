package com.oskarvos.cityweatherapp.controller;

import com.oskarvos.cityweatherapp.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.service.city.CityFacadeControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CityController {

    private final CityFacadeControllerService cityFacadeControllerService;

    public CityController(CityFacadeControllerService cityFacadeControllerService) {
        this.cityFacadeControllerService = cityFacadeControllerService;
    }

    @GetMapping("/cities/name/{cityName}")
    public ResponseEntity<?> getCityName(@PathVariable String cityName) {
        return cityFacadeControllerService.getCityName(cityName);
    }

    @GetMapping("/cities")
    public ResponseEntity<CityListResponse> getCities(@AuthenticationPrincipal UserDetails userDetails) {
        return cityFacadeControllerService.getCities(userDetails);
    }

    @DeleteMapping("/cities/delete/{cityName}")
    public ResponseEntity<?> deleteCity(@PathVariable String cityName) {
        return cityFacadeControllerService.deleteCity(cityName);
    }

    @GetMapping("/cities/favorite/name/{cityName}")
    public ResponseEntity<?> createFavoriteCity(@PathVariable String cityName) {
        return cityFacadeControllerService.createFavoriteCity(cityName);
    }

}
