-- ------- Data population -------

-- Configuration for period
INSERT INTO CONFIGURACION_PERIODO(PERIODO)
VALUES ('2025_01');

-- 1) Security users
INSERT INTO USUARIOS (USERNAME, PASSWORD_HASH, ROL, ENABLED, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED,
                      CREDENTIALS_NON_EXPIRED)
VALUES ('admin', '$2a$10$DzKRNGXAgXEjJ10JFUBAcepzN3hh.BXfYWC5/oiC/rcYL2Ml7Y3IG', 'COORDINADOR', TRUE, TRUE, TRUE, TRUE),
       ('coordinador', '$2a$10$DzKRNGXAgXEjJ10JFUBAcepzN3hh.BXfYWC5/oiC/rcYL2Ml7Y3IG', 'COORDINADOR', TRUE, TRUE, TRUE,
        TRUE),
       ('docente', '$2a$10$DzKRNGXAgXEjJ10JFUBAcepzN3hh.BXfYWC5/oiC/rcYL2Ml7Y3IG', 'DOCENTE', TRUE, TRUE, TRUE, TRUE),
       ('user', '$2a$10$DzKRNGXAgXEjJ10JFUBAcepzN3hh.BXfYWC5/oiC/rcYL2Ml7Y3IG', 'DOCENTE', TRUE, TRUE, TRUE, TRUE),
       ('evaluador', '$2a$10$DzKRNGXAgXEjJ10JFUBAcepzN3hh.BXfYWC5/oiC/rcYL2Ml7Y3IG', 'EVALUADOR_EXTERNO', TRUE, TRUE,
        TRUE, TRUE),
       ('COORDINADOR', '$2a$10$DzKRNGXAgXEjJ10JFUBAcepzN3hh.BXfYWC5/oiC/rcYL2Ml7Y3IG', 'COORDINADOR', TRUE, TRUE, TRUE,
        TRUE);

INSERT INTO DOCENTES (NOMBRE, APELLIDO, CORREO_ACADEMICO, TIPO_IDENTIFICACION, IDENTIFICACION, TIPO_DOCENTE, TITULO_ACADEMICO, ACTIVO, USUARIO_ID)
VALUES
    ('Carlos', 'Ramírez', 'cramirez@universidad.edu.co', 'CC', '1234567890', 'PLANTA', 'Magíster en Ingeniería de Software', TRUE, 3),
    ('Ana', 'González', 'agonzalez@universidad.edu.co', 'CC', '9876543210', 'CATEDRA', 'Especialista en Bases de Datos', TRUE, 4);


INSERT INTO COMPETENCIAS_PROGRAMA (CODIGO, DESCRIPCION, NIVEL)
VALUES
    ('CP01', 'Capacidad para diseñar software de calidad', 'AVANZADO'),
    ('CP02', 'Capacidad para realizar pruebas de software', 'INTERMEDIO'),
    ('CP03', 'Capacidad para trabajar en equipos ágiles', 'BASICO');

INSERT INTO RESULTADOS_PROGRAMA (CODIGO, DESCRIPCION, ID_COMPETENCIA)
VALUES
    ('RP01', 'El estudiante diseña soluciones usando patrones de diseño', 1),
    ('RP02', 'El estudiante realiza pruebas unitarias efectivas', 2),
    ('RP03', 'El estudiante participa activamente en metodologías ágiles', 3);

INSERT INTO ASIGNATURAS (CODIGO, NOMBRE, CREDITOS, OBJETIVOS, SEMESTRE, ACTIVA)
VALUES
    ('ASG101', 'Patrones de Diseño', 3, 'Aplicar patrones en proyectos de software', 6, TRUE),
    ('ASG102', 'Pruebas de Software', 2, 'Implementar pruebas unitarias y de integración', 5, TRUE),
    ('ASG103', 'Metodologías Ágiles', 2, 'Adoptar prácticas ágiles en el desarrollo de software', 4, TRUE);

INSERT INTO COMPETENCIAS_ASIGNATURA (CODIGO, DESCRIPCION, ID_COMPETENCIA)
VALUES
    ('CA01', 'Competencia específica para patrones', 1),
    ('CA02', 'Competencia específica para pruebas', 2),
    ('CA03', 'Competencia específica para metodologías ágiles', 3);

