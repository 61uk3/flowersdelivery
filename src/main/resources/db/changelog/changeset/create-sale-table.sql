CREATE TABLE sales (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    store_id INT NOT NULL,
    flower_id INT NOT NULL,
    quantity INT NOT NULL,
    sale_date DATE NOT NULL,
    price DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (store_id) REFERENCES stores(id),
    FOREIGN KEY (flower_id) REFERENCES flowers(id)
);

ALTER TABLE sales
    ADD CONSTRAINT chk_price CHECK (price > 0.0);

ALTER TABLE sales
    ADD CONSTRAINT chk_quantity CHECK (quantity > 0);

INSERT INTO sales (store_id, flower_id, quantity, sale_date, price) VALUES
(1, 1, 10, '2024-06-04', 200),
(2, 2, 20, '2024-06-05', 180),
(3, 3, 30, '2024-06-06', 160);