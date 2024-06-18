CREATE TABLE flowers (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(50) NOT NULL
);

INSERT INTO flowers (name, color) VALUES
('Роза', 'Красный'),
('Тюльпан', 'Жёлтый'),
('Лилия', 'Белый');