CREATE TABLE usuario (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

CREATE TABLE prestamo (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL REFERENCES usuario (id),
    monto NUMERIC(14, 2) NOT NULL,
    plazo_meses INTEGER NOT NULL,
    estado VARCHAR(20) NOT NULL,
    version BIGINT NOT NULL,
    creado_en TIMESTAMPTZ NOT NULL
);
