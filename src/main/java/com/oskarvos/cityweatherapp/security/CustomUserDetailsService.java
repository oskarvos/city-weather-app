package com.oskarvos.cityweatherapp.security;

import com.oskarvos.cityweatherapp.audit.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