INSERT INTO ASIGNACIONES_ASIGNATURA (ID_COMPETENCIA, ID_ASIGNATURA, PERIODO, ID_DOCENTE)
VALUES
    (1, 1, '2025_01', 1),
    (2, 2, '2025_01', 1),
    (3, 3, '2025_01', 2);

INSERT INTO RESULTADOS_ASIGNATURA (CODIGO, DESCRIPCION, ID_COMPETENCIA)
VALUES
    ('RA01', 'Diseña software usando patrones de diseño', 1),
    ('RA02', 'Implementa pruebas unitarias', 2),
    ('RA03', 'Aplica metodologías ágiles', 3);


-- 2) Sample rubrics (simplified without ID_RA_ASIGNATURA)
INSERT INTO RUBRICAS (NOMBRE, DESCRIPCION, FECHA_CREACION, ESTADO)
VALUES ('Rúbrica para Patrones de Diseño', 'Evaluación de la aplicación de patrones de diseño en proyectos de software',
        '2024-05-15', 'ACTIVO'),
       ('Rúbrica para Pruebas Unitarias',
        'Evaluación de la implementación de pruebas unitarias en proyectos de software', '2024-05-20', 'ACTIVO'),
       ('Rúbrica para Metodologías Ágiles', 'Evaluación de la aplicación de metodologías ágiles en proyectos',
        '2024-05-25', 'ACTIVO'),
       ('Rúbrica para Diseño de BD', 'Evaluación del diseño de bases de datos relacionales', '2024-06-01', 'ACTIVO'),
       ('Rúbrica de Programación', 'Evaluación de habilidades de programación', CURRENT_TIMESTAMP, 'ACTIVO');
UPDATE RUBRICAS
SET ID_RA = 1
WHERE NOMBRE = 'Rúbrica para Patrones de Diseño';

UPDATE RUBRICAS
SET ID_RA = 2
WHERE NOMBRE = 'Rúbrica para Pruebas Unitarias';

UPDATE RUBRICAS
SET ID_RA = 3
WHERE NOMBRE = 'Rúbrica para Metodologías Ágiles';

-- 3) Sample criteria for rubrics
INSERT INTO CRITERIOS (NOMBRE, DESCRIPCION, PESO, RUBRICA_ID)
VALUES ('Selección de patrones', 'Correcta selección e implementación de patrones de diseño adecuados al problema',
        30.00, 1),
       ('Implementación', 'Calidad en la implementación de los patrones seleccionados', 40.00, 1),
       ('Documentación', 'Documentación clara y completa de los patrones utilizados', 30.00, 1),

       ('Cobertura de pruebas', 'Porcentaje del código cubierto por pruebas unitarias', 35.00, 2),
       ('Calidad de pruebas', 'Calidad y efectividad de las pruebas implementadas', 35.00, 2),
       ('Automatización', 'Nivel de automatización de las pruebas', 30.00, 2),

       ('Aplicación del Scrum', 'Correcta aplicación de la metodología Scrum', 25.00, 3),
       ('Uso de herramientas ágiles', 'Uso efectivo de herramientas para gestión ágil', 25.00, 3),
       ('Entregables incrementales', 'Calidad de los entregables incrementales', 25.00, 3),
       ('Retrospectivas', 'Calidad de las retrospectivas y mejora continua', 25.00, 3),

       ('Normalización', 'Nivel de normalización alcanzado en el diseño', 40.00, 4),
       ('Integridad referencial', 'Correcta implementación de la integridad referencial', 30.00, 4),
       ('Optimización', 'Optimización del diseño para el rendimiento', 30.00, 4),

       ('Calidad del Código', 'Evalúa la calidad y legibilidad del código fuente', 30.00, 5),
       ('Documentación', 'Evalúa la calidad y completitud de la documentación', 20.00, 5);

