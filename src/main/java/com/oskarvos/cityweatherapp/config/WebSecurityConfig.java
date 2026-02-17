package com.oskarvos.cityweatherapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    @Order(2)  // этот бин проверяется первым (имеет приоритет)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {
        http
                // .securityMatcher("/**") можно не писать, это цепочка по умолчанию
                .authorizeHttpRequests(authz -> authz
                        // Разрешаем доступ без аутентификации к страницам логина, регистрации и статике
                        .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                // Можно включить CSRF для форм, но для простоты пока отключим
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

}
