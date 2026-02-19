package com.oskarvos.cityweatherapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем CSRF (для REST API с Basic Auth это обычно нужно)
                .csrf(csrf -> csrf.disable())
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
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Создаём пользователя с ролью USER
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("123"))
                .roles("USER")
                .build();

        // Создаём администратора с ролью ADMIN
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt - надёжное хэширование паролей
        return new BCryptPasswordEncoder();
    }

}
