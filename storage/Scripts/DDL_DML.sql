-- database: casilleroInteligente\storage\Databases\casilleroInteligente.sqlite
DROP TABLE IF EXISTS Tokenacceso;
DROP TABLE IF EXISTS Solicitud;
DROP TABLE IF EXISTS EstadoSolicitud;
DROP TABLE IF EXISTS Casillero;
DROP TABLE IF EXISTS EstadoCasillero;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS UsuarioTipo;
DROP TABLE IF EXISTS CredencialCasillero;
DROP TABLE IF EXISTS RegistroEvento;
DROP TABLE IF EXISTS TipoEvento;

CREATE TABLE TipoEvento (
    idTipoEvento INTEGER PRIMARY KEY AUTOINCREMENT,
    ,Nombre VARCHAR(50) NOT NULL UNIQUE
    ,Descripcion VARCHAR(100) NULL
    ,FechaCreacion DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
    ,FechaModificacion DATETIME NOT NULL DEFAULT (datetime('now','localtime'))  
);

CREATE TABLE RegistroEvento (
    idRegistroEvento    INTEGER PRIMARY KEY AUTOINCREMENT
    ,idTipoEvento       INTEGER NOT NULL REFERENCES TipoEvento(idTipoEvento)
    ,idUsuario          INTEGER NOT NULL REFERENCES Usuario(idUsuario)
    ,FechaCreacion      DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
    ,FechaModificacion  DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE CredencialCasillero (
    idCredencialCasillero INTEGER PRIMARY KEY AUTOINCREMENT
    ,idCasillero INTEGER NOT NULL REFERENCES Casillero(idCasillero)
    ,pinHash TEXT NOT NULL
    ,salt TEXT NOT NULL
);

CREATE TABLE UsuarioTipo (
    idUsuarioTipo INTEGER PRIMARY KEY AUTOINCREMENT,
    ,Nombre VARCHAR(20) NOT NULL UNIQUE
    ,Descripcion VARCHAR(100) NULL
    ,Estado VARCHAR(10) NOT NULL DEFAULT 'Activo'
    ,FechaCreacion DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
    ,FechaModificacion DATETIME NOT NULL DEFAULT (datetime('now','localtime'))
);

CREATE TABLE Usuario (
    idUsuario INTEGER PRIMARY KEY AUTOINCREMENT,
    idUsuarioTipo INTEGER NOT NULL,
    nombre TEXT NOT NULL,
    apellido TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    contrasena TEXT NOT NULL,
    telefono TEXT,
    fechaRegistro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idUsuarioTipo) REFERENCES UsuarioTipo(idUsuarioTipo)
);

CREATE TABLE EstadoCasillero (
    idEstadoCasillero INTEGER PRIMARY KEY AUTOINCREMENT,
    descripcion TEXT NOT NULL
);

CREATE TABLE Casillero (
    idCasillero INTEGER PRIMARY KEY AUTOINCREMENT,
    idEstadoCasillero INTEGER NOT NULL,
    ubicacion TEXT NOT NULL,
    numeroCasillero TEXT NOT NULL UNIQUE,
    FOREIGN KEY (idEstadoCasillero) REFERENCES EstadoCasillero(idEstadoCasillero)
);

CREATE TABLE EstadoSolicitud (
    idEstadoSolicitud INTEGER PRIMARY KEY AUTOINCREMENT,
    descripcion TEXT NOT NULL
);

CREATE TABLE Solicitud (
    idSolicitud INTEGER PRIMARY KEY AUTOINCREMENT,
    idUsuario INTEGER NOT NULL,
    idCasillero INTEGER NOT NULL,
    idEstadoSolicitud INTEGER NOT NULL,
    fechaSolicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fechaResolucion TIMESTAMP,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario),
    FOREIGN KEY (idCasillero) REFERENCES Casillero(idCasillero),
    FOREIGN KEY (idEstadoSolicitud) REFERENCES EstadoSolicitud(idEstadoSolicitud)
);

CREATE TABLE Tokenacceso (
    idTokenacceso INTEGER PRIMARY KEY AUTOINCREMENT,
    idUsuario INTEGER NOT NULL,
    token TEXT NOT NULL UNIQUE,
    fechaExpiracion TIMESTAMP,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);