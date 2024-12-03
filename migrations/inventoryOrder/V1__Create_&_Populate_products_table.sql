CREATE TABLE IF NOT EXISTS inventory.products (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255),
    name VARCHAR(255) NOT NULL UNIQUE ,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL
);

INSERT INTO inventory.products (description, name, price, quantity) VALUES
    ('test', 'notebook', 999.99, 20),
    ('test', 'iphone', 699.90, 20),
    ('test', 'pencil', 1.90, 20),
    ('test', 'mouse', 20.00, 20),
    ('test', 'banana', 1300.50, 20);
