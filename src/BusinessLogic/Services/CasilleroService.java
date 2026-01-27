// =========================================================
// BusinessLogic/Services/CasilleroService.java
// =========================================================
package BusinessLogic.Services;

import DataAccess.DAOs.CasilleroDAO;
import DataAccess.DAOs.CredencialCasilleroDAO;
import DataAccess.DAOs.RegistroEventoDAO;
import DataAccess.DAOs.SolicitudDAO;
import DataAccess.DAOs.TipoEventoDAO;
import DataAccess.DAOs.TokenAccesoDAO;
import DataAccess.DTOs.CasilleroDTO;
import DataAccess.DTOs.CredencialCasilleroDTO;
import DataAccess.DTOs.SolicitudDTO;
import DataAccess.DTOs.TokenAccesoDTO;
import Infrastructure.AppException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class CasilleroService {

    private final CasilleroDAO casilleroDAO;
    private final CredencialCasilleroDAO credencialDAO;
    private final TipoEventoDAO tipoEventoDAO;
    private final RegistroEventoDAO eventoDAO;
    private final SolicitudDAO solicitudDAO;
    private final TokenAccesoDAO tokenDAO;

    // ===== Catálogos (según tu BD) =====
    private static final int ESTADO_READY  = 1;
    private static final int ESTADO_LOCKED = 2;

    private static final int SOL_PENDIENTE = 1;

    // Admin por defecto (mientras no tengas login admin real conectado a esto)
    private static final int ADMIN_DEFAULT = 1;

    // ===== Nombres exactos de TipoEvento (según tu INSERT) =====
    private static final String EV_PIN_OK        = "Pin OK";
    private static final String EV_PIN_FAIL      = "Pin FAIL";
    private static final String EV_LOCKED_3FAILS = "Locked 3 Fails";
    private static final String EV_SOL_PEND      = "Solicitud de Recuperación Pendiente";

    private static final String EV_TOKEN_OK      = "Token OK";
    private static final String EV_TOKEN_FAIL    = "Token FAIL";
    private static final String EV_DESBLOQUEO    = "Desbloqueo";

    public CasilleroService() throws AppException {
        this.casilleroDAO = new CasilleroDAO();
        this.credencialDAO = new CredencialCasilleroDAO();
        this.tipoEventoDAO = new TipoEventoDAO();
        this.eventoDAO = new RegistroEventoDAO();
        this.solicitudDAO = new SolicitudDAO();
        this.tokenDAO = new TokenAccesoDAO();
    }

    /**
     * Valida PIN del casillero:
     * - OK  -> resetea intentos, evento Pin OK
     * - FAIL-> incrementa intentos, evento Pin FAIL
     * - al 3er FAIL -> bloquea, evento Locked 3 Fails, crea Solicitud Pendiente (si no existe) + evento Pendiente
     */
    public ResultadoValidacionPin validarPin(int idCasillero, int idUsuario, String pinPlano) throws AppException {

        CasilleroDTO cas = casilleroDAO.obtenerPorId(idCasillero);
        if (cas == null) throw new AppException("Casillero no existe", null, getClass(), "validarPin");

        // Si ya está bloqueado: asegurar solicitud pendiente y devolver BLOQUEADO
        if (cas.getIdEstadoCasillero() != null && cas.getIdEstadoCasillero() == ESTADO_LOCKED) {
            asegurarSolicitudPendienteConEvento(idCasillero, ADMIN_DEFAULT);
            return ResultadoValidacionPin.BLOQUEADO;
        }

        CredencialCasilleroDTO cred = credencialDAO.getByCasillero(idCasillero);
        if (cred == null || cred.getPinHash() == null) {
            throw new AppException("Casillero sin credencial", null, getClass(), "validarPin");
        }

        String hashIngresado = sha256(pinPlano);
        boolean ok = hashIngresado.equalsIgnoreCase(cred.getPinHash());

        if (ok) {
            casilleroDAO.resetIntentos(idCasillero);
            eventoDAO.crearEvento(tipoEventoIdOrThrow(EV_PIN_OK), idUsuario, idCasillero);
            return ResultadoValidacionPin.OK;
        }

        // FAIL
        casilleroDAO.incrementarIntentos(idCasillero);
        eventoDAO.crearEvento(tipoEventoIdOrThrow(EV_PIN_FAIL), idUsuario, idCasillero);

        CasilleroDTO cas2 = casilleroDAO.obtenerPorId(idCasillero);
        int intentos = (cas2 != null && cas2.getIntentosFallidos() != null) ? cas2.getIntentosFallidos() : 0;

        if (intentos >= 3) {
            // Bloquear
            casilleroDAO.actualizarEstado(idCasillero, ESTADO_LOCKED);
            eventoDAO.crearEvento(tipoEventoIdOrThrow(EV_LOCKED_3FAILS), idUsuario, idCasillero);

            // Crear solicitud pendiente (si no existe) + evento pendiente
            asegurarSolicitudPendienteConEvento(idCasillero, ADMIN_DEFAULT);

            return ResultadoValidacionPin.BLOQUEADO;
        }

        return ResultadoValidacionPin.FAIL;
    }

        /**
     * Valida TOKEN para desbloqueo (delegado a TokenService).
     * Mantiene la firma por compatibilidad con la UI.
     *
     * @return true si el token es válido y se procesó el desbloqueo, false si no.
     */
    public boolean validarToken(int idCasillero, int idUsuario, String tokenIngresado) throws AppException {

        TokenService tokenService = new TokenService();
        TokenService.ResultadoValidacionToken r = tokenService.validarToken(idCasillero, idUsuario, tokenIngresado);

        if (r == TokenService.ResultadoValidacionToken.OK) return true;

        // Si no hay token activo, mantenemos el comportamiento anterior: registrar FAIL
        if (r == TokenService.ResultadoValidacionToken.NO_TOKEN) {
            eventoDAO.crearEvento(tipoEventoIdOrThrow(EV_TOKEN_FAIL), idUsuario, idCasillero);
        }

        return false;
    }

    /**
     * Crea una solicitud PENDIENTE si no existe otra PENDIENTE activa para ese casillero.
     * Si la crea, registra también el evento "Solicitud de Recuperación Pendiente".
     */
    private void asegurarSolicitudPendienteConEvento(int idCasillero, int idAdmin) throws AppException {

        List<SolicitudDTO> list = solicitudDAO.listarPorCasillero(idCasillero);

        boolean yaExistePendiente = false;
        if (list != null) {
            for (SolicitudDTO s : list) {
                if (s == null) continue;
                if (!"A".equalsIgnoreCase(s.getEstado())) continue;
                if (s.getIdEstadoSolicitud() != null && s.getIdEstadoSolicitud() == SOL_PENDIENTE) {
                    yaExistePendiente = true;
                    break;
                }
            }
        }

        if (!yaExistePendiente) {
            Integer idSol = solicitudDAO.crearSolicitud(idCasillero, idAdmin, SOL_PENDIENTE);
            if (idSol != null) {
                eventoDAO.crearEvento(tipoEventoIdOrThrow(EV_SOL_PEND), idAdmin, idCasillero);
            }
        }
    }

    // ===== helpers =====

    private int tipoEventoIdOrThrow(String nombre) throws AppException {
        Integer id = tipoEventoDAO.findIdByName(nombre);
        if (id == null) {
            throw new AppException("No existe TipoEvento: " + nombre, null, getClass(), "tipoEventoIdOrThrow");
        }
        return id;
    }

    private static String sha256(String input) throws AppException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new AppException("Error SHA-256", e, CasilleroService.class, "sha256");
        }
    }

    public enum ResultadoValidacionPin {
        OK, FAIL, BLOQUEADO
    }
}
