package BusinessLogic.Services;

import DataAccess.DAOs.CasilleroDAO;
import DataAccess.DAOs.RegistroEventoDAO;
import DataAccess.DAOs.TipoEventoDAO;
import DataAccess.DAOs.TokenAccesoDAO;
import DataAccess.DTOs.CasilleroDTO;
import DataAccess.DTOs.TokenAccesoDTO;
import Infrastructure.AppException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class TokenService {

    private final TokenAccesoDAO tokenDAO;
    private final CasilleroDAO casilleroDAO;
    private final TipoEventoDAO tipoEventoDAO;
    private final RegistroEventoDAO eventoDAO;

    // IDs según tu catálogo EstadoCasillero
    private static final int ESTADO_READY  = 1;
    private static final int ESTADO_LOCKED = 2;

    public TokenService() throws AppException {
        this.tokenDAO = new TokenAccesoDAO();
        this.casilleroDAO = new CasilleroDAO();
        this.tipoEventoDAO = new TipoEventoDAO();
        this.eventoDAO = new RegistroEventoDAO();
    }

    /**
     * Valida Token del casillero:
     * - Si NO hay token activo/no expirado -> NO_TOKEN
     * - Si token OK -> desbloquea casillero (Ready), resetea intentos, registra "Token OK", desactiva token
     * - Si token FAIL -> registra "Token FAIL"
     *
     * Nota: por compatibilidad compara:
     *  1) tokenIngresado == TokenHash (directo)
     *  2) sha256(tokenIngresado) == TokenHash (por si guardas hash real)
     */
    public ResultadoValidacionToken validarToken(int idCasillero, int idUsuario, String tokenIngresado) throws AppException {

        CasilleroDTO cas = casilleroDAO.obtenerPorId(idCasillero);
        if (cas == null) throw new AppException("Casillero no existe", null, getClass(), "validarToken");

        TokenAccesoDTO token = tokenDAO.obtenerActivoPorCasillero(idCasillero);
        if (token == null) {
            return ResultadoValidacionToken.NO_TOKEN;
        }

        String tokenDb = token.getTokenHash();
        boolean ok = tokenDb != null && (
                tokenDb.equalsIgnoreCase(tokenIngresado) ||
                tokenDb.equalsIgnoreCase(sha256(tokenIngresado))
        );

        if (ok) {
            // Desbloquear casillero y reset intentos
            casilleroDAO.resetIntentos(idCasillero);
            casilleroDAO.actualizarEstado(idCasillero, ESTADO_READY);

            // Evento Token OK
            Integer idTipoOk = tipoEventoDAO.findIdByName("Token OK");
            if (idTipoOk != null) eventoDAO.crearEvento(idTipoOk, idUsuario, idCasillero);

            // Desactiva token usado
            tokenDAO.desactivarTokensPorCasillero(token.getIdTokenacceso());

            return ResultadoValidacionToken.OK;
        }

        // Evento Token FAIL
        Integer idTipoFail = tipoEventoDAO.findIdByName("Token FAIL");
        if (idTipoFail != null) eventoDAO.crearEvento(idTipoFail, idUsuario, idCasillero);

        return ResultadoValidacionToken.FAIL;
    }

    // ===== helpers =====
    private static String sha256(String input) throws AppException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new AppException("Error SHA-256", e, TokenService.class, "sha256");
        }
    }

    public enum ResultadoValidacionToken {
        OK, FAIL, NO_TOKEN
    }
}

