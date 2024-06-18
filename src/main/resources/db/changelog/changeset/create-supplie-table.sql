CREATE TABLE supplies (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    store_id INT NOT NULL,
    flower_id INT NOT NULL,
    quantity INT NOT NULL,
    supply_date DATE NOT NULL,
    price DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (store_id) REFERENCES stores(id),
    FOREIGN KEY (flower_id) REFERENCES flowers(id)
);

ALTER TABLE supplies
    ADD CONSTRAINT chk_price CHECK (price > 0.0);

ALTER TABLE supplies
    ADD CONSTRAINT chk_quantity CHECK (quantity > 0);

INSERT INTO supplies (store_id, flower_id, quantity, supply_date, price) VALUES
(1, 1, 100, '2024-06-01', 100.00),
(2, 2, 150, '2024-06-02', 90.00),
(3, 3, 200, '2024-06-03', 80.00);