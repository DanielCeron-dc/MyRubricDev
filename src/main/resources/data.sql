-- ------- Data population -------

-- 1) Security users
INSERT INTO USUARIOS (USERNAME, PASSWORD_HASH, ROL, ENABLED, ACCOUNT_NON_EXPIRED, ACCOUNT_NON_LOCKED, CREDENTIALS_NON_EXPIRED) VALUES
    ('admin',      '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'ADMIN',                TRUE, TRUE, TRUE, TRUE),
    ('coordinador','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'COORDINADOR',         TRUE, TRUE, TRUE, TRUE),
    ('docente',    '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'DOCENTE',             TRUE, TRUE, TRUE, TRUE),
    ('user',       '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'USER',                TRUE, TRUE, TRUE, TRUE),
    ('evaluador',  '$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.', 'EVALUADOR_EXTERNO',    TRUE, TRUE, TRUE, TRUE
);
