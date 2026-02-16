package com.oskarvos.cityweatherapp.controller;

import com.oskarvos.cityweatherapp.dto.response.CityListResponse;
import com.oskarvos.cityweatherapp.dto.response.CityResponse;
import com.oskarvos.cityweatherapp.entity.City;
import com.oskarvos.cityweatherapp.service.CityFacadeService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)  // Загружаем только контроллер для тестирования
@Import(CityControllerSimpleTest.TestSecurityConfig.class)  // Импортируем тестовую конфигурацию безопасности
@DisplayName("Тесты для CityController")
class CityControllerSimpleTest {

    @TestConfiguration
    static class TestSecurityConfig {

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    // Отключаем CSRF для тестов (в реальном приложении для API с Basic Auth это обычно нужно)
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(authz -> authz
                            .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/cities/delete/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                    )
                    .httpBasic(org.springframework.security.config.Customizer.withDefaults());

            return http.build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return new InMemoryUserDetailsManager(
                    User.withUsername("admin")
                            .password("{noop}password")  // {noop} означает без шифрования пароля
                            .roles("ADMIN")
                            .build(),

                    User.withUsername("user")
                            .password("{noop}password")
                            .roles("USER")
                            .build()
            );
        }
    }

    @Autowired
    private MockMvc mockMvc;  // Позволяет выполнять HTTP запросы к контроллеру без запуска сервера

    @MockBean
    private CityFacadeService cityFacadeService;  // Создаем mock сервиса, чтобы контролировать его поведение

    private CityResponse testCityResponse;        // Тестовый DTO для ответа с городом
    private CityListResponse testCityListResponse; // Тестовый DTO для списка городов
    private City testCity;                          // Тестовая сущность города

    @BeforeEach
    void setUp() {
        testCityResponse = new CityResponse(1L, "London", 20.5, LocalDateTime.now(), null);

        testCity = new City("London", 20.5);

        // Устанавливаем ID через рефлексию, так как в классе City нет сеттера для id
        // Это необходимо, чтобы City имел конкретный ID для тестов
        try {
            java.lang.reflect.Field idField = City.class.getDeclaredField("id");
            idField.setAccessible(true);  // Разрешаем доступ к private полю
            idField.set(testCity, 1L);    // Устанавливаем значение id = 1
        } catch (Exception e) {
            // В случае ошибки выводим stack trace (в тестах допустимо)
            e.printStackTrace();
        }

        testCityListResponse = new CityListResponse(
                Collections.emptyList(),          // favoriteCities - пустой список
                Collections.singletonList(testCity) // cities - список с одним городом
        );
    }

    @Test
    @WithMockUser  // Создаем аутентифицированного пользователя для теста (по умолчанию роль USER)
    @DisplayName("Успешное получение города по имени - возвращает 200 OK")
    void getCityNameWithValidNameReturnsOk() throws Exception {
        String cityName = "London";

        // Настраиваем поведение mock: при вызове getCityName("London") вернуть ResponseEntity.ok с тестовыми данными
        // Используем thenAnswer вместо thenReturn для работы с wildcard типом ResponseEntity<?>
        when(cityFacadeService.getCityName(cityName))
                .thenAnswer(invocation -> ResponseEntity.ok(testCityResponse));

        mockMvc.perform(get("/api/cities/name/{cityName}", cityName))  // Выполняем GET запрос
                .andExpect(status().isOk());  // Проверяем что статус ответа = 200
    }

    @Test
    @WithMockUser
    @DisplayName("Поиск несуществующего города - возвращает 404 Not Found")
    void getCityNameWithNonExistentCityReturnsNotFound() throws Exception {
        String cityName = "NonExistentCity";

        when(cityFacadeService.getCityName(cityName))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        mockMvc.perform(get("/api/cities/name/{cityName}", cityName))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("Поиск города с невалидным именем - возвращает 400 Bad Request")
    void getCityNameWithInvalidNameReturnsBadRequest() throws Exception {
        String cityName = "Invalid@City";

        when(cityFacadeService.getCityName(cityName))
                .thenReturn(ResponseEntity.badRequest().build());

        mockMvc.perform(get("/api/cities/name/{cityName}", cityName))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")  // Пользователь с ролью ADMIN (Spring добавит префикс ROLE_)
    @DisplayName("Администратор получает список всех городов - возвращает 200 OK")
    void getCitiesAsAdminReturnsAllCities() throws Exception {

        // any() означает "при любом аргументе" - нам не важно какой UserDetails будет передан
        when(cityFacadeService.getCities(any()))
                .thenReturn(ResponseEntity.ok(testCityListResponse));

        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Обычный пользователь получает список избранных городов - возвращает 200 OK")
    void getCitiesAsRegularUserReturnsFavoriteCities() throws Exception {

        when(cityFacadeService.getCities(any()))
                .thenReturn(ResponseEntity.ok(testCityListResponse));

        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Неавторизованный пользователь получает 401 Unauthorized")
    void getCitiesWithoutAuthenticationReturnsUnauthorized() throws Exception {

        mockMvc.perform(get("/api/cities"))  // Запрос без аутентификации
                .andExpect(status().isUnauthorized());  // Проверяем статус 401
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    // Явно указываем username для соответствия тестовой конфигурации
    @DisplayName("Администратор успешно удаляет существующий город - возвращает 200 OK")
    void deleteCityWithValidNameReturnsDeletedCity() throws Exception {
        String cityName = "London";

        when(cityFacadeService.deleteCity(cityName))
                .thenAnswer(invocation -> ResponseEntity.ok(testCityResponse));

        mockMvc.perform(delete("/api/cities/delete/{cityName}", cityName))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Администратор пытается удалить несуществующий город - возвращает 404 Not Found")
    void deleteCityWithNonExistentCityReturnsNotFound() throws Exception {
        String cityName = "NonExistentCity";

        when(cityFacadeService.deleteCity(cityName))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        mockMvc.perform(delete("/api/cities/delete/{cityName}", cityName))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Обычный пользователь пытается удалить город - возвращает 403 Forbidden")
    void deleteCityAsUserReturnsForbidden() throws Exception {
        String cityName = "London";

        // НЕ настраиваем mock, так как запрос даже не должен дойти до сервиса
        // Security должен вернуть 403 до вызова сервиса

        mockMvc.perform(delete("/api/cities/delete/{cityName}", cityName))
                .andExpect(status().isForbidden());  // Проверяем статус 403 Forbidden
    }

}
