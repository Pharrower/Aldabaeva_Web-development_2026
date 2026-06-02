# Концептуальная доменная модель (Domain Model)

## Описание
Доменная модель представляет собой концептуальный каркас сущностей реального мира, их атрибутов и логических взаимосвязей, составляющих ядро бизнес-логики ИС приюта «Доброе сердце».

## Визуализация модели
Концептуальные классы и связи между ними:

![Доменная модель предметной области](images/domain-model.png)

## Код модели (PlantUML)

```plantuml
@startuml
skinparam classAttributeIconSize 0
skinparam shadowing false
skinparam linetype ortho

title Доменная модель предметной области ИС Приют "Доброе сердце" (Domain Model)

class Animal {
  - id: Long
  - name: String
  - species: String
  - gender: String
  - age: Integer
  - weight: Double
  - description: String
  - imageUrl: String
}

class User {
  - id: Long
  - email: String
  - password: String
  - firstName: String
}

class AdoptionRequest {
  - id: Long
  - message: String
  - status: String
}

class Supply {
  - id: Long
  - name: String
  - currentAmount: Double
  - targetAmount: Double
  - unit: String
}

class Donation {
  - id: Long
  - amount: Double
  - donationDate: LocalDateTime
}

class VolunteerRequest {
  - id: Long
  - status: String
  - applicationDate: LocalDateTime
}

' Концептуальные связи между сущностями приюта
User "1" -- "0..*" AdoptionRequest : "оформляет"
Animal "1" -- "0..*" AdoptionRequest : "фигурирует в"

User "1" -- "0..*" Donation : "совершает"
User "1" -- "0..*" VolunteerRequest : "подает анкету"

@enduml
```

## Структурные элементы домена

1. **Сущность `User` (Пользователь):** Описывает аккаунты пользователей, включая администраторов и волонтеров.

2. **Сущность `Animal` (Питомец):** Центральный объект системы. Хранит детальную информацию о животном для каталога.

3. **Сущность `AdoptionRequest` (Заявка на усыновление):** Связующая сущность, фиксирующая связь между пользователем и выбранным питомцем.

4. **Сущность `Supply` (Материальный ресурс):** Объект учета складских запасов, необходимых для обеспечения нужд приюта.

5. **Сущность `Donation` (Пожертвование):** Финансовая сущность, отражающая вклад пользователя в деятельность организации.

6. **Сущность `VolunteerRequest` (Анкета волонтера):** Заявка пользователя на участие в волонтерской деятельности приюта.