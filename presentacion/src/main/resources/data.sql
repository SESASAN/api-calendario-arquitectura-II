INSERT INTO tipo (id, tipo) VALUES
    (1, 'Día laboral'),
    (2, 'Fin de Semana'),
    (3, 'Día festivo')
ON CONFLICT (id) DO NOTHING;

SELECT setval(pg_get_serial_sequence('tipo', 'id'), (SELECT MAX(id) FROM tipo));
