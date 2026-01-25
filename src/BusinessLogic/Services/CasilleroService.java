package BusinessLogic.Services;

import DataAccess.DAOs.CasilleroDAO;
import DataAccess.DAOs.CredencialCasilleroDAO;
import DataAccess.DAOs.RegistroEventoDAO;
import DataAccess.DAOs.SolicitudDAO;
import DataAccess.DAOs.TipoEventoDAO;
import DataAccess.DTOs.CasilleroDTO;
import DataAccess.DTOs.CredencialCasilleroDTO;
import DataAccess.DTOs.SolicitudDTO;
import Infrastructure.AppException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class CasilleroService {

    private final CasilleroDAO casilleroDAO;
    private final CredencialCasilleroDAO credencialDAO;
    private final TipoEventoDAO tipoEventoDAO;
    private final RegistroEventoDAO registroEventoDAO;
    private final SolicitudDAO solicitudDAO;

    // IDs según tu BD
    private static final int ESTADO_READY  = 1;
    private static final int ESTADO_LOCKED = 2;

    // Estados Solicitud según tu BD
    private static final int SOL_PENDIENTE = 1;

    // Admin por defecto (mientras no tengas login real en BL)
    private static final int ADMIN_DEFAULT = 1;

    public CasilleroService() throws AppException {
        this.casilleroDAO = new CasilleroDAO();
        this.credencialDAO = new CredencialCasilleroDAO();
        this.tipoEventoDAO = new TipoEventoDAO();
        this.registroEventoDAO = new RegistroEventoDAO();
        this.solicitudDAO = new SolicitudDAO();
    }

    /**
     * Valida PIN del casillero:
     * - OK  -> resetea intentos, registra evento Pin OK
     * - FAIL-> incrementa intentos, registra Pin FAIL
     * - al 3er FAIL -> bloquea, registra Locked 3 Fails y crea Solicitud Pendiente (si no existe)
     */
    public ResultadoValidacionPin validarPin(int idCasillero, int idUsuario, String pinPlano) throws AppException {

        CasilleroDTO dto = casilleroDAO.obtenerPorId(idCasillero);
        if (dto == null) throw new AppException("Casillero no existe", null, getClass(), "validarPin");

        // Si ya está bloqueado, NO seguimos registrando Pin FAIL (evita spam de eventos)
        if (dto.getIdEstadoCasillero() != null && dto.getIdEstadoCasillero() == ESTADO_LOCKED) {
            // Asegura que exista solicitud pendiente (por si fue bloqueado por otro flujo)
            asegurarSolicitudPendiente(idCasillero);
            return ResultadoValidacionPin.BLOQUEADO;
        }

        CredencialCasilleroDTO cred = credencialDAO.getByCasillero(idCasillero);
        if (cred == null || cred.getPinHash() == null)
            throw new AppException("Casillero sin credencial", null, getClass(), "validarPin");

        String hashIngresado = sha256(pinPlano);
        boolean ok = hashIngresado.equalsIgnoreCase(cred.getPinHash());

        if (ok) {
            casilleroDAO.resetIntentos(idCasillero);

            Integer idTipoOk = tipoEventoDAO.findIdByName("Pin OK");
            if (idTipoOk != null) registroEventoDAO.crearEvento(idTipoOk, idUsuario, idCasillero);

            return ResultadoValidacionPin.OK;
        }

        // FAIL
        casilleroDAO.incrementarIntentos(idCasillero);

        Integer idTipoFail = tipoEventoDAO.findIdByName("Pin FAIL");
        if (idTipoFail != null) registroEventoDAO.crearEvento(idTipoFail, idUsuario, idCasillero);

        // leer intentos actuales
        CasilleroDTO dto2 = casilleroDAO.obtenerPorId(idCasillero);
        int intentos = (dto2 != null && dto2.getIntentosFallidos() != null) ? dto2.getIntentosFallidos() : 0;

        if (intentos >= 3) {
            casilleroDAO.actualizarEstado(idCasillero, ESTADO_LOCKED);

            Integer idTipoLocked = tipoEventoDAO.findIdByName("Locked 3 Fails");
            if (idTipoLocked != null) registroEventoDAO.crearEvento(idTipoLocked, idUsuario, idCasillero);

            // ✅ crea solicitud pendiente solo si NO existe una pendiente activa
            asegurarSolicitudPendiente(idCasillero);

            return ResultadoValidacionPin.BLOQUEADO;
        }

        return ResultadoValidacionPin.FAIL;
    }

    /**
     * Evita duplicar solicitudes: si ya hay una Pendiente activa para ese casillero, no crea otra.
     */
    private void asegurarSolicitudPendiente(int idCasillero) throws AppException {
        List<SolicitudDTO> list = solicitudDAO.listarPorCasillero(idCasillero);
        boolean yaExistePendiente = false;

        if (list != null) {
            for (SolicitudDTO s : list) {
                if (s != null
                        && "A".equalsIgnoreCase(s.getEstado())
                        && s.getIdEstadoSolicitud() != null
                        && s.getIdEstadoSolicitud() == SOL_PENDIENTE) {
                    yaExistePendiente = true;
                    break;
                }
            }
        }

        if (!yaExistePendiente) {
            solicitudDAO.crearSolicitud(idCasillero, ADMIN_DEFAULT, SOL_PENDIENTE);
        }
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
            throw new AppException("Error SHA-256", e, CasilleroService.class, "sha256");
        }
    }

    public enum ResultadoValidacionPin {
        OK, FAIL, BLOQUEADO
    }
}