-- 4) Sample performance levels for criteria
INSERT INTO NIVELES_DESEMPENO (DESCRIPCION, PUNTAJE, CRITERIO_ID)
VALUES
    -- Niveles para "Selección de patrones"
    ('Excelente: Selecciona los patrones óptimos para el problema', 5.00, 1),
    ('Bueno: Selecciona patrones adecuados con pequeñas mejoras posibles', 4.00, 1),
    ('Regular: Selecciona algunos patrones adecuados pero con varias mejoras posibles', 3.00, 1),
    ('Deficiente: Selecciona patrones inadecuados o los implementa incorrectamente', 2.00, 1),

    -- Niveles para "Implementación"
    ('Excelente: Implementación clara, eficiente y mantenible de los patrones', 5.00, 2),
    ('Bueno: Implementación correcta con pequeños problemas de mantenibilidad', 4.00, 2),
    ('Regular: Implementación funcional pero con problemas de diseño', 3.00, 2),
    ('Deficiente: Implementación incorrecta o muy difícil de mantener', 2.00, 2),

    -- Niveles para "Documentación"
    ('Excelente: Documentación completa, clara y útil', 5.00, 3),
    ('Bueno: Documentación adecuada con pequeñas omisiones', 4.00, 3),
    ('Regular: Documentación básica con varias omisiones importantes', 3.00, 3),
    ('Deficiente: Documentación insuficiente o ausente', 2.00, 3),

    -- Niveles para "Cobertura de pruebas"
    ('Excelente: >90% de cobertura en código crítico', 5.00, 4),
    ('Bueno: 75-90% de cobertura en código crítico', 4.00, 4),
    ('Regular: 50-75% de cobertura en código crítico', 3.00, 4),
    ('Deficiente: <50% de cobertura en código crítico', 2.00, 4),

    -- Niveles para "Calidad de pruebas"
    ('Excelente: Pruebas exhaustivas que validan todos los escenarios importantes', 5.00, 5),
    ('Bueno: Pruebas que cubren la mayoría de los escenarios importantes', 4.00, 5),
    ('Regular: Pruebas básicas que cubren algunos escenarios importantes', 3.00, 5),
    ('Deficiente: Pruebas insuficientes o que no validan escenarios importantes', 2.00, 5),

    -- Niveles para "Calidad del Código"
    ('Excelente: Código limpio, bien estructurado y altamente mantenible', 5.00, 14),
    ('Bueno: Código estructurado con algunos aspectos mejorables', 4.00, 14),
    ('Regular: Código funcional pero con problemas de estructura', 3.00, 14),
    ('Deficiente: Código desorganizado y difícil de mantener', 2.00, 14),

    -- Niveles para "Documentación" de programación
    ('Excelente: Documentación completa, clara y bien estructurada', 5.00, 15),
    ('Bueno: Documentación adecuada con pequeñas omisiones', 4.00, 15),
    ('Regular: Documentación básica con omisiones importantes', 3.00, 15),
    ('Deficiente: Documentación insuficiente o ausente', 2.00, 15);


INSERT INTO ESTUDIANTES (NOMBRE_COMPLETO, CORREO_INSTITUCIONAL, CODIGO)
VALUES
    ('Laura Sánchez', 'laura.sanchez@universidad.edu.co', 20211001),
    ('Julián Torres', 'julian.torres@universidad.edu.co', 20211002),
    ('María Fernanda Gómez', 'maria.gomez@universidad.edu.co', 20211003);

INSERT INTO EVALUACIONES (ID_ESTUDIANTE, ID_RUBRICA, ID_EVALUADOR, FECHA, COMENTARIOS, FINALIZADA)
VALUES
    (1, 1, 5, '2025-05-10', 'Buen trabajo, solo falta mejorar la documentación.', TRUE),
    (2, 2, 5, '2025-05-12', 'Cobertura adecuada pero falta calidad en las pruebas.', TRUE),
    (3, 3, 5, '2025-05-15', 'Muy buena participación en sprints y retrospectivas.', TRUE);


INSERT INTO EVALUACION_CRITERIO (ID_EVALUACION, ID_CRITERIO, NIVEL_SELECCIONADO, NOTA)
VALUES
-- Eval 1 (Rubrica 1)
(1, 1, 'Excelente: Selecciona los patrones óptimos para el problema', 5.00),
(1, 2, 'Bueno: Implementación correcta con pequeños problemas de mantenibilidad', 4.00),
(1, 3, 'Regular: Documentación básica con varias omisiones importantes', 3.00),

-- Eval 2 (Rubrica 2)
(2, 4, 'Bueno: 75-90% de cobertura en código crítico', 4.00),
(2, 5, 'Regular: Pruebas básicas que cubren algunos escenarios importantes', 3.00),

-- Eval 3 (Rubrica 3)
(3, 7, 'Excelente: Correcta aplicación de la metodología Scrum', 5.00),
(3, 8, 'Bueno: Uso efectivo de herramientas para gestión ágil', 4.00),
(3, 9, 'Bueno: Calidad de los entregables incrementales', 4.00),
(3, 10, 'Bueno: Calidad de las retrospectivas y mejora continua', 4.00);
