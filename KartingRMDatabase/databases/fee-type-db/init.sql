CREATE TABLE fee_type (
    fee_type_id SERIAL PRIMARY KEY,
    lap_number INT NOT NULL,
    max_time INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    duration INT NOT NULL
);
