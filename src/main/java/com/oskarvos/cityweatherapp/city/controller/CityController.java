package com.oskarvos.cityweatherapp.city.controller;

import com.oskarvos.cityweatherapp.audit.annotation.Auditable;
import com.oskarvos.cityweatherapp.city.dto.request.CityRequest;
import com.oskarvos.cityweatherapp.city.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.city.service.CityFacadeControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityFacadeControllerService cityFacadeControllerService;

    @PostMapping("/name")
    @Auditable(action = "VIEW_CITY")
    public ResponseEntity<?> getCityName(@RequestBody CityRequest request) {
        return cityFacadeControllerService.getCityName(request.getCityName());
    }

    @GetMapping
    @Auditable(action = "VIEW_CITIES_LIST")
    public ResponseEntity<CityListResponse> getCities(@AuthenticationPrincipal UserDetails userDetails) {
        return cityFacadeControllerService.getCities(userDetails);
    }

    @DeleteMapping("/delete")
    @Auditable(action = "DELETE_CITY")
    public ResponseEntity<?> deleteCity(@RequestBody CityRequest request) {
        return cityFacadeControllerService.deleteCity(request.getCityName());
    }

    @PostMapping("/favorite/name")
    @Auditable(action = "VIEW_FAVORITE_CITY")
    public ResponseEntity<?> createFavoriteCity(@RequestBody CityRequest request) {
        return cityFacadeControllerService.createFavoriteCity(request.getCityName());
    }

}
