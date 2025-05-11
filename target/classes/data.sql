
-- Salones
INSERT INTO salones (code, name, location) VALUES
  ('SAL001', 'Auditorio Principal', 'Edificio A'),
  ('SAL002', 'Sala de Conferencias',  'Edificio B'),
  ('SAL003', 'Laboratorio de Computación', 'Edificio C');

-- Reservas
INSERT INTO reservas (name, surname, location, people_amount, date,    start_time, end_time, salon_id) VALUES
  ('Carlos', 'Gomez',      'Medellín', 20, '2025-05-12', '09:00', '11:00', 1),
  ('Maria',  'Rodriguez',  'Cali',     15, '2025-05-13', '13:00', '15:00', 2),
  ('Luis',   'Martinez',   'Bogotá',   30, '2025-05-14', '16:00', '18:00', 3);