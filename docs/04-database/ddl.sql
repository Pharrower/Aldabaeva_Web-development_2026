-- ====================================================================
-- Скрипт инициализации структуры БД (DDL) приюта "Доброе сердце"
-- Целевая СУБД: PostgreSQL 17
-- ====================================================================

-- Очистка старых таблиц при пересоздании (с учетом зависимостей)
DROP TABLE IF EXISTS adoption_requests;
DROP TABLE IF EXISTS animals;
DROP TABLE IF EXISTS supplies;
DROP TABLE IF EXISTS users;

-- 1. Таблица волонтеров / администраторов
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- 2. Таблица карточек питомцев приюта
CREATE TABLE animals (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    species VARCHAR(30) NOT NULL,
    age INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    description TEXT
);

-- 3. Таблица учета материального обеспечения (склад)
CREATE TABLE supplies (
    id BIGSERIAL PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    current_quantity INT NOT NULL,
    max_capacity INT NOT NULL
);

-- 4. Таблица заявок на адопцию животных
CREATE TABLE adoption_requests (
    id BIGSERIAL PRIMARY KEY,
    animal_id BIGINT NOT NULL,
    applicant_name VARCHAR(100) NOT NULL,
    applicant_phone VARCHAR(20) NOT NULL,
    status VARCHAR(30) NOT NULL,
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_animal FOREIGN KEY (animal_id) REFERENCES animals(id) ON DELETE CASCADE
);

-- Создание системных индексов для оптимизации поисковых запросов в каталоге
CREATE INDEX idx_animals_status ON animals(status);
CREATE INDEX idx_adoption_animal ON adoption_requests(animal_id);