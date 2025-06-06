CREATE TABLE people_discount (
    people_discount_id SERIAL PRIMARY KEY,
    min_people INT NOT NULL,
    max_people INT NOT NULL,
    discount NUMERIC(10, 2) NOT NULL
);
INSERT INTO people_discount (min_people, max_people, discount)
VALUES 
    (1, 2, 1),
    (3, 5, 0.90),
    (6, 10, 0.80),
    (11, 15, 0.70);