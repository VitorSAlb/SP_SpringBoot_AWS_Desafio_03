CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE order_products (
    order_id INT,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (order_id, name),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (name) REFERENCES products(name)
);

INSERT INTO orders (email) VALUES
    ('vitor@email.com'),
    ('carlos@email.com'),
    ('lis@email.com'),
    ('clara@email.com');


INSERT INTO order_products (order_id, name, price, quantity) VALUES
    (1, 'Notebook', 999.99, 1),
    (1, 'Mouse', 20.00, 1),
    (2, 'Banana', 1300.50, 1),
    (3, 'Pencil', 1.90, 1),
    (4, 'Iphone', 699.90, 2);
