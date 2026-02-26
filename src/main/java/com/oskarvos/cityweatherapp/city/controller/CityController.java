package com.oskarvos.cityweatherapp.city.controller;

import com.oskarvos.cityweatherapp.audit.annotation.Auditable;
import com.oskarvos.cityweatherapp.city.dto.request.CityRequest;
import com.oskarvos.cityweatherapp.city.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.city.service.CityFacadeControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityFacadeControllerService cityFacadeControllerService;

    public CityController(CityFacadeControllerService cityFacadeControllerService) {
        this.cityFacadeControllerService = cityFacadeControllerService;
    }

    @PostMapping("/name")
    @Auditable(action = "VIEW_WEATHER")
    public ResponseEntity<?> getCityName(@RequestBody CityRequest request) {
        return cityFacadeControllerService.getCityName(request.getCityName());
    }

    @GetMapping("/")
    public ResponseEntity<CityListResponse> getCities(@AuthenticationPrincipal UserDetails userDetails) {
        return cityFacadeControllerService.getCities(userDetails);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCity(@RequestBody CityRequest request) {
        return cityFacadeControllerService.deleteCity(request.getCityName());
    }

    @PostMapping("/favorite/name")
    public ResponseEntity<?> createFavoriteCity(@RequestBody CityRequest request) {
        return cityFacadeControllerService.createFavoriteCity(request.getCityName());
    }

}
