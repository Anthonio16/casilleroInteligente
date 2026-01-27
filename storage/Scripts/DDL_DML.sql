--database: F:\casilleroInteligente_con_cambios_15_16_C\casilleroInteligente\casilleroInteligente\storage\Databases\casilleroInteligente.sqlite
PRAGMA foreign_keys = ON;

-- =========================
--        DROPS
-- =========================
DROP VIEW IF EXISTS vw_MisCasilleros_Estudiante;
DROP VIEW IF EXISTS vw_CasilleroDashboard_Estudiante;
DROP VIEW IF EXISTS vw_CasilleroDashboard_Admin;
DROP TABLE IF EXISTS RegistroEvento;
DROP TABLE IF EXISTS Tokenacceso;
DROP TABLE IF EXISTS Solicitud;
DROP TABLE IF EXISTS EstadoSolicitud;
DROP TABLE IF EXISTS CredencialCasillero;
DROP TABLE IF EXISTS Casillero;
DROP TABLE IF EXISTS EstadoCasillero;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS UsuarioTipo;
DROP TABLE IF EXISTS TipoEvento;

-- =========================
--        TABLAS
-- =========================

CREATE TABLE TipoEvento (
    idTipoEvento        INTEGER PRIMARY KEY AUTOINCREMENT,
    Nombre              VARCHAR(50) NOT NULL UNIQUE,
    Descripcion         VARCHAR(100) NULL,
    Estado              VARCHAR(1)  NOT NULL DEFAULT 'A',
    FechaCreacion       DATETIME    NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME    NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE UsuarioTipo (
    idUsuarioTipo       INTEGER PRIMARY KEY AUTOINCREMENT,
    Nombre              VARCHAR(20) NOT NULL UNIQUE,
    Descripcion         VARCHAR(100) NULL,
    Estado              VARCHAR(1)  NOT NULL DEFAULT 'A',
    FechaCreacion       DATETIME    NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME    NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE Usuario (
    idUsuario           INTEGER PRIMARY KEY AUTOINCREMENT,
    idUsuarioTipo       INTEGER NOT NULL REFERENCES UsuarioTipo(idUsuarioTipo),
    Nombre              VARCHAR(50) NOT NULL,
    Clave               VARCHAR(80) NOT NULL,
    Descripcion         VARCHAR(100) NULL,
    Estado              VARCHAR(1)  NOT NULL DEFAULT 'A',
    FechaCreacion       DATETIME    NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME    NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE EstadoCasillero (
    idEstadoCasillero   INTEGER PRIMARY KEY AUTOINCREMENT,
    Nombre              VARCHAR(20) NOT NULL UNIQUE,
    Descripcion         VARCHAR(100) NULL,
    Estado              VARCHAR(1)  NOT NULL DEFAULT 'A',
    FechaCreacion       DATETIME    NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME    NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE Casillero (
    idCasillero         INTEGER PRIMARY KEY AUTOINCREMENT,
    idEstadoCasillero   INTEGER NOT NULL REFERENCES EstadoCasillero(idEstadoCasillero),
    idEstudiante        INTEGER NULL REFERENCES Usuario(idUsuario),
    IntentosFallidos    INTEGER NOT NULL DEFAULT 0,
    Descripcion         VARCHAR(100) NULL,
    Estado              VARCHAR(1)  NOT NULL DEFAULT 'A',
    FechaCreacion       DATETIME    NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME    NOT NULL DEFAULT (datetime('now','localtime'))
);

-- 1 credencial por casillero
CREATE TABLE CredencialCasillero (
    idCredencialCasillero INTEGER PRIMARY KEY AUTOINCREMENT,
    idCasillero           INTEGER NOT NULL UNIQUE REFERENCES Casillero(idCasillero),
    pinHash               TEXT NOT NULL,
    Estado                VARCHAR(1) NOT NULL DEFAULT 'A',
    FechaCreacion         DATETIME   NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion     DATETIME   NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE EstadoSolicitud (
    idEstadoSolicitud   INTEGER PRIMARY KEY AUTOINCREMENT,
    Nombre              VARCHAR(20) NOT NULL UNIQUE,
    Descripcion         VARCHAR(100) NULL,
    Estado              VARCHAR(1)  NOT NULL DEFAULT 'A',
    FechaCreacion       DATETIME    NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME    NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE Solicitud (
    idSolicitud              INTEGER    PRIMARY KEY AUTOINCREMENT,
    idCasillero              INTEGER    NOT NULL REFERENCES Casillero(idCasillero),
    idEstudianteSolicitante  INTEGER    NOT NULL REFERENCES Usuario(idUsuario),
    idAdmin                  INTEGER    NULL REFERENCES Usuario(idUsuario),
    idEstadoSolicitud        INTEGER    NOT NULL DEFAULT 1 REFERENCES EstadoSolicitud(idEstadoSolicitud),
    Estado                   VARCHAR(1) NOT NULL DEFAULT 'A',
    FechaCreacion            DATETIME   NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion        DATETIME   NOT NULL DEFAULT (datetime('now','localtime'))
);


CREATE TABLE Tokenacceso (
    idTokenacceso       INTEGER PRIMARY KEY AUTOINCREMENT,
    idSolicitud         INTEGER NOT NULL REFERENCES Solicitud(idSolicitud),
    idCasillero         INTEGER NOT NULL REFERENCES Casillero(idCasillero),
    TokenHash           TEXT NOT NULL,
    Estado              VARCHAR(1)  NOT NULL DEFAULT 'A',
    FechaCreacion       DATETIME    NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME    NOT NULL DEFAULT (datetime('now','localtime')),
    FechaExpiracion     DATETIME    NULL
);

CREATE TABLE RegistroEvento (
    idRegistroEvento    INTEGER PRIMARY KEY AUTOINCREMENT,
    idTipoEvento        INTEGER NOT NULL REFERENCES TipoEvento(idTipoEvento),
    idUsuario           INTEGER NOT NULL REFERENCES Usuario(idUsuario),
    idCasillero         INTEGER NOT NULL REFERENCES Casillero(idCasillero),
    Estado              VARCHAR(1)  NOT NULL DEFAULT 'A',
    FechaCreacion       DATETIME    NOT NULL DEFAULT (datetime('now','localtime')),
    FechaModificacion   DATETIME    NOT NULL DEFAULT (datetime('now','localtime'))
);

-- =========================
--        INSERTS
-- =========================

INSERT INTO UsuarioTipo (Nombre, Descripcion) VALUES
('Admin', 'Administrador del sistema'),
('Estudiante', 'Usuario estudiante');

INSERT INTO Usuario (idUsuarioTipo, Nombre, Clave, Descripcion) VALUES
(1, 'patmic',       'patmic123', 'Administrador del sistema'),
(2, 'estudiante1', 'est123',   'Estudiante 1'),
(2, 'estudiante2', 'est123',   'Estudiante 2');

INSERT INTO EstadoCasillero (Nombre, Descripcion) VALUES
('Ready to Unlock', 'Casillero disponible para asignación'),
('Locked',          'Casillero actualmente asignado a un usuario');

INSERT INTO EstadoSolicitud (Nombre, Descripcion) VALUES
('Pendiente', 'Solicitud en espera de revisión'),
('Aprobada',  'Solicitud aprobada por el administrador'),
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

-- Casilleros
INSERT INTO Casillero (idEstadoCasillero, idEstudiante, IntentosFallidos, Descripcion, Estado)
VALUES
(2, 2, 0, 'Casillero 1', 'A'),   -- Locked: asignado a estudiante 2
(1, NULL, 0, 'Casillero 2', 'A'),
(2, 3, 0, 'Casillero 3', 'A'),   -- Locked: asignado a estudiante 3
(1, NULL, 0, 'Casillero 4', 'A'),
(1, NULL, 0, 'Casillero 5', 'A');

-- Credenciales
INSERT INTO CredencialCasillero (idCasillero, pinHash, Estado) VALUES
(1, '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'A'),
(2, '6cb75f652a9b52798eb6cf2201057c73e0677d3f8cb1d5e4a3d6b7a3e5f5c1e0', 'A'),
(3, '2c1743a391305fbf367df8e4f069f9f9b828b5c6f5d5c1e4b8e4b5c6f5d5c1e4', 'A'),
(4, '3c363836cf4e16666669a25da280a1865c2d2874f5d5c1e4b8e4b5c6f5d5c1e4', 'A'),
(5, '098f6bcd4621d373cade4e832627b4f6b828b5c6f5d5c1e4b8e4b5c6f5d5c1e4', 'A');

-- Solicitudes (Pendiente=1)
INSERT INTO Solicitud (idCasillero, idEstudianteSolicitante, idAdmin, idEstadoSolicitud, Estado) VALUES
(1, 2, NULL, 1, 'A'),
(3, 3, NULL, 1, 'A');


-- Tokens (Activos)
INSERT INTO Tokenacceso (idSolicitud, idCasillero, TokenHash, Estado, FechaExpiracion) VALUES
(1, 1, 'd8578edf8458ce06fbc5bb76a58c5ca4', 'A', datetime('now','localtime','+15 minutes')),
(2, 2, '5f4dcc3b5aa765d61d8327deb882cf99', 'A', datetime('now','localtime','+15 minutes'));

-- Eventos
INSERT INTO RegistroEvento (idTipoEvento, idUsuario, idCasillero, Estado) VALUES
(1, 2, 1, 'A'),
(2, 2, 1, 'A'),
(4, 1, 1, 'A'),
(5, 2, 2, 'A'),
(2, 3, 3, 'A');

-- =========================
--           VIEWS
-- =========================

-- 1) Admin: ve TODO (incluye pinHash)
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
    ON cc.idCasillero = c.idCasillero AND cc.Estado = 'A'
LEFT JOIN RegistroEvento re
    ON re.idRegistroEvento = (
        SELECT re2.idRegistroEvento
        FROM RegistroEvento re2
        WHERE re2.idCasillero = c.idCasillero AND re2.Estado = 'A'
        ORDER BY re2.FechaModificacion DESC, re2.idRegistroEvento DESC
        LIMIT 1
    )
LEFT JOIN TipoEvento te
    ON te.idTipoEvento = re.idTipoEvento
WHERE c.Estado = 'A';

-- 2) Estudiante: NO ve pinHash
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
    ON cc.idCasillero = c.idCasillero AND cc.Estado = 'A'
LEFT JOIN RegistroEvento re
    ON re.idRegistroEvento = (
        SELECT re2.idRegistroEvento
        FROM RegistroEvento re2
        WHERE re2.idCasillero = c.idCasillero AND re2.Estado = 'A'
        ORDER BY re2.FechaModificacion DESC, re2.idRegistroEvento DESC
        LIMIT 1
    )
LEFT JOIN TipoEvento te
    ON te.idTipoEvento = re.idTipoEvento
WHERE c.Estado = 'A';

-- 3) Estudiante: solo mis casilleros (filtrar en SELECT final)
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
    ON cc.idCasillero = c.idCasillero AND cc.Estado = 'A'
LEFT JOIN RegistroEvento re
    ON re.idRegistroEvento = (
        SELECT re2.idRegistroEvento
        FROM RegistroEvento re2
        WHERE re2.idCasillero = c.idCasillero AND re2.Estado = 'A'
        ORDER BY re2.FechaModificacion DESC, re2.idRegistroEvento DESC
        LIMIT 1
    )
LEFT JOIN TipoEvento te
    ON te.idTipoEvento = re.idTipoEvento
WHERE c.Estado = 'A';

-- =========================
--        PRUEBAS
-- =========================
SELECT * FROM vw_CasilleroDashboard_Admin;
SELECT * FROM vw_CasilleroDashboard_Estudiante;
SELECT * FROM vw_MisCasilleros_Estudiante WHERE idEstudiante = 2;
SELECT * FROM vw_MisCasilleros_Estudiante WHERE idEstudiante = 3;

CREATE UNIQUE INDEX IF NOT EXISTS ux_Casillero_idEstudiante
ON Casillero(idEstudiante)
WHERE idEstudiante IS NOT NULL;

DROP VIEW IF EXISTS vw_Auditoria_Admin;
DROP VIEW IF EXISTS vw_Auditoria_Estudiante;

-- Auditoría completa (admin)
CREATE VIEW vw_Auditoria_Admin AS
SELECT
  re.idRegistroEvento                          AS idRegistroEvento,
  re.FechaCreacion                             AS Fecha,
  re.idCasillero                               AS idCasillero,
  c.Descripcion                                AS CasilleroDescripcion,
  ec.Nombre                                    AS EstadoCasillero,
  c.idEstudiante                               AS idEstudiante,

  re.idUsuario                                 AS idUsuario,
  u.Nombre                                     AS UsuarioNombre,
  ut.Nombre                                    AS UsuarioRol,

  te.Nombre                                    AS EventoNombre,
  CASE
    WHEN te.Nombre LIKE '%OK%' THEN 'OK'
    WHEN te.Nombre LIKE '%FAIL%' THEN 'FAIL'
    WHEN te.Nombre LIKE '%Locked%' THEN 'BLOQUEO'
    WHEN te.Nombre LIKE '%Aprobada%' THEN 'OK'
    WHEN te.Nombre LIKE '%Rechazada%' THEN 'FAIL'
    ELSE 'INFO'
  END                                          AS Resultado

FROM RegistroEvento re
JOIN TipoEvento te      ON te.idTipoEvento = re.idTipoEvento
JOIN Usuario u          ON u.idUsuario = re.idUsuario
JOIN UsuarioTipo ut     ON ut.idUsuarioTipo = u.idUsuarioTipo
JOIN Casillero c        ON c.idCasillero = re.idCasillero
JOIN EstadoCasillero ec ON ec.idEstadoCasillero = c.idEstadoCasillero
WHERE re.Estado = 'A';

-- Auditoría para estudiante: misma data, pero se filtra por idEstudiante desde el código
CREATE VIEW vw_Auditoria_Estudiante AS
SELECT * FROM vw_Auditoria_Admin;

