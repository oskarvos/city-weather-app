package com.oskarvos.cityweatherapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем CSRF (для REST API с Basic Auth это обычно нужно)
                .csrf(AbstractHttpConfigurer::disable)
                // Настраиваем правила доступа
                .authorizeHttpRequests(authz -> authz
                        // Все запросы к /api/cities требуют аутентификации
                        .requestMatchers(HttpMethod.GET, "/api/cities/name/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/cities").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/cities/delete/**").hasRole("ADMIN")
                        // Все остальные запросы тоже требуют аутентификации
                        .anyRequest().authenticated()

                )
                // Включаем HTTP Basic аутентификацию
                .httpBasic(withDefaults())
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt - надёжное хэширование паролей
        return new BCryptPasswordEncoder();
    }

}
