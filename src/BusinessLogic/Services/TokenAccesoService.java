package BusinessLogic.Services;

import DataAccess.DAOs.CasilleroDAO;
import DataAccess.DAOs.RegistroEventoDAO;
import DataAccess.DAOs.TipoEventoDAO;
import DataAccess.DAOs.TokenAccesoDAO;
import DataAccess.DTOs.TokenAccesoDTO;
import Infrastructure.AppException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class TokenAccesoService {

    private final TokenAccesoDAO tokenDAO;
    private final CasilleroDAO casilleroDAO;
    private final TipoEventoDAO tipoEventoDAO;
    private final RegistroEventoDAO eventoDAO;
    private static final int ESTADO_READY = 1;

    public TokenAccesoService() throws AppException {
        this.tokenDAO = new TokenAccesoDAO();
        this.casilleroDAO = new CasilleroDAO();
        this.tipoEventoDAO = new TipoEventoDAO();
        this.eventoDAO = new RegistroEventoDAO();
    }

    public ResultadoValidacionToken validarTokenYDesbloquear(int idCasillero, int idUsuario, String tokenPlano) throws AppException {

        TokenAccesoDTO token = tokenDAO.obtenerActivoPorCasillero(idCasillero);
        if (token == null) {
            return ResultadoValidacionToken.NO_TOKEN;
        }

        String hashIngresado = sha256(tokenPlano);
        boolean ok = hashIngresado.equalsIgnoreCase(token.getTokenHash());

        if (!ok) {
            Integer idTipoFail = tipoEventoDAO.findIdByName("Token FAIL");
            if (idTipoFail != null) eventoDAO.crearEvento(idTipoFail, idUsuario, idCasillero);
            return ResultadoValidacionToken.FAIL;
        }

        casilleroDAO.resetIntentos(idCasillero);
        casilleroDAO.actualizarEstado(idCasillero, ESTADO_READY);

        tokenDAO.desactivarTokensPorCasillero(idCasillero);

        Integer idTipoOk = tipoEventoDAO.findIdByName("Token OK");
        if (idTipoOk != null) eventoDAO.crearEvento(idTipoOk, idUsuario, idCasillero);

        return ResultadoValidacionToken.OK;
    }

    private static String sha256(String input) throws AppException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new AppException("Error SHA-256", e, TokenAccesoService.class, "sha256");
        }
    }

    public enum ResultadoValidacionToken {
        OK, FAIL, NO_TOKEN
    }
}
