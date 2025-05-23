-- Drop existing tables if they exist
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS salones;
DROP TABLE IF EXISTS usuarios;

-- Table: usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create index on username for faster lookups
CREATE INDEX idx_usuarios_username ON usuarios(username);

-- Create index on role for authorization queries
CREATE INDEX idx_usuarios_rol ON usuarios(rol);

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
