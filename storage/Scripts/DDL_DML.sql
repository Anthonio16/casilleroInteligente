-- database: storage\Databases\casilleroInteligente.sqlite

DROP TABLE IF EXISTS RegistroEvento;
DROP TABLE IF EXISTS Tokenacceso;
DROP TABLE IF EXISTS Solicitud;
DROP TABLE IF EXISTS EstadoSolicitud;
DROP TABLE IF EXISTS CredencialCasillero;
DROP TABLE IF EXISTS Casillero;
DROP TABLE IF EXISTS EstadoCasillero;
DROP TABLE IF EXISTS UsuarioTipo;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS TipoEvento;



-- =========================
--        TABLAS
-- =========================

CREATE TABLE TipoEvento (
    idTipoEvento        INTEGER PRIMARY KEY AUTOINCREMENT,
    Nombre              VARCHAR(50) NOT NULL UNIQUE,
    Descripcion         VARCHAR(100) NULL,
    FechaCreacion       DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);


CREATE TABLE Usuario (
    idUsuario           INTEGER PRIMARY KEY AUTOINCREMENT,
    idUsuarioTipo       INTEGER NOT NULL REFERENCES UsuarioTipo(idUsuarioTipo),
    Nombre              VARCHAR(50) NOT NULL,
    Clave               VARCHAR(20) NOT NULL,
    Descripcion         VARCHAR(100) NULL,
    Estado              VARCHAR(10) NOT NULL DEFAULT 'Activo',
    FechaCreacion       DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE UsuarioTipo (
    idUsuarioTipo       INTEGER PRIMARY KEY AUTOINCREMENT,
    Nombre              VARCHAR(20) NOT NULL UNIQUE,
    Descripcion         VARCHAR(100) NULL,
    Estado              VARCHAR(10) NOT NULL DEFAULT 'Activo',
    FechaCreacion       DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);
CREATE TABLE EstadoCasillero (
    idEstadoCasillero   INTEGER PRIMARY KEY AUTOINCREMENT,
    Nombre              VARCHAR(20) NOT NULL UNIQUE,
    Descripcion         VARCHAR(100) NULL,
    Estado              VARCHAR(10) NOT NULL DEFAULT 'Activo',
    FechaCreacion       DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE Casillero (
    idCasillero         INTEGER PRIMARY KEY AUTOINCREMENT,
    idEstadoCasillero   INTEGER NOT NULL REFERENCES EstadoCasillero(idEstadoCasillero),
    idEstudiante        INTEGER NULL REFERENCES Usuario(idUsuario),
    IntentosFallidos    INTEGER NOT NULL DEFAULT 0,
    Descripcion         VARCHAR(100) NULL,
    Estado              VARCHAR(10) NOT NULL DEFAULT 'Activo',
    FechaCreacion       DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);

-- 1 credencial por casillero (UNIQUE recomendado)
CREATE TABLE CredencialCasillero (
    idCredencialCasillero INTEGER PRIMARY KEY AUTOINCREMENT,
    idCasillero           INTEGER NOT NULL UNIQUE REFERENCES Casillero(idCasillero),
    pinHash               TEXT NOT NULL
);

CREATE TABLE EstadoSolicitud (
    idEstadoSolicitud   INTEGER PRIMARY KEY AUTOINCREMENT,
    Nombre              VARCHAR(20) NOT NULL UNIQUE,
    Descripcion         VARCHAR(100) NULL,
    FechaCreacion       DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE Solicitud (
    idSolicitud         INTEGER PRIMARY KEY AUTOINCREMENT,
    idCasillero         INTEGER NOT NULL REFERENCES Casillero(idCasillero),
    idAdmin             INTEGER NOT NULL REFERENCES Usuario(idUsuario),
    FechaCreacion       DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE Tokenacceso (
    idTokenacceso       INTEGER PRIMARY KEY AUTOINCREMENT,
    idSolicitud         INTEGER NOT NULL REFERENCES Solicitud(idSolicitud),
    idCasillero         INTEGER NOT NULL REFERENCES Casillero(idCasillero),
    TokenHash           TEXT NOT NULL,
    Estado              VARCHAR(10) NOT NULL DEFAULT 'Activo',
    FechaCreacion       DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);

-- IMPORTANTE: se agrega idCasillero para poder saber el evento del casillero
CREATE TABLE RegistroEvento (
    idRegistroEvento    INTEGER PRIMARY KEY AUTOINCREMENT,
    idTipoEvento        INTEGER NOT NULL REFERENCES TipoEvento(idTipoEvento),
    idUsuario           INTEGER NOT NULL REFERENCES Usuario(idUsuario),
    idCasillero         INTEGER NOT NULL REFERENCES Casillero(idCasillero),
    FechaCreacion       DATETIME NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);

PRAGMA foreign_keys = ON;

-- =========================
--        INSERTS
-- =========================

INSERT INTO UsuarioTipo (Nombre, Descripcion) VALUES
('Admin', 'Administrador del sistema'),
('Estudiante', 'Usuario estudiante');

INSERT INTO EstadoCasillero (Nombre, Descripcion) VALUES
('Ready to Unlock', 'Casillero disponible para asignación'),
('Locked', 'Casillero actualmente asignado a un usuario');

INSERT INTO EstadoSolicitud (Nombre, Descripcion) VALUES
('Pendiente', 'Solicitud en espera de revisión'),
('Aprobada', 'Solicitud aprobada por el administrador'),
('Rechazada', 'Solicitud rechazada por el administrador');

INSERT INTO TipoEvento (Nombre, Descripcion) VALUES
('Pin OK', 'Ingreso correcto del PIN'),
('Pin FAIL', 'Ingreso incorrecto del PIN'),
('Locked 3 Fails', 'Bloqueo del casillero por 3 intentos fallidos de PIN'),
('Desbloqueo', 'Desbloqueo del casillero de forma remota por el administrador'),
('Token OK', 'Ingreso correcto del Token de acceso'),
('Token FAIL', 'Ingreso incorrecto del Token de acceso'),
('Solicitud de Recuperación Pendiente', 'Creación de una nueva solicitud de casillero'),
('Solicitud de Recuperación Aprobada', 'Aprobación de una solicitud de casillero'),
('Solicitud de Recuperación Rechazada', 'Rechazo de una solicitud de casillero');

INSERT INTO Usuario (idUsuarioTipo, Nombre, Clave, Descripcion) VALUES
(1, 'admin', 'admin123', 'Administrador del sistema'),
(2, 'estudiante1', 'est123', 'Estudiante 1'),
(2, 'estudiante2', 'est123', 'Estudiante 2');

-- Casilleros
INSERT INTO Casillero
(idEstadoCasillero, idEstudiante, IntentosFallidos, Descripcion, Estado, FechaCreacion, FechaModificacion)
VALUES
(1, 2, 0, 'Casillero 1', 'Activo', datetime('now','localtime'), datetime('now','localtime')),
(2, 2, 0, 'Casillero 2', 'Activo', datetime('now','localtime'), datetime('now','localtime')),
(1, 3, 0, 'Casillero 3', 'Activo', datetime('now','localtime'), datetime('now','localtime')),
(1, NULL, 0, 'Casillero 4', 'Activo', datetime('now','localtime'), datetime('now','localtime')),
(2, NULL, 0, 'Casillero 5', 'Activo', datetime('now','localtime'), datetime('now','localtime'));

-- Credenciales (hashes de ejemplo)
INSERT INTO CredencialCasillero (idCasillero, pinHash) VALUES
(1, '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
(2, '6cb75f652a9b52798eb6cf2201057c73e0677d3f8cb1d5e4a3d6b7a3e5f5c1e0'),
(3, '2c1743a391305fbf367df8e4f069f9f9b828b5c6f5d5c1e4b8e4b5c6f5d5c1e4'),
(4, '3c363836cf4e16666669a25da280a1865c2d2874f5d5c1e4b8e4b5c6f5d5c1e4'),
(5, '098f6bcd4621d373cade4e832627b4f6b828b5c6f5d5c1e4b8e4b5c6f5d5c1e4');

-- Solicitudes (necesarias para Tokenacceso)
INSERT INTO Solicitud (idCasillero, idAdmin) VALUES
(1, 1),
(2, 1);

-- Tokens (depende de Solicitud)
INSERT INTO Tokenacceso (idSolicitud, idCasillero, TokenHash, Estado) VALUES
(1, 1, 'd8578edf8458ce06fbc5bb76a58c5ca4', 'Activo'),
(2, 2, '5f4dcc3b5aa765d61d8327deb882cf99', 'Activo');

-- Eventos (ya con idCasillero)
INSERT INTO RegistroEvento (idTipoEvento, idUsuario, idCasillero) VALUES
(1, 2, 1), -- Pin OK por estudiante1 en casillero 1
(2, 2, 1), -- Pin FAIL por estudiante1 en casillero 1
(4, 1, 1), -- Desbloqueo por admin en casillero 1
(5, 2, 2), -- Token OK por estudiante1 en casillero 2
(2, 3, 3); -- Pin FAIL por estudiante2 en casillero 3

-- =========================
--           VIEWS
-- =========================

DROP VIEW IF EXISTS vw_MisCasilleros_Estudiante;
DROP VIEW IF EXISTS vw_CasilleroDashboard_Estudiante;
DROP VIEW IF EXISTS vw_CasilleroDashboard_Admin;


-- 1) Admin: ve TODO (incluye pinHash)
DROP VIEW IF EXISTS vw_CasilleroDashboard_Admin;

CREATE VIEW vw_CasilleroDashboard_Admin AS
SELECT
    c.idCasillero,

    c.Descripcion                         AS CasilleroDescripcion,
    c.Estado                              AS CasilleroEstado,
    c.FechaCreacion                       AS CasilleroFechaCreacion,
    c.FechaModificacion                   AS CasilleroFechaModificacion,

    ec.Nombre                             AS EstadoCasilleroNombre,

    cc.pinHash                            AS CasilleroPinHash,

    te.Nombre                             AS TipoEventoRegistrado,
    re.FechaCreacion                      AS EventoFechaCreacion,
    re.FechaModificacion                  AS EventoFechaModificacion

FROM Casillero c
JOIN EstadoCasillero ec
    ON ec.idEstadoCasillero = c.idEstadoCasillero
LEFT JOIN CredencialCasillero cc
    ON cc.idCasillero = c.idCasillero
LEFT JOIN RegistroEvento re
    ON re.idRegistroEvento = (
        SELECT re2.idRegistroEvento
        FROM RegistroEvento re2
        WHERE re2.idCasillero = c.idCasillero
        ORDER BY re2.FechaModificacion DESC, re2.idRegistroEvento DESC
        LIMIT 1
    )
LEFT JOIN TipoEvento te
    ON te.idTipoEvento = re.idTipoEvento;

-- 2) Estudiante: NO ve pinHash (solo indicador + preview)
DROP VIEW IF EXISTS vw_CasilleroDashboard_Estudiante;

CREATE VIEW vw_CasilleroDashboard_Estudiante AS
SELECT
    c.idCasillero,

    c.Descripcion                         AS CasilleroDescripcion,
    c.Estado                              AS CasilleroEstado,
    c.FechaCreacion                       AS CasilleroFechaCreacion,
    c.FechaModificacion                   AS CasilleroFechaModificacion,

    ec.Nombre                             AS EstadoCasilleroNombre,

    CASE WHEN cc.pinHash IS NULL THEN 0 ELSE 1 END AS TieneCredencial,
    CASE WHEN cc.pinHash IS NULL THEN NULL ELSE substr(cc.pinHash, 1, 6) || '...' END AS CredencialPreview,

    te.Nombre                             AS TipoEventoRegistrado,
    re.FechaCreacion                      AS EventoFechaCreacion,
    re.FechaModificacion                  AS EventoFechaModificacion

FROM Casillero c
JOIN EstadoCasillero ec
    ON ec.idEstadoCasillero = c.idEstadoCasillero
LEFT JOIN CredencialCasillero cc
    ON cc.idCasillero = c.idCasillero
LEFT JOIN RegistroEvento re
    ON re.idRegistroEvento = (
        SELECT re2.idRegistroEvento
        FROM RegistroEvento re2
        WHERE re2.idCasillero = c.idCasillero
        ORDER BY re2.FechaModificacion DESC, re2.idRegistroEvento DESC
        LIMIT 1
    )
LEFT JOIN TipoEvento te
    ON te.idTipoEvento = re.idTipoEvento;

-- 3) Estudiante: SOLO mis casilleros (filtra por idEstudiante)
-- Nota: un VIEW no acepta parámetro, por eso creamos una vista basada en idEstudiante,
-- y luego tú filtras con WHERE idEstudiante = X al consultar.
DROP VIEW IF EXISTS vw_MisCasilleros_Estudiante;

CREATE VIEW vw_MisCasilleros_Estudiante AS
SELECT
    c.idCasillero,
    c.idEstudiante,

    c.Descripcion                         AS CasilleroDescripcion,
    c.Estado                              AS CasilleroEstado,
    c.FechaCreacion                       AS CasilleroFechaCreacion,
    c.FechaModificacion                   AS CasilleroFechaModificacion,

    ec.Nombre                             AS EstadoCasilleroNombre,

    CASE WHEN cc.pinHash IS NULL THEN 0 ELSE 1 END AS TieneCredencial,
    CASE WHEN cc.pinHash IS NULL THEN NULL ELSE substr(cc.pinHash, 1, 6) || '...' END AS CredencialPreview,

    te.Nombre                             AS TipoEventoRegistrado,
    re.FechaCreacion                      AS EventoFechaCreacion,
    re.FechaModificacion                  AS EventoFechaModificacion

FROM Casillero c
JOIN EstadoCasillero ec
    ON ec.idEstadoCasillero = c.idEstadoCasillero
LEFT JOIN CredencialCasillero cc
    ON cc.idCasillero = c.idCasillero
LEFT JOIN RegistroEvento re
    ON re.idRegistroEvento = (
        SELECT re2.idRegistroEvento
        FROM RegistroEvento re2
        WHERE re2.idCasillero = c.idCasillero
        ORDER BY re2.FechaModificacion DESC, re2.idRegistroEvento DESC
        LIMIT 1
    )
LEFT JOIN TipoEvento te
    ON te.idTipoEvento = re.idTipoEvento;

-- =========================
--        PRUEBAS
-- =========================

-- Admin ve todo
SELECT * FROM vw_CasilleroDashboard_Admin;

-- Estudiante ve todo (sin pinHash)
SELECT * FROM vw_CasilleroDashboard_Estudiante;

-- "Mis casilleros" del estudiante 1 (idUsuario=2)
SELECT * FROM vw_MisCasilleros_Estudiante WHERE idEstudiante = 2;

-- "Mis casilleros" del estudiante 2 (idUsuario=3)
SELECT * FROM vw_MisCasilleros_Estudiante WHERE idEstudiante = 3;

