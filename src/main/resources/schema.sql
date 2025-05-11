
CREATE TABLE
    salones (
        id INT AUTO_INCREMENT PRIMARY KEY,
        code VARCHAR(100),
        name VARCHAR(255),
        location VARCHAR(255)
    );

CREATE TABLE
    reservas (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255),
        surname VARCHAR(255),
        location VARCHAR(255),
        people_amount INT,
        date DATE,
        start_time TIME,
        end_time TIME,
        salon_id INT,
        FOREIGN KEY (salon_id) REFERENCES salones (id)
    );