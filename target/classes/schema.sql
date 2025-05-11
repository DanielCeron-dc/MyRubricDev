-- Drop existing tables if they exist
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS salones;

-- Table: salones
CREATE TABLE salones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

-- Table: reservas
CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    people_amount INT NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    salon_id INT NOT NULL,
    status VARCHAR(10) NOT NULL DEFAULT 'pending',
    FOREIGN KEY (salon_id) REFERENCES salones(id)
);
