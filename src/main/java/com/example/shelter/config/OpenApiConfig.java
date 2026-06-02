package com.example.shelter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Приюта 'Доброе сердце'")
                        .version("1.0")
                        .description("Документация REST API для взаимодействия с системой управления приютом.")
                        .contact(new Contact()
                                .name("Виктория")
                                .email("viktoria.aldabaeva@gmail.com")));
    }
}