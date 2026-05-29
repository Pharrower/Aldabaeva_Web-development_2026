-- Инициализация таблиц БД приюта "Доброе сердце"

DROP TABLE IF EXISTS adoption_requests;
DROP TABLE IF EXISTS animals;
DROP TABLE IF EXISTS supplies;
DROP TABLE IF EXISTS users;

-- 1. Таблица пользователей
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- 2. Таблица животных
CREATE TABLE animals (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    species VARCHAR(30) NOT NULL,
    age INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    description TEXT
);

-- 3. Таблица материального обеспечения (склад)
CREATE TABLE supplies (
    id BIGSERIAL PRIMARY KEY,
    item_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    current_quantity INT NOT NULL,
    max_capacity INT NOT NULL
);

-- 4. Таблица заявок на усыновление
CREATE TABLE adoption_requests (
    id BIGSERIAL PRIMARY KEY,
    animal_id BIGINT REFERENCES animals(id) ON DELETE CASCADE,
    applicant_name VARCHAR(100) NOT NULL,
    applicant_phone VARCHAR(20) NOT NULL,
    status VARCHAR(30) NOT NULL,
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Вставка тестовых данных для демонстрации работы прогресс-бара и AJAX
INSERT INTO supplies (item_name, category, current_quantity, max_capacity) VALUES
('Сухой корм для собак Premium', 'Корма', 75, 100),
('Влажный корм для кошек', 'Корма', 40, 200),
('Вакцины антирабические', 'Медикаменты', 15, 50);

INSERT INTO animals (name, species, age, status, description) VALUES
('Бим', 'Собака', 2, 'Доступен', 'Дружелюбный пес, любит детей.'),
('Мурка', 'Кошка', 1, 'На карантине', 'Спокойная ласковая кошка.');