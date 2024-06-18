CREATE TABLE stores (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL
);

INSERT INTO stores (name, address) VALUES
('Рай Цветов', 'ул. Рыбинская, 14'),
('Ириски', 'ул. Ленинградская, 24'),
('Цветочный Дворик', 'пр. Ленина, 110А');