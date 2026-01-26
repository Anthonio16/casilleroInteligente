// =========================================================
// Dentro de BusinessLogic/Services/CasilleroService.java
// Agrega estos imports arriba:
// import DataAccess.DAOs.TokenAccesoDAO;
// import DataAccess.DTOs.TokenAccesoDTO;
// =========================================================

// En tu clase CasilleroService agrega el DAO:
private final TokenAccesoDAO tokenDAO;

// En el constructor:
this.tokenDAO = new TokenAccesoDAO();

// Constantes eventos (exactas a tu INSERT):
private static final String EV_TOKEN_OK   = "Token OK";
private static final String EV_TOKEN_FAIL = "Token FAIL";
private static final String EV_DESBLOQ    = "Desbloqueo";

// Método nuevo:
public boolean validarToken(int idCasillero, int idUsuario, String tokenPlano) throws AppException {

    // 1) Debe existir casillero
    CasilleroDTO dto = casilleroDAO.obtenerPorId(idCasillero);
    if (dto == null) throw new AppException("Casillero no existe", null, getClass(), "validarToken");

    // 2) Token activo del casillero
    TokenAccesoDTO token = tokenDAO.obtenerActivoPorCasillero(idCasillero);
    if (token == null || token.getTokenHash() == null) {
        // registra FAIL (estricto)
        registroEventoDAO.crearEvento(tipoEventoIdOrThrow(EV_TOKEN_FAIL), idUsuario, idCasillero);
        return false;
    }

    boolean ok = token.getTokenHash().equals(tokenPlano);

    if (!ok) {
        registroEventoDAO.crearEvento(tipoEventoIdOrThrow(EV_TOKEN_FAIL), idUsuario, idCasillero);
        return false;
    }

    // 3) OK -> desactivar token (1 solo uso recomendado)
    tokenDAO.desactivarToken(token.getIdTokenacceso());

    // 4) dejar casillero READY=1
    casilleroDAO.actualizarEstado(idCasillero, ESTADO_READY);

    // opcional: reset intentos
    casilleroDAO.resetIntentos(idCasillero);

    // 5) eventos estrictos
    registroEventoDAO.crearEvento(tipoEventoIdOrThrow(EV_TOKEN_OK), idUsuario, idCasillero);
    registroEventoDAO.crearEvento(tipoEventoIdOrThrow(EV_DESBLOQ), idUsuario, idCasillero);

    return true;
}

// helper estricto (si no lo tienes ya)
private int tipoEventoIdOrThrow(String nombre) throws AppException {
    Integer id = tipoEventoDAO.findIdByName(nombre);
    if (id == null) {
        throw new AppException("No existe TipoEvento: " + nombre, null, getClass(), "tipoEventoIdOrThrow");
    }
    return id;
}

