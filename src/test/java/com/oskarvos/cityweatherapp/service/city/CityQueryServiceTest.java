package com.oskarvos.cityweatherapp.service.city;

import com.oskarvos.cityweatherapp.city.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.city.entity.City;
import com.oskarvos.cityweatherapp.city.service.CityQueryService;
import com.oskarvos.cityweatherapp.city.service.mapper.CityMapper;
import com.oskarvos.cityweatherapp.city.service.validation.CityNameValidationService;
import com.oskarvos.cityweatherapp.weather.service.WeatherService;
import com.oskarvos.cityweatherapp.common.validation.date.OutdatedChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class CityQueryServiceTest {

    @Mock
    WeatherService weatherService;
    @Mock
    OutdatedChecker outdatedChecker;
    @Mock
    CityMapper cityMapper;
    @Mock
    CityNameValidationService cityNameValidationService;

    @InjectMocks
    CityQueryService cityQueryService;

    private City city;
    private CityResponse cityResponse;
    private CityResponse cityResponseInfo;
    private static final Long ID = 1L;
    private static final String CITY_NAME = "Minsk";
    private static final Double TEMPERATURE = 22.3;
    private static final LocalDateTime UPDATE_AT = LocalDateTime.of(
            2026, 2, 19, 20, 30, 45, 123456789);
    private static final String INFO = "устаревшие данные";
    private static final String NORMALIZE = "Minsk";

    @BeforeEach
    void setUp() {
        /** TODO
         * создаю City через сеттеры, так как нет конструктора с ID и не использую Lombok (т.е. и не могу через @Builder),
         * а CityResponse создаю через конструктор.
         * Можно ли использовать разные подходы создания, или это плохо? Стоит ли лезть в продакшн и создавать конструктор?
         */
        city = new City();
        city.setId(ID);
        city.setCityName(CITY_NAME);
        city.setTemperature(TEMPERATURE);
        city.setUpdatedAt(UPDATE_AT);

        cityResponse = new CityResponse(ID, CITY_NAME, TEMPERATURE);
        cityResponseInfo = new CityResponse(ID, CITY_NAME, TEMPERATURE, UPDATE_AT, INFO);
    }


//    @Test
//    @DisplayName("Должен вернуть успешный ответ с актуальными данными")
//    void shouldReturnSuccessResponseWhenDataIsActual() {
//        when(cityNameValidationService.normalizeAndValidate(CITY_NAME)).thenReturn(NORMALIZE);
//        when(weatherService.getActualWeather(NORMALIZE)).thenReturn(city);
//        when(outdatedChecker.isOutdated(UPDATE_AT)).thenReturn(false);
//        when(cityMapper.buildValid(city)).thenReturn(cityResponse);
//
//        CityResponse response = cityQueryService.getCityByName(CITY_NAME);
//
//        assertAll("Должен вернуть актуальные данные",
//                () -> assertEquals(ID, response.getId()),
//                () -> assertEquals(CITY_NAME, response.getCityName()),
//                () -> assertEquals(TEMPERATURE, response.getTemperature()),
//                () -> assertNull(response.getInfo(), "Для актуальных данных сообщения быть не должно")
//        );
//
//        verify(cityNameValidationService).normalizeAndValidate("Minsk");
//        verify(weatherService).getActualWeather(NORMALIZE);
//        verify(outdatedChecker).isOutdated(city.getUpdatedAt());
//        verify(cityMapper).buildValid(city);
//        verifyNoMoreInteractions(cityNameValidationService, weatherService, outdatedChecker, cityMapper);
//    }

}
