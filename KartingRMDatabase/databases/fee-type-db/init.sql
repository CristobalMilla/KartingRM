CREATE TABLE fee_type (
    fee_type_id SERIAL PRIMARY KEY,
    lap_number INT NOT NULL,
    max_time INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    duration INT NOT NULL
);
INSERT INTO fee_type (lap_number, max_time, price, duration)
VALUES 
    (10, 15000, 10.00, 30),
    (15, 20000, 15.00, 35),
    (20, 25000, 20.00, 40);