# Настройка Swagger (OpenAPI Configuration)

Для интеграции документации в проект был использован класс конфигурации `OpenApiConfig`, который расширяет стандартные возможности Springdoc.

## Конфигурационный класс
Класс находится в пакете `com.example.shelter.config` и отвечает за метаданные API.

```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Приюта 'Доброе сердце'")
                        .version("1.0")
                        .description("REST API для системы управления приютом.")
                        .contact(new Contact()
                                .name("Виктория")
                                .email("viktoria.aldabaeva@gmail.com")));
    }
}
```

## Ключевые возможности конфигурации:
- **Кастомизация заголовка:** Использование Info позволяет задать название проекта, версию и контактные данные.

- **Централизация:** Все настройки API хранятся в одном месте, что упрощает обновление версии API или контактной информации.

- **Безопасность (опционально):** В данной конфигурации предусмотрена возможность добавления SecurityScheme (JWT/Basic Auth), если потребуется защитить API паролем.