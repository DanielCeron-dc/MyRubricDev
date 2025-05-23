-- Test users with BCrypt-hashed passwords
-- All users have the password 'password123'
-- BCrypt hash for 'password123' is '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.'
INSERT INTO usuarios (username, password_hash, rol, enabled, account_non_expired, account_non_locked, credentials_non_expired) VALUES
    -- Admin user
    ('admin', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'ADMIN', TRUE, TRUE, TRUE, TRUE),
    
    -- Coordinator user
    ('coordinador', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'COORDINADOR', TRUE, TRUE, TRUE, TRUE),
    
    -- Teacher user
    ('docente', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'DOCENTE', TRUE, TRUE, TRUE, TRUE),
    
    -- Regular user
    ('user', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'USER', TRUE, TRUE, TRUE, TRUE),
    
    -- Evaluator user
    ('evaluador', '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'EVALUADOR_EXTERNO', TRUE, TRUE, TRUE, TRUE);

-- Dummy data for salones
INSERT INTO salones (id, code, name, location) VALUES
    (1, 'SAL001', 'Auditorio Central', 'Edificio A'),
    (2, 'SAL002', 'Sala de Conferencias', 'Edificio B'),
    (3, 'SAL003', 'Laboratorio 1',       'Edificio C'),
    (4, 'SAL004', 'Sala Multimedia',     'Edificio D'),
    (5, 'SAL005', 'Aula Magna',          'Edificio E');

-- Dummy data for reservas
INSERT INTO reservas (name, surname, location, people_amount, date, start_time, end_time, salon_id, status) VALUES
    ('Carlos',   'Gomez',    'Cali',    20, '2025-06-01', '09:00:00', '11:00:00', 1, 'pending'),
    ('Mariana',  'Lopez',    'Popayán', 15, '2025-06-02', '13:00:00', '15:00:00', 2, 'pending'),
    ('Luis',     'Martinez', 'Cali',    30, '2025-06-03', '08:30:00', '10:30:00', 3, 'pending'),
    ('Ana',      'Rodríguez','Cauca',   25, '2025-06-04', '14:00:00', '16:00:00', 4, 'pending'),
    ('Felipe',   'Hernández','Cali',    50, '2025-06-05', '10:00:00', '12:00:00', 5, 'pending'),
    ('Sofía',    'Ramírez',  'Popayán', 10, '2025-06-06', '12:00:00', '13:00:00', 1, 'pending'),
    ('Andrés',   'Pérez',    'Cali',    40, '2025-06-07', '15:00:00', '17:00:00', 2, 'pending'),
    ('Valentina','García',   'Cauca',   35, '2025-06-08', '09:30:00', '11:30:00', 3, 'pending'),
    ('Diego',    'Castro',   'Popayán', 28, '2025-06-09', '11:00:00', '13:00:00', 4, 'pending'),
    ('Laura',    'Vargas',   'Cali',    18, '2025-06-10', '16:00:00', '18:00:00', 5, 'pending');
