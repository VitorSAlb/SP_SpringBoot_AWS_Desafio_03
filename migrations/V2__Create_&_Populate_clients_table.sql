CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    birthday DATE,
    email VARCHAR(255) NOT NULL UNIQUE ,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

INSERT INTO clients (birthday, email, first_name, last_name) VALUES
    ('2000-01-01', 'clara@email.com', 'Clara', 'Albuquerque'),
    ('2000-01-01', 'vitor@email.com', 'Vitor', 'Albuquerque'),
    ('2000-01-01', 'geo@email.com', 'Geo', 'Albuquerque'),
    ('2000-01-01', 'carlos@email.com', 'Carlos', 'Albuquerque'),
    ('2000-01-01', 'lis@email.com', 'Lis', 'Muricy');

