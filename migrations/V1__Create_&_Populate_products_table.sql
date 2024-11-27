CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    name VARCHAR(255) NOT NULL UNIQUE ,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL
);

INSERT INTO products (description, name, price, quantity) VALUES
    ('test', 'Notebook', 999.99, 20),
    ('test', 'Iphone', 699.90, 20),
    ('test', 'Pencil', 1.90, 20),
    ('test', 'Mouse', 20.00, 20),
    ('test', 'Banana', 1300.50, 20);
