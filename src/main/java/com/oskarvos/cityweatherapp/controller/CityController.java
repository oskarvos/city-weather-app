package com.oskarvos.cityweatherapp.controller;

import com.oskarvos.cityweatherapp.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.exception.CityNotFoundException;
import com.oskarvos.cityweatherapp.exception.CityValidationException;
import com.oskarvos.cityweatherapp.exception.WeatherApiCityNotFoundException;
import com.oskarvos.cityweatherapp.exception.WeatherApiConnectionException;
import com.oskarvos.cityweatherapp.service.CityDeleteService;
import com.oskarvos.cityweatherapp.service.CityQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CityController {

    private static final Logger log = LoggerFactory.getLogger(CityController.class);

    private final CityQueryService cityQueryService;
    private final CityDeleteService cityDeleteService;

    public CityController(CityQueryService cityQueryService,
                          CityDeleteService cityDeleteService) {
        this.cityQueryService = cityQueryService;
        this.cityDeleteService = cityDeleteService;
    }

    @GetMapping("/cities/name/{cityName}")
    public ResponseEntity<?> getCityName(@PathVariable String cityName) {
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

    @GetMapping("/cities")
    public ResponseEntity<CityListResponse> getCities() {
        log.info("Получен запрос на поиск всех городов из БД");
        return ResponseEntity.ok(cityQueryService.getAllCities());
    }

    @DeleteMapping("/cities/delete/{cityName}")
    public ResponseEntity<?> deleteCity(@PathVariable String cityName) {
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

}
