package com.oskarvos.cityweatherapp.validation.name;

import com.oskarvos.cityweatherapp.dto.response.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Тесты для CityNameNotNullValidator")
class CityNameNotNullValidatorTest {

    @Autowired
    private CityNameNotNullValidator validator;

    @Test
    @DisplayName("Валидное название города - ошибки нет")
    void validCityName_ShouldReturnEmpty() {
        String validName = "London";

        Optional<ValidationError> result = validator.validate(validName);

        assertFalse(result.isPresent(), "Для валидного названия ошибки быть не должно");
    }

    @Test
    @DisplayName("Null название города - возвращает ошибку")
    void nullCityName_ShouldReturnError() {
        String nullName = null;

        Optional<ValidationError> result = validator.validate(nullName);

        assertTrue(result.isPresent(), "Для null должна быть ошибка");
        assertEquals("ERROR_CODE_1", result.get().getField());
        assertEquals("Поле пустое, введите город", result.get().getMessage());
    }

    @Test
    @DisplayName("Пустая строка - возвращает ошибку")
    void emptyCityName_ShouldReturnError() {
        String emptyName = "";

        Optional<ValidationError> result = validator.validate(emptyName);

        assertTrue(result.isPresent(), "Для пустой строки должна быть ошибка");
        assertEquals("ERROR_CODE_1", result.get().getField());
    }

    @Test
    @DisplayName("Строка из пробелов - возвращает ошибку")
    void blankCityName_ShouldReturnError() {
        String blankName = "   ";

        Optional<ValidationError> result = validator.validate(blankName);

        assertTrue(result.isPresent(), "Для строки из пробелов должна быть ошибка");
    }

}
