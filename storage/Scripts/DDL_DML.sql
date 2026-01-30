--database: F:\casilleroInteligente_con_cambios_15_16_C\casilleroInteligente\casilleroInteligente\storage\Databases\casilleroInteligente.sqlite
PRAGMA foreign_keys = ON;

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

INSERT INTO Casillero (idEstadoCasillero, idEstudiante, IntentosFallidos, Descripcion, Estado)
VALUES
(2, 2, 0, 'Casillero 1', 'A'),   -- Locked: asignado a estudiante 2
(1, NULL, 0, 'Casillero 2', 'A'),
(2, 3, 0, 'Casillero 3', 'A'),   -- Locked: asignado a estudiante 3
(1, NULL, 0, 'Casillero 4', 'A'),
(1, NULL, 0, 'Casillero 5', 'A');

-- Credenciales
-- PINs de ejemplo (en claro):
--   Casillero 1: 12341
--   Casillero 2: 23451
--   Casillero 3: 34561
--   Casillero 4: 45671
--   Casillero 5: 56781
-- (en BD se guarda SHA-256)
INSERT INTO CredencialCasillero (idCasillero, pinHash, Estado) VALUES
(1, '0f6370c4f94a173cc95f01b127638f8eebe33537ca21ed3c289c77c232f5c183', 'A'),
(2, '68e6056bca0bd76350ac00fd8e2668b92ee482585a347a9dc865c91810fa70c6', 'A'),
(3, '4fa2fd78506cb701702f8ee78621eb7b18711ca95cd65cc39183d3f4c18f8a4f', 'A'),
(4, 'f54bfbf7be392a56b0e6896f2e20409470b71cd5303740a0df247f6a156e57ce', 'A'),
(5, '9a7f3ec4f8e2676f4f42df2ebcc11afbf3b8067298d2f5533581b790f7323b0f', 'A');

INSERT INTO Solicitud (idCasillero, idEstudianteSolicitante, idAdmin, idEstadoSolicitud, Estado) VALUES
(1, 2, NULL, 1, 'A'),
(3, 3, NULL, 1, 'A');


INSERT INTO Tokenacceso (idSolicitud, idCasillero, TokenHash, Estado, FechaExpiracion) VALUES
(1, 1, 'd8578edf8458ce06fbc5bb76a58c5ca4', 'A', datetime('now','localtime','+15 minutes')),
(2, 2, '5f4dcc3b5aa765d61d8327deb882cf99', 'A', datetime('now','localtime','+15 minutes'));

INSERT INTO RegistroEvento (idTipoEvento, idUsuario, idCasillero, Estado) VALUES
(1, 2, 1, 'A'),
(2, 2, 1, 'A'),
(4, 1, 1, 'A'),
(5, 2, 2, 'A'),
(2, 3, 3, 'A');


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

CREATE VIEW vw_Auditoria_Estudiante AS
SELECT * FROM vw_Auditoria_Admin;

