CREATE TABLE frequency_discount (
    frequency_discount_id SERIAL PRIMARY KEY,
    category VARCHAR(255) NOT NULL,
    min_frequency INT NOT NULL,
    max_frequency INT NOT NULL,
    discount NUMERIC(10, 2) NOT NULL
);
INSERT INTO frequency_discount (category, min_frequency, max_frequency, discount)
VALUES 
    ('No frecuente', 0, 1, 1.00),
    ('Regular', 2, 4, 0.90),
    ('Frecuente', 5, 6, 0.80),
    ('Muy frecuente', 7, 1000, 0.70);
