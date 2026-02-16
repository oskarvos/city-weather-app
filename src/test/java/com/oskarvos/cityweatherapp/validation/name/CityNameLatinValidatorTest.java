package com.oskarvos.cityweatherapp.validation.name;

import com.oskarvos.cityweatherapp.dto.response.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Тесты для CityNameLatinValidator")
class CityNameLatinValidatorTest {

    @Autowired
    private CityNameLatinValidator validator;

    @Test
    @DisplayName("Валидное название города - ошибки нет")
    void validCityNameShouldReturnEmpty() {
        String cityNameLatin = "London";

        Optional<ValidationError> error = validator.validate(cityNameLatin);

        assertFalse(error.isPresent(), "Для валидного названия ошибки быть не должно");
    }

    @Test
    @DisplayName("Не на латинице - возвращает ошибку")
    void cityNameLatinShouldReturnError() {
        String validName = "Москва";

        Optional<ValidationError> error = validator.validate(validName);

        assertTrue(error.isPresent(), "Для города на кирилице т.е. 'Москва' должна быть ошибка");
        assertEquals("ERROR_CODE_2", error.get().getField());
        assertEquals("Только латиница, пробел или тире", error.get().getMessage());
    }

}
