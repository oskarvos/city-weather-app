package com.oskarvos.cityweatherapp.controller;

import com.oskarvos.cityweatherapp.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.service.CityFacadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CityController {

    private final CityFacadeService cityFacadeService;

    public CityController(CityFacadeService cityFacadeService) {
        this.cityFacadeService = cityFacadeService;
    }

    @GetMapping("/cities/name/{cityName}")
    public ResponseEntity<?> getCityName(@PathVariable String cityName) {
        return cityFacadeService.getCityName(cityName);
    }

    @GetMapping("/cities")
    public ResponseEntity<CityListResponse> getCities(@AuthenticationPrincipal UserDetails userDetails) {
        return cityFacadeService.getCities(userDetails);
    }

    @DeleteMapping("/cities/delete/{cityName}")
    public ResponseEntity<?> deleteCity(@PathVariable String cityName) {
        return cityFacadeService.deleteCity(cityName);
    }

}
