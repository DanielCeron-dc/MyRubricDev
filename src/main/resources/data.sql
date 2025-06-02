-- ------- Data population -------

-- 1) Security users
INSERT INTO USUARIOS (USERNAME, PASSWORD_HASH, ROL, ENABLED, ACCOUND_NON_EXPIRED, ACCOUNT_NON_LOCKED, CREDENTIALS_NON_EXPIRED) VALUES
    ('admin',      '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'ADMIN',                TRUE, TRUE, TRUE, TRUE),
    ('coordinador','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'COORDINADOR',         TRUE, TRUE, TRUE, TRUE),
    ('docente',    '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'DOCENTE',             TRUE, TRUE, TRUE, TRUE),
    ('user',       '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'USER',                TRUE, TRUE, TRUE, TRUE),
    ('evaluador',  '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'EVALUADOR_EXTERNO',    TRUE, TRUE, TRUE, TRUE
);

-- 2) Rooms (SALONES)
INSERT INTO SALONES (ID, CODE, NAME, LOCATION) VALUES
    (1, 'SAL001', 'Auditorio Central',   'Edificio A'),
    (2, 'SAL002', 'Sala de Conferencias','Edificio B'),
    (3, 'SAL003', 'Laboratorio 1',       'Edificio C'),
    (4, 'SAL004', 'Sala Multimedia',     'Edificio D'),
    (5, 'SAL005', 'Aula Magna',          'Edificio E'
);

-- 3) Reservations (RESERVAS)
INSERT INTO RESERVAS (NAME, SURNAME, LOCATION, PEOPLE_AMOUNT, DATE, START_TIME, END_TIME, SALON_ID, STATUS) VALUES
    ('Carlos',    'Gomez',    'Cali',    20, '2025-06-01', '09:00:00', '11:00:00', 1, 'pending'),
    ('Mariana',   'Lopez',    'Popayán', 15, '2025-06-02', '13:00:00', '15:00:00', 2, 'pending'),
    ('Luis',      'Martinez', 'Cali',    30, '2025-06-03', '08:30:00', '10:30:00', 3, 'pending'),
    ('Ana',       'Rodríguez','Cauca',   25, '2025-06-04', '14:00:00', '16:00:00', 4, 'pending'),
    ('Felipe',    'Hernández','Cali',    50, '2025-06-05', '10:00:00', '12:00:00', 5, 'pending'),
    ('Sofía',     'Ramírez',  'Popayán', 10, '2025-06-06', '12:00:00', '13:00:00', 1, 'pending'),
    ('Andrés',    'Pérez',    'Cali',    40, '2025-06-07', '15:00:00', '17:00:00', 2, 'pending'),
    ('Valentina', 'García',   'Cauca',   35, '2025-06-08', '09:30:00', '11:30:00', 3, 'pending'),
    ('Diego',     'Castro',   'Popayán', 28, '2025-06-09', '11:00:00', '13:00:00', 4, 'pending'),
    ('Laura',     'Vargas',   'Cali',    18, '2025-06-10', '16:00:00', '18:00:00', 5, 'pending'
    );