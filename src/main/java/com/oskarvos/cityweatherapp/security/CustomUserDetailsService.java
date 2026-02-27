package com.oskarvos.cityweatherapp.security;

import com.oskarvos.cityweatherapp.audit.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.info("Аутентификация пользователя: {}", login);

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> {
                    log.warn("Пользователь не найден: {}", login);
                    return new UsernameNotFoundException("Нет такого юзера в БД: " + login);
                });

        log.debug("Найден пользователь: {}", login);
        CustomUserDetails customUserDetails = new CustomUserDetails();

        customUserDetails.setLastName(user.getLastName());
        customUserDetails.setFirstName(user.getFirstName());
        customUserDetails.setEmail(user.getEmail());
        customUserDetails.setRole(user.getRole());
        customUserDetails.setLogin(user.getLogin());
        customUserDetails.setPassword(user.getPassword());

        log.info("Пользователь {} успешно аутентифицирован", login);
        return customUserDetails;
    }

}
