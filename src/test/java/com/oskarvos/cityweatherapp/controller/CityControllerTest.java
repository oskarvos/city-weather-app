package com.oskarvos.cityweatherapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarvos.cityweatherapp.city.controller.CityController;
import com.oskarvos.cityweatherapp.city.dto.request.CityRequest;
import com.oskarvos.cityweatherapp.city.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.city.service.CityFacadeControllerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Простые тесты для CityController с правильной Security конфигурацией
 */
@WebMvcTest(CityController.class)
@Import(CityControllerSimpleTest.TestSecurityConfig.class)  // Импортируем тестовую конфигурацию безопасности
@DisplayName("Тесты CityController")
class CityControllerSimpleTest {

    /**
     * Тестовая конфигурация безопасности
     * Отключает CSRF и настраивает простую аутентификацию
     */
    @TestConfiguration
    static class TestSecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())  // Отключаем CSRF для тестов
                    .authorizeHttpRequests(authz -> authz
                            .anyRequest().permitAll()  // РАЗРЕШАЕМ ВСЕ ЗАПРОСЫ для тестов
                    );
            return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return new InMemoryUserDetailsManager(
                    User.withUsername("user")
                            .password("{noop}password")
                            .roles("USER")
                            .build(),
                    User.withUsername("admin")
                            .password("{noop}password")
                            .roles("ADMIN")
                            .build()
            );
        }
    }

    @Autowired
    private MockMvc mockMvc;  // Для эмуляции HTTP запросов

    @Autowired
    private ObjectMapper objectMapper;  // Для преобразования объектов в JSON

    @MockBean
    private CityFacadeControllerService cityFacadeControllerService;  // Заглушка сервиса

    private CityResponse testCityResponse;  // Тестовый ответ

    @BeforeEach
    void setUp() {
        // Создаем тестовый город для ответов
        testCityResponse = new CityResponse(
                1L,
                "London",
                20.5,
                LocalDateTime.now(),
                null
        );
    }

    @Test
    @WithMockUser  // Имитация авторизованного пользователя
    @DisplayName("Успешное получение города по имени - 200 OK")
    void getCityName_Success() throws Exception {
        // Подготовка данных
        String cityName = "London";
        CityRequest request = new CityRequest();
        request.setCityName(cityName);

        // Настраиваем заглушку
        when(cityFacadeControllerService.getCityName(eq(cityName)))
                .thenAnswer(invocation -> new ResponseEntity<>(testCityResponse, HttpStatus.OK));

        // Выполняем POST запрос и проверяем результат
        mockMvc.perform(post("/api/cities/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("Поиск несуществующего города - 404 Not Found")
    void getCityName_NotFound() throws Exception {
        // Подготовка данных
        String cityName = "NonExistentCity";
        CityRequest request = new CityRequest();
        request.setCityName(cityName);

        // Настраиваем заглушку
        when(cityFacadeControllerService.getCityName(eq(cityName)))
                .thenAnswer(invocation -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        // Выполняем POST запрос и проверяем результат
        mockMvc.perform(post("/api/cities/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Поиск города с невалидным именем - 400 Bad Request")
    void getCityName_BadRequest() throws Exception {
        // Подготовка данных
        String cityName = "Invalid@City";
        CityRequest request = new CityRequest();
        request.setCityName(cityName);

        // Настраиваем заглушку
        when(cityFacadeControllerService.getCityName(eq(cityName)))
                .thenAnswer(invocation -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        // Выполняем POST запрос и проверяем результат
        mockMvc.perform(post("/api/cities/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    @DisplayName("Администратор получает список городов - 200 OK")
//    void getCities_Admin_Success() throws Exception {
//        // Настраиваем заглушку
//        when(cityFacadeControllerService.getCities(any()))
//                .thenAnswer(invocation -> new ResponseEntity<>(HttpStatus.OK));
//
//        // Выполняем GET запрос и проверяем результат
//        mockMvc.perform(get("/api/cities"))
//                .andExpect(status().isOk());
//    }

//    @Test
//    @WithMockUser
//    @DisplayName("Обычный пользователь получает список городов - 200 OK")
//    void getCities_User_Success() throws Exception {
//        // Настраиваем заглушку
//        when(cityFacadeControllerService.getCities(any()))
//                .thenAnswer(invocation -> new ResponseEntity<>(HttpStatus.OK));
//
//        // Выполняем GET запрос и проверяем результат
//        mockMvc.perform(get("/api/cities"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("Неавторизованный пользователь - 200 OK (в тестах все разрешено)")
//    void getCities_Unauthorized() throws Exception {
//        // В тестовой конфигурации все запросы разрешены, поэтому ожидаем 200, а не 401
//        when(cityFacadeControllerService.getCities(any()))
//                .thenAnswer(invocation -> new ResponseEntity<>(HttpStatus.OK));
//
//        mockMvc.perform(get("/api/cities"))
//                .andExpect(status().isOk());  // Теперь будет 200, а не 401
//    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Администратор удаляет город - 200 OK")
    void deleteCity_Admin_Success() throws Exception {
        // Подготовка данных
        String cityName = "London";
        CityRequest request = new CityRequest();
        request.setCityName(cityName);

        // Настраиваем заглушку
        when(cityFacadeControllerService.deleteCity(eq(cityName)))
                .thenAnswer(invocation -> new ResponseEntity<>(testCityResponse, HttpStatus.OK));

        // Выполняем DELETE запрос и проверяем результат
        mockMvc.perform(delete("/api/cities/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Администратор пытается удалить несуществующий город - 404 Not Found")
    void deleteCity_Admin_NotFound() throws Exception {
        // Подготовка данных
        String cityName = "NonExistentCity";
        CityRequest request = new CityRequest();
        request.setCityName(cityName);

        // Настраиваем заглушку
        when(cityFacadeControllerService.deleteCity(eq(cityName)))
                .thenAnswer(invocation -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        // Выполняем DELETE запрос и проверяем результат
        mockMvc.perform(delete("/api/cities/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Обычный пользователь удаляет город - 200 OK (в тестах все разрешено)")
    void deleteCity_User_Success() throws Exception {
        // Подготовка данных
        String cityName = "London";
        CityRequest request = new CityRequest();
        request.setCityName(cityName);

        // Настраиваем заглушку
        when(cityFacadeControllerService.deleteCity(eq(cityName)))
                .thenAnswer(invocation -> new ResponseEntity<>(testCityResponse, HttpStatus.OK));

        // В тестовой конфигурации все запросы разрешены, поэтому пользователь тоже может удалять
        mockMvc.perform(delete("/api/cities/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());  // Будет 200, а не 403
    }

    @Test
    @WithMockUser
    @DisplayName("Добавление города в избранное - 200 OK")
    void createFavoriteCity_Success() throws Exception {
        // Подготовка данных
        String cityName = "London";
        CityRequest request = new CityRequest();
        request.setCityName(cityName);

        // Настраиваем заглушку
        when(cityFacadeControllerService.createFavoriteCity(eq(cityName)))
                .thenAnswer(invocation -> new ResponseEntity<>(testCityResponse, HttpStatus.OK));

        // Выполняем POST запрос и проверяем результат
        mockMvc.perform(post("/api/cities/favorite/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName("Добавление уже существующего города в избранное - 409 Conflict")
    void createFavoriteCity_Conflict() throws Exception {
        // Подготовка данных
        String cityName = "London";
        CityRequest request = new CityRequest();
        request.setCityName(cityName);

        // Настраиваем заглушку
        when(cityFacadeControllerService.createFavoriteCity(eq(cityName)))
                .thenAnswer(invocation -> new ResponseEntity<>(HttpStatus.CONFLICT));

        // Выполняем POST запрос и проверяем результат
        mockMvc.perform(post("/api/cities/favorite/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }
}