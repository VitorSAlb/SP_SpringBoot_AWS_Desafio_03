CREATE TABLE orders.orders (
                               id SERIAL PRIMARY KEY,
                               email VARCHAR(255) NOT NULL
);

CREATE TABLE orders.order_products (
                                       order_id INT,
                                       name VARCHAR(255) NOT NULL,
                                       price DECIMAL(10, 2) NOT NULL,
                                       quantity INT NOT NULL,
                                       PRIMARY KEY (order_id, name),
                                       FOREIGN KEY (order_id) REFERENCES orders.orders(id),
                                       FOREIGN KEY (name) REFERENCES inventory.products(name)
);


INSERT INTO orders.orders (email) VALUES
                                      ('vitor@email.com'),
                                      ('carlos@email.com'),
                                      ('lis@email.com'),
                                      ('clara@email.com');


INSERT INTO orders.order_products (order_id, name, price, quantity) VALUES
                                                                        (1, 'notebook', 999.99, 1),
                                                                        (1, 'mouse', 20.00, 1),
                                                                        (2, 'banana', 1300.50, 1),
                                                                        (3, 'pencil', 1.90, 1),
                                                                        (4, 'iphone', 699.90, 2);
