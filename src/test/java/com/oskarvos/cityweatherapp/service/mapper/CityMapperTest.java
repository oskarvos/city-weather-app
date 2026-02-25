package com.oskarvos.cityweatherapp.service.mapper;

import com.oskarvos.cityweatherapp.city.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.city.entity.City;
import com.oskarvos.cityweatherapp.city.service.mapper.CityMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CityMapperTest {

    private final CityMapper cityMapper = new CityMapper();

    private City city;
    private static final Long ID = 1L;
    private static final String CITY_NAME = "Minsk";
    private static final Double TEMPERATURE = 22.3;
    private static final LocalDateTime UPDATE_AT = LocalDateTime.of(
            2026, 2, 19, 20, 30, 45, 123456789);
    private static final String INFO = "Данные устарели";

    @Test
    @DisplayName("Должен вернуть актуальные данные при условии валидных данных")
    void shouldReturnCityResponseWhenCityValid() {
        createCityActual();

        CityResponse result = cityMapper.buildValid(city);

        assertAll("Должен вернуть CityResponse с правильными данными",
                () -> assertNotNull(result),
                () -> assertEquals(ID, result.getId()),
                () -> assertEquals(CITY_NAME, result.getCityName()),
                () -> assertEquals(TEMPERATURE, result.getTemperature())
        );
    }

    @Test
    @DisplayName("Должен упасть с NPE при null")
    void shouldThrowExceptionWhenCityNull() {
        assertThrows(NullPointerException.class,
                () -> cityMapper.buildValid(null));
    }

    @Test
    @DisplayName("Должен сохранить пустую строку в названии города")
    void shouldReturnEmptyFieldWhenCityNameEmptyField() {
        city = new City();
        city.setCityName("");

        CityResponse result = cityMapper.buildValid(city);

        assertNotNull(result);
        assertEquals("", result.getCityName());
    }

    @Test
    @DisplayName("Не должен изменять исходный объект City")
    void shouldNotModifyOriginalCity() {
        createCityActual();

        Long originalId = city.getId();
        String originalName = city.getCityName();
        Double originalTemp = city.getTemperature();
        LocalDateTime originalUpdatedAt = city.getUpdatedAt();

        CityResponse result = cityMapper.buildValid(city);

        assertAll("Исходный City не должен измениться после маппинга",
                () -> assertEquals(originalId, city.getId()),
                () -> assertEquals(originalName, city.getCityName()),
                () -> assertEquals(originalTemp, city.getTemperature()),
                () -> assertEquals(originalUpdatedAt, city.getUpdatedAt())
        );

        assertAll("Результат маппинга должен быть корректным",
                () -> assertNotNull(result),
                () -> assertEquals(ID, result.getId()),
                () -> assertEquals(CITY_NAME, result.getCityName()),
                () -> assertEquals(TEMPERATURE, result.getTemperature())
        );
    }

    @Test
    @DisplayName("Должен вернуть актуальные данные и сообщение при условии валидных данных")
    void shouldReturnCityResponseWithMessageWhenCityValid() {
        createCityActual();

        CityResponse result = cityMapper.buildWithMessage(city, INFO);

        assertAll("Должен вернуть СityResponse с сообщением",
                () -> assertNotNull(result),
                () -> assertEquals(ID, result.getId()),
                () -> assertEquals(CITY_NAME, result.getCityName()),
                () -> assertEquals(TEMPERATURE, result.getTemperature()),
                () -> assertEquals("Данные устарели", INFO)
        );
    }

    private void createCityActual() {
        city = new City();
        city.setId(ID);
        city.setCityName(CITY_NAME);
        city.setTemperature(TEMPERATURE);
        city.setUpdatedAt(UPDATE_AT);
    }

}
