# Диаграмма классов проектирования (Class Diagram)

Данная диаграмма демонстрирует взаимодействие между компонентами системы на примере модуля учета материальных запасов. Она отражает реализацию паттерна PCMEF и принципа инверсии управления (IoC).

## Визуализация
![Диаграмма классов](images/class-diagram.png)\

## PlantUML
```plantuml
@startuml
skinparam classAttributeIconSize 0
skinparam shadowing false
skinparam linetype ortho

title Диаграмма классов проектирования (Фрагмент: Учет материальных запасов)

interface SupplyRepository <<interface>> {
  + findAll() : List<Supply>
  + save(Supply) : Supply
}

class AnimalService {
  - supplyRepository : SupplyRepository
  + getAllSupplies() : List<Supply>
  + saveSupply(Supply) : void
}

class ShelterAdminController {
  - animalService : AnimalService
  + showHelpPage(Model) : String
  + manageSupplies(Model) : String
  + saveSupply(Supply) : String
}

' Связи зависимости и внедрения зависимостей (DI)
ShelterAdminController --> AnimalService : "делегирует логику"
AnimalService --> SupplyRepository : "вызывает методы JPA"

@enduml
```

## Описание связей
* **ShelterAdminController ──> AnimalService:** Контроллер не создает сервис самостоятельно, он получает его через конструктор (Dependency Injection), что позволяет легко подменять реализацию (например, для тестирования).
* **AnimalService ──> SupplyRepository:** Слой сервиса обращается к данным через интерфейс репозитория, скрывая детали реализации запросов к БД.
* **Свойства:** * `-` (private): Инкапсуляция полей зависимостей.
  * `+` (public): Публичный контракт методов контроллера и сервиса.

---
*Диаграмма подтверждает соблюдение принципа слабой связанности (Loose Coupling): компоненты взаимодействуют через интерфейсы и абстракции, а не через прямые экземпляры классов.*