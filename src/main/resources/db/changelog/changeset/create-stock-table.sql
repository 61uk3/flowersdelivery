CREATE TABLE stock (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    store_id INT NOT NULL,
    flower_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (store_id) REFERENCES stores(id),
    FOREIGN KEY (flower_id) REFERENCES flowers(id)
);

ALTER TABLE stock
    ADD CONSTRAINT chk_price CHECK (price > 0.0);

ALTER TABLE stock
    ADD CONSTRAINT chk_quantity CHECK (quantity > 0);

INSERT INTO stock (store_id, flower_id, quantity, price) VALUES
(1, 1, 50, 200.00),
(2, 2, 70, 180.00),
(3, 3, 100, 160.00);