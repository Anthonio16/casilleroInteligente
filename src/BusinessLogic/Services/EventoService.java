// =========================================================
// BusinessLogic/Services/EventoService.java
// =========================================================
package BusinessLogic.Services;

import DataAccess.DAOs.EventoDAOJoin;
import DataAccess.DTOs.EventoConNombreDTO;
import Infrastructure.AppException;
import java.util.List;

public class EventoService {

    private final EventoDAOJoin eventoJoinDAO;

    public EventoService() throws AppException {
        this.eventoJoinDAO = new EventoDAOJoin();
    }

    public EventoConNombreDTO ultimoEventoConNombre(int idCasillero) throws AppException {
        return eventoJoinDAO.ultimoEventoConNombre(idCasillero);
    }

    public List<EventoConNombreDTO> listarConNombre(int idCasillero) throws AppException {
        return eventoJoinDAO.listarConNombre(idCasillero);
    }
}






