package BusinessLogic.Services;

import DataAccess.DAOs.RegistroEventoDAO;
import DataAccess.DAOs.SolicitudDAO;
import DataAccess.DAOs.TipoEventoDAO;
import DataAccess.DAOs.TokenAccesoDAO;
import DataAccess.DTOs.SolicitudDTO;
import DataAccess.DTOs.TokenAccesoDTO;
import Infrastructure.AppException;
import java.util.List;

public class RecuperacionService {

    private final SolicitudDAO solicitudDAO;
    private final TokenAccesoDAO tokenDAO;
    private final TipoEventoDAO tipoEventoDAO;
    private final RegistroEventoDAO eventoDAO;

    // Catálogo EstadoSolicitud (según tus inserts)
    private static final int EST_PENDIENTE = 1;
    private static final int EST_APROBADA  = 2;
    private static final int EST_RECHAZADA = 3;

    public RecuperacionService() throws AppException {
        this.solicitudDAO = new SolicitudDAO();
        this.tokenDAO = new TokenAccesoDAO();
        this.tipoEventoDAO = new TipoEventoDAO();
        this.eventoDAO = new RegistroEventoDAO();
    }

    public List<SolicitudDTO> listarPendientes() throws AppException {
        return solicitudDAO.listarPendientes(); // debe existir en tu SolicitudDAO
    }

    // Admin aprueba: cambia estado -> desactiva tokens anteriores -> crea token 15 min -> registra evento
    public Integer aprobarSolicitud(int idSolicitud, int idAdmin) throws AppException {
        SolicitudDTO sol = solicitudDAO.obtenerPorId(idSolicitud);
        if (sol == null) {
            throw new AppException("Solicitud no existe", null, getClass(), "aprobarSolicitud");
        }

        // Solo permitir si está pendiente (opcional pero recomendado)
        if (sol.getIdEstadoSolicitud() == null || sol.getIdEstadoSolicitud() != EST_PENDIENTE) {
            throw new AppException("Solicitud no está PENDIENTE", null, getClass(), "aprobarSolicitud");
        }

        solicitudDAO.actualizarEstadoSolicitud(idSolicitud, EST_APROBADA);

        // ✅ desactiva tokens activos del casillero
        tokenDAO.desactivarTokensPorCasillero(sol.getIdCasillero());

        Integer idToken = tokenDAO.crearToken15Min(
            sol.getIdSolicitud(),
            sol.getIdCasillero(),
            "hash_" + System.currentTimeMillis()
        );

        Integer idTipoEvento = tipoEventoDAO.findIdByName("Solicitud de Recuperación Aprobada");
        if (idTipoEvento != null) eventoDAO.crearEvento(idTipoEvento, idAdmin, sol.getIdCasillero());

        return idToken;
    }

    // Admin rechaza: cambia estado -> registra evento
    public void rechazarSolicitud(int idSolicitud, int idAdmin) throws AppException {
        SolicitudDTO sol = solicitudDAO.obtenerPorId(idSolicitud);
        if (sol == null) {
            throw new AppException("Solicitud no existe", null, getClass(), "rechazarSolicitud");
        }

        if (sol.getIdEstadoSolicitud() == null || sol.getIdEstadoSolicitud() != EST_PENDIENTE) {
            throw new AppException("Solicitud no está PENDIENTE", null, getClass(), "rechazarSolicitud");
        }

        solicitudDAO.actualizarEstadoSolicitud(idSolicitud, EST_RECHAZADA);

        Integer idTipoEvento = tipoEventoDAO.findIdByName("Solicitud de Recuperación Rechazada");
        if (idTipoEvento != null) eventoDAO.crearEvento(idTipoEvento, idAdmin, sol.getIdCasillero());
    }

    public TokenAccesoDTO tokenActivoCasillero(int idCasillero) throws AppException {
        return tokenDAO.obtenerActivoPorCasillero(idCasillero);
    }
}


