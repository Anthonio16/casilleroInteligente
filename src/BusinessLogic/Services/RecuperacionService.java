package BusinessLogic.Services;

import DataAccess.DAOs.CasilleroDAO;
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
    private final CasilleroDAO casilleroDAO;
    private static final int EST_PENDIENTE = 1;
    private static final int EST_APROBADA  = 2;
    private static final int EST_RECHAZADA = 3;
    private static final int ESTADO_READY = 1;
    private static final String EV_SOL_APR  = "Solicitud de Recuperación Aprobada";
    private static final String EV_SOL_REC  = "Solicitud de Recuperación Rechazada";
    private static final String EV_DESBLOQ  = "Desbloqueo";

    public RecuperacionService() throws AppException {
        this.solicitudDAO = new SolicitudDAO();
        this.tokenDAO = new TokenAccesoDAO();
        this.tipoEventoDAO = new TipoEventoDAO();
        this.eventoDAO = new RegistroEventoDAO();
        this.casilleroDAO = new CasilleroDAO();
    }

    public List<SolicitudDTO> listarPendientes() throws AppException {
        return solicitudDAO.listarPendientes();
    }


    public Integer aprobarSolicitud(int idSolicitud, int idAdmin) throws AppException {

        SolicitudDTO sol = solicitudDAO.obtenerPorId(idSolicitud);
        if (sol == null) {
            throw new AppException("Solicitud no existe", null, getClass(), "aprobarSolicitud");
        }
        if (sol.getIdEstadoSolicitud() == null || sol.getIdEstadoSolicitud() != EST_PENDIENTE) {
            throw new AppException("Solicitud no está PENDIENTE", null, getClass(), "aprobarSolicitud");
        }

        boolean okUpd = solicitudDAO.atenderSolicitud(idSolicitud, idAdmin, EST_APROBADA);
        if (!okUpd) {
            throw new AppException("No se pudo actualizar solicitud", null, getClass(), "aprobarSolicitud");
        }

        tokenDAO.desactivarTokensPorCasillero(sol.getIdCasillero());

        Integer idToken = tokenDAO.crearToken15Min(
            sol.getIdSolicitud(),
            sol.getIdCasillero(),
            "hash_" + System.currentTimeMillis()
        );
        if (idToken == null) {
            throw new AppException("No se pudo crear Token", null, getClass(), "aprobarSolicitud");
        }

        boolean okReady = casilleroDAO.actualizarEstado(sol.getIdCasillero(), ESTADO_READY);
        if (!okReady) {
            throw new AppException("No se pudo poner casillero en READY", null, getClass(), "aprobarSolicitud");
        }

        eventoDAO.crearEvento(tipoEventoIdOrThrow(EV_SOL_APR), idAdmin, sol.getIdCasillero());
        eventoDAO.crearEvento(tipoEventoIdOrThrow(EV_DESBLOQ), idAdmin, sol.getIdCasillero());

        return idToken;
    }

    public void rechazarSolicitud(int idSolicitud, int idAdmin) throws AppException {

        SolicitudDTO sol = solicitudDAO.obtenerPorId(idSolicitud);
        if (sol == null) {
            throw new AppException("Solicitud no existe", null, getClass(), "rechazarSolicitud");
        }
        if (sol.getIdEstadoSolicitud() == null || sol.getIdEstadoSolicitud() != EST_PENDIENTE) {
            throw new AppException("Solicitud no está PENDIENTE", null, getClass(), "rechazarSolicitud");
        }

        boolean okUpd = solicitudDAO.atenderSolicitud(idSolicitud, idAdmin, EST_RECHAZADA);
        if (!okUpd) {
            throw new AppException("No se pudo actualizar solicitud", null, getClass(), "rechazarSolicitud");
        }

        eventoDAO.crearEvento(tipoEventoIdOrThrow(EV_SOL_REC), idAdmin, sol.getIdCasillero());
    }

    public TokenAccesoDTO tokenActivoCasillero(int idCasillero) throws AppException {
        return tokenDAO.obtenerActivoPorCasillero(idCasillero);
    }

    private int tipoEventoIdOrThrow(String nombre) throws AppException {
        Integer id = tipoEventoDAO.findIdByName(nombre);
        if (id == null) {
            throw new AppException("No existe TipoEvento: " + nombre, null, getClass(), "tipoEventoIdOrThrow");
        }
        return id;
    }


}

