<<<<<<< Updated upstream
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
=======
CREATE DATABASE IF NOT EXISTS SistemaRA;
USE SistemaRA;
>>>>>>> Stashed changes

-- USUARIOS
CREATE TABLE Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(150) UNIQUE NOT NULL,
    tipo_identificacion VARCHAR(20),
    identificacion VARCHAR(50) UNIQUE NOT NULL,
    rol ENUM('Coordinador', 'Docente', 'Evaluador') NOT NULL,
    tipo_docente ENUM('Catedra', 'Tiempo Completo', 'Planta') DEFAULT NULL,
    titulo_academico VARCHAR(100),
    contrasena_hash VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- COMPETENCIAS DEL PROGRAMA
CREATE TABLE CompetenciasPrograma (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    descripcion TEXT NOT NULL,
    nivel ENUM('Basico', 'Intermedio', 'Avanzado') NOT NULL
);

-- RESULTADOS DE APRENDIZAJE DEL PROGRAMA (RAP)
CREATE TABLE ResultadosPrograma (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    descripcion TEXT NOT NULL,
    id_competencia INT,
    FOREIGN KEY (id_competencia) REFERENCES CompetenciasPrograma(id)
);

-- ASIGNATURAS
CREATE TABLE Asignaturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    creditos INT,
    semestre INT,
    activa BOOLEAN DEFAULT TRUE
);

-- VINCULACIÓN DE COMPETENCIAS Y RAP A ASIGNATURAS
CREATE TABLE AsignaturaCompetencia (
    id_asignatura INT,
    id_competencia INT,
    PRIMARY KEY (id_asignatura, id_competencia),
    FOREIGN KEY (id_asignatura) REFERENCES Asignaturas(id),
    FOREIGN KEY (id_competencia) REFERENCES CompetenciasPrograma(id)
);

CREATE TABLE AsignaturaRAP (
    id_asignatura INT,
    id_rap INT,
    PRIMARY KEY (id_asignatura, id_rap),
    FOREIGN KEY (id_asignatura) REFERENCES Asignaturas(id),
    FOREIGN KEY (id_rap) REFERENCES ResultadosPrograma(id)
);

-- RESULTADOS DE APRENDIZAJE POR ASIGNATURA
CREATE TABLE ResultadosAsignatura (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descripcion TEXT NOT NULL,
    id_asignatura INT NOT NULL,
    FOREIGN KEY (id_asignatura) REFERENCES Asignaturas(id)
);

-- RÚBRICAS
CREATE TABLE Rubricas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    id_ra_asignatura INT NOT NULL,
    FOREIGN KEY (id_ra_asignatura) REFERENCES ResultadosAsignatura(id)
);

-- CRITERIOS DE EVALUACIÓN POR RÚBRICA
CREATE TABLE Criterios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_rubrica INT NOT NULL,
    descripcion TEXT NOT NULL,
    ponderacion DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (id_rubrica) REFERENCES Rubricas(id)
);

-- NIVELES DE DESEMPEÑO POR CRITERIO
CREATE TABLE NivelesDesempeno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_criterio INT NOT NULL,
    nombre_nivel VARCHAR(50) NOT NULL,
    descripcion TEXT NOT NULL,
    nota_min DECIMAL(3,2) NOT NULL,
    nota_max DECIMAL(3,2) NOT NULL,
    FOREIGN KEY (id_criterio) REFERENCES Criterios(id)
);

-- EVALUACIONES
CREATE TABLE Evaluaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante INT NOT NULL,
    id_rubrica INT NOT NULL,
    id_evaluador INT NOT NULL,
    fecha DATE DEFAULT CURRENT_DATE,
    comentarios TEXT,
    finalizada BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_estudiante) REFERENCES Usuarios(id),
    FOREIGN KEY (id_rubrica) REFERENCES Rubricas(id),
    FOREIGN KEY (id_evaluador) REFERENCES Usuarios(id)
);

-- RESULTADO POR CRITERIO
CREATE TABLE EvaluacionCriterio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_evaluacion INT NOT NULL,
    id_criterio INT NOT NULL,
    nivel_seleccionado VARCHAR(50),
    nota DECIMAL(4,2),
    FOREIGN KEY (id_evaluacion) REFERENCES Evaluaciones(id),
    FOREIGN KEY (id_criterio) REFERENCES Criterios(id)
);