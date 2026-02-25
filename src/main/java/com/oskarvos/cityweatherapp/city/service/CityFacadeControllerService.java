package com.oskarvos.cityweatherapp.city.service;

import com.oskarvos.cityweatherapp.city.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.city.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.common.exception.CityNotFoundException;
import com.oskarvos.cityweatherapp.common.exception.CityValidationException;
import com.oskarvos.cityweatherapp.common.exception.WeatherApiCityNotFoundException;
import com.oskarvos.cityweatherapp.common.exception.WeatherApiConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CityFacadeControllerService {

    private static final Logger log = LoggerFactory.getLogger(CityFacadeControllerService.class);

    private final CityQueryService cityQueryService;
    private final CityDeleteService cityDeleteService;
    private final CityListingService cityListingService;
    private final CityFavoriteService cityFavoriteService;

    public CityFacadeControllerService(CityQueryService cityQueryService,
                                       CityDeleteService cityDeleteService,
                                       CityListingService cityListingService,
                                       CityFavoriteService cityFavoriteService) {
        this.cityQueryService = cityQueryService;
        this.cityDeleteService = cityDeleteService;
        this.cityListingService = cityListingService;
        this.cityFavoriteService = cityFavoriteService;
    }

    public ResponseEntity<?> getCityName(String cityName) {
        log.info("Получен запрос на поиск города {}", cityName);

        CityResponse response = new CityResponse();
        try {
            response = cityQueryService.getCityByName(cityName);
            log.info("Город '{}' успешно найден", cityName);
            return ResponseEntity.ok(response);
        } catch (WeatherApiConnectionException e) {
            log.warn("Не удалось обновить данные для города {}, возвращаем устаревшие: {}", cityName, e.getMessage());
            return ResponseEntity.ok(response);
        } catch (WeatherApiCityNotFoundException e) {
            log.error("Город {} не найден в API погоды", cityName);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (CityValidationException e) {
            log.warn("Ошибка валидации для город {}: {} ошибок", cityName, e.getErrors().size());
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error", "Ошибки валидации",
                            "details", e.getErrors()
                    ));
        }
    }

    public ResponseEntity<CityListResponse> getCities(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Получен запрос на поиск всех городов из БД");

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(authz -> authz.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return ResponseEntity.ok(cityListingService.getAllCities());
        } else {
            return ResponseEntity.ok(cityListingService.getFavoriteCities());
        }
    }

    public ResponseEntity<?> deleteCity(String cityName) {
        log.info("Получен запрос на удаление города {} из БД", cityName);

        try {
            CityResponse response = cityDeleteService.deleteCityByName(cityName);
            log.info("Город {} успешно удален из БД", cityName);
            return ResponseEntity.ok(response);
        } catch (CityNotFoundException e) {
            log.warn("Город {} не найден в БД", cityName);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (CityValidationException e) {
            log.warn("Ошибка валидации для город {}: {} ошибок", cityName, e.getErrors().size());
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error", "Ошибки валидации",
                            "details", e.getErrors()
                    ));
        }
    }

    public ResponseEntity<?> createFavoriteCity(String cityName) {
        log.info("Получен запрос на добавление города {} в избранные", cityName);

        try {
            CityResponse response = cityFavoriteService.createFavoriteCity(cityName);
            log.info("Получен ответ добавлен город {} в избранные либо уже существует как избранный", cityName);
            return ResponseEntity.ok(response);
        } catch (WeatherApiCityNotFoundException e) {
            log.error("Город {} не найден в API погоды", cityName);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (CityValidationException e) {
            log.warn("Ошибка валидации для город {}: {} ошибок", cityName, e.getErrors().size());
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "error", "Ошибки валидации",
                            "details", e.getErrors()
                    ));
        }
    }

}
