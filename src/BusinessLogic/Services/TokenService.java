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

<<<<<<< HEAD
    // EstadoCasillero
    private static final int ESTADO_READY = 1;

    // TipoEvento.Nombre (exactos)
    private static final String EV_TOKEN_OK   = "Token OK";
    private static final String EV_TOKEN_FAIL = "Token FAIL";
    private static final String EV_DESBLOQ    = "Desbloqueo";
=======
    
    private static final int ESTADO_READY  = 1;
    private static final int ESTADO_LOCKED = 2;
>>>>>>> 8ec0def35403ec88f68bad7a0a88c9a8715082aa

    public TokenService() throws AppException {
        this.tokenDAO = new TokenAccesoDAO();
        this.casilleroDAO = new CasilleroDAO();
        this.tipoEventoDAO = new TipoEventoDAO();
        this.eventoDAO = new RegistroEventoDAO();
    }

    /**
     * - Si NO hay token activo/no expirado -> NO_TOKEN
     * - Si token OK -> READY=1, reset intentos, evento Token OK + Desbloqueo, desactiva token
     * - Si token FAIL -> evento Token FAIL
     */
    public ResultadoValidacionToken validarToken(int idCasillero, int idUsuario, String tokenIngresado) throws AppException {

        CasilleroDTO cas = casilleroDAO.obtenerPorId(idCasillero);
        if (cas == null) throw new AppException("Casillero no existe", null, getClass(), "validarToken");

        TokenAccesoDTO token = tokenDAO.obtenerActivoPorCasillero(idCasillero);
        if (token == null || token.getTokenHash() == null) {
            return ResultadoValidacionToken.NO_TOKEN;
        }

        if (tokenIngresado == null) tokenIngresado = "";

        String tokenDb = token.getTokenHash();

        boolean ok =
            tokenDb.equalsIgnoreCase(tokenIngresado) ||
            tokenDb.equalsIgnoreCase(sha256(tokenIngresado));

        if (ok) {
            // 1) reset intentos + READY
            casilleroDAO.resetIntentos(idCasillero);
            casilleroDAO.actualizarEstado(idCasillero, ESTADO_READY);

<<<<<<< HEAD
            // 2) eventos estrictos
            eventoDAO.crearEvento(tipoEventoIdOrThrow(EV_TOKEN_OK), idUsuario, idCasillero);
            eventoDAO.crearEvento(tipoEventoIdOrThrow(EV_DESBLOQ),  idUsuario, idCasillero);

            // 3) desactiva SOLO el token usado
            tokenDAO.desactivarToken(token.getIdTokenacceso());
=======
            //  OK
            Integer idTipoOk = tipoEventoDAO.findIdByName("Token OK");
            if (idTipoOk != null) eventoDAO.crearEvento(idTipoOk, idUsuario, idCasillero);

            // Desactiva token
            tokenDAO.desactivarTokensPorCasillero(token.getIdTokenacceso());
>>>>>>> 8ec0def35403ec88f68bad7a0a88c9a8715082aa

            return ResultadoValidacionToken.OK;
        }

<<<<<<< HEAD
        // FAIL
        eventoDAO.crearEvento(tipoEventoIdOrThrow(EV_TOKEN_FAIL), idUsuario, idCasillero);
        return ResultadoValidacionToken.FAIL;
    }

    private int tipoEventoIdOrThrow(String nombre) throws AppException {
        Integer id = tipoEventoDAO.findIdByName(nombre);
        if (id == null) throw new AppException("No existe TipoEvento: " + nombre, null, getClass(), "tipoEventoIdOrThrow");
        return id;
    }

=======
        //  FAIL
        Integer idTipoFail = tipoEventoDAO.findIdByName("Token FAIL");
        if (idTipoFail != null) eventoDAO.crearEvento(idTipoFail, idUsuario, idCasillero);

        return ResultadoValidacionToken.FAIL;
    }

    // ===== Helpers =====
>>>>>>> 8ec0def35403ec88f68bad7a0a88c9a8715082aa
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


