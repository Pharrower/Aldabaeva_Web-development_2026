# Архитектурная диаграмма слоев PCMEF (Logical Component Diagram)

## Описание
Диаграмма отображает статическую структуру распределения классов по слоям паттерна PCMEF и направленность векторов зависимостей. Ключевое правило архитектуры: верхние слои могут зависеть от нижних, но нижние слои никогда не должны знать о существовании верхних.

## Визуализация диаграммы

![Диаграмма слоев PCMEF](images/pcmef-diagram.png)

## Код модели (PlantUML)

```plantuml
@startuml
skinparam packageStyle rectangle
skinparam shadowing false
skinparam linetype ortho

skinparam package {
    BackgroundColor<<Presentation>> #E8F0FE
    BackgroundColor<<Control>> #E6F4EA
    BackgroundColor<<Mediator>> #FEF7E0
    BackgroundColor<<Entity>> #FCE8E6
    BackgroundColor<<Foundation>> #F3E8FD
    BorderColor #5F6368
}

title Диаграмма пакетов ИС Приют "Доброе сердце" (Компактная PCMEF)

package "Presentation Layer (P)" <<Presentation>> {
    package "resources.templates" as Templates {
        [index.html] as idx
        [catalog.html] as cat
        [edit-animal.html] as edit
        [add-animal.html] as add
        [fragments.html] as frag
        [admin-*.html] as adm
    }
    package "resources.static" as Static {
        [style.css] as css
    }
}

package "Control Layer (C)" <<Control>> {
    package "com.example.shelter.controllers" as Controllers {
        [MainController] as MC
        [AuthController] as AC
        [AnimalAdminController] as AAC
        [ShelterAdminController] as SAC
        [DonationRestController] as DRC
        [RestApiController] as RAC
    }
    package "com.example.shelter.dto" as DTO {
        [DonationDto] as DDTO
    }
}

package "Mediator Layer (M)" <<Mediator>> {
    package "com.example.shelter.services" as Services {
        [AnimalService] as AS
        [UserService] as US
        [CustomUserDetailsService] as CUDS
    }
}

package "Entity Layer (E)" <<Entity>> {
    package "com.example.shelter.entities" as Entities {
        [Animal] as E_Anim
        [User] as E_User
        [Donation] as E_Don
        [Supply] as E_Sup
        [AdoptionRequest] as E_Adopt
        [VolunteerRequest] as E_Vol
    }
}

package "Foundation Layer (F)" <<Foundation>> {
    package "com.example.shelter.repositories" as Repositories {
        [AnimalRepository] as R_Anim
        [UserRepository] as R_User
        [DonationRepository] as R_Don
        [SupplyRepository] as R_Sup
        [AdoptionRequestRepository] as R_Adopt
        [VolunteerRepository] as R_Vol
    }
    package "com.example.shelter.config" as Config {
        [SecurityConfig] as SecConf
    }
    [ShelterApplication] as App
}

package "Test Environment" #F1F3F4 {
    package "com.example.shelter.test" as Tests {
        [AnimalLogicTest] as T1
        [SupplyLogicTest] as T2
        [WebCoverageTest] as T3
        [ServiceQuickCoverageTest] as T4
    }
}

' Шаблоны в 2 колонки
idx -[hidden]right-> cat
edit -[hidden]right-> add
frag -[hidden]right-> adm
idx -[hidden]down-> edit
edit -[hidden]down-> frag

' Контроллеры в сетку 2x3
MC -[hidden]right-> AC
AAC -[hidden]right-> SAC
DRC -[hidden]right-> RAC
MC -[hidden]down-> AAC
AAC -[hidden]down-> DRC

' Сущности (Entity) в сетку 3x2
E_Anim -[hidden]right-> E_User
E_User -[hidden]right-> E_Don
E_Sup -[hidden]right-> E_Adopt
E_Adopt -[hidden]right-> E_Vol
E_Anim -[hidden]down-> E_Sup

' Репозитории в сетку 3x2
R_Anim -[hidden]right-> R_User
R_User -[hidden]right-> R_Don
R_Sup -[hidden]right-> R_Adopt
R_Adopt -[hidden]right-> R_Vol
R_Anim -[hidden]down-> R_Sup

' Тесты в сетку 2x2
T1 -[hidden]right-> T2
T3 -[hidden]right-> T4
T1 -[hidden]down-> T3


' --- Реальные направления логических зависимостей по методологии PCMEF ---
Templates --> Controllers : "HTTP Requests / AJAX"
Controllers --> DTO : "Использует"
Controllers --> Services : "Вызовы методов бизнес-логики"
Services --> Entities : "Управление состоянием"
Services --> Repositories : "Запросы данных"
Repositories --> Entities : "Маппинг сущностей"
Tests ..> Services : "Тестирование (JUnit / Mockito)"
Tests ..> Entities : "Проверка бизнес-правил"

@enduml
```

## Спецификация слоев приложения
1. **Presentation Layer (Слой представления):** Динамические XHTML-шаблоны Thymeleaf (`index.html`, `catalog.html`), CSS-стили Bootstrap 5 и клиентские скрипты на языке JavaScript для обработки асинхронных AJAX-запросов.
2. **Control Layer (Слой контроллеров):** Веб-контроллеры Spring MVC (`MainController`), выполняющие маршрутизацию HTTP-запросов (`GET/POST`), диспетчеризацию потоков управления и первичную валидацию веб-форм.
3. **Mediator Layer / Service Layer (Слой бизнес-логики):** Транзакционные сервисы (`AnimalService`, `SupplyService`, `AdoptionRequestService`), инкапсулирующие ключевые алгоритмы, бизнес-правила приюта и вычисляемые параметры.
4. **Entity Layer (Слой доменных сущностей):** ORM-модели данных (`Animal`, `User`, `Supply`, `AdoptionRequest`), размеченные JPA-аннотациями для автоматического отображения на таблицы БД.
5. **Foundation Layer (Базовый инфраструктурный слой):** Репозитории Spring Data JPA (`AnimalRepository`, `SupplyRepository`), обеспечивающие абстракцию над SQL-запросами, и конфигурационные классы системы (настройки пула HikariCP).