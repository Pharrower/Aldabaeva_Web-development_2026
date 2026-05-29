package com.example.shelter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Шифрование паролей
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/catalog", "/help", "/about", "/register", "/login", "/css/**", "/js/**", "/*.png", "/*.jpg").permitAll() // Доступно всем
                        .requestMatchers("/UUID*").permitAll()
                        .requestMatchers("/profile").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Только админу
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated() // Все остальное — только после входа
                )
                .formLogin(login -> login
                        .loginPage("/login") // Своя страница входа
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/"));

        return http.build();
    }
}