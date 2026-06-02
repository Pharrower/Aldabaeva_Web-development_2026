# Диаграмма сущностей и связей (Entity-Relationship Diagram)

## Описание
ER-диаграмма отображает физическую структуру таблиц в СУБД PostgreSQL, типы данных колонок, обязательность заполнения полей (`NOT NULL`) и логические связи между таблицами (один-ко-многим). Моделирование выполнено в нотации Crow's Foot («Птичья лапка»).

## Визуализация диаграммы

![ER-диаграмма базы данных](images/er-diagram.png)

## Исходный код (PlantUML):

```plantuml
@startuml
skinparam linetype ortho
skinparam shadowing false

title Реляционная ER-диаграмма базы данных ИС Приют "Доброе сердце" (PostgreSQL)

entity "users" as users {
  * id : bigint <<generated>> [PK]
  --
  email : varchar(255)
  first_name : varchar(255)
  password : varchar(255)
}

entity "animals" as animals {
  * id : bigint <<generated>> [PK]
  --
  name : varchar(255)
  species : varchar(100)
  gender : varchar(50)
  age : integer
  weight : double precision
  description : text
  image_url : varchar(255)
}

entity "adoption_requests" as adopt {
  * id : bigint <<generated>> [PK]
  --
  user_id : bigint <<FK>>
  animal_id : bigint <<FK>>
  message : text
  status : varchar(50)
}

entity "supplies" as supplies {
  * id : bigint <<generated>> [PK]
  --
  name : varchar(255)
  current_amount : double precision
  target_amount : double precision
  unit : varchar(50)
}

entity "donations" as donations {
  * id : bigint <<generated>> [PK]
  --
  user_id : bigint <<FK>>
  amount : double precision
  donation_date : timestamp
}

entity "volunteer_requests" as vol_req {
  * id : bigint <<generated>> [PK]
  --
  user_id : bigint <<FK>>
  status : varchar(50)
  application_date : timestamp
}

' Физические связи между таблицами по внешним ключам (нотация "лапки")
users ||--o{ adopt : "user_id -> id"
animals ||--o{ adopt : "animal_id -> id"

users ||--o{ donations : "user_id -> id"
users ||--o{ vol_req : "user_id -> id"

@enduml

```

## Описание реляционных связей
* **`users` (Пользователи):** Центральная таблица, связывающая персонал и волонтеров с их активностями (заявками на адопцию, пожертвованиями и заявками на волонтерство).
* **`animals` ───< `adoption_requests` (Один-ко-многим):** Одно животное может фигурировать в нескольких заявках. Реализовано через внешний ключ `animal_id`.
* **`supplies` (Склад):** Учет ресурсов приюта.
* **`donations` (Пожертвования):** Хранит историю финансовых взносов от пользователей.
* **`volunteer_requests` (Заявки волонтеров):** Хранит статус рассмотрения заявок от кандидатов в волонтеры.