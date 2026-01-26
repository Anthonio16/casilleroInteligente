package BusinessLogic.Services;

import DataAccess.DAOs.EventoDAOJoin;
import DataAccess.DTOs.RegistroEventoNombreDTO;
import Infrastructure.AppException;
import java.util.List;

public class EventoService {

    private final EventoDAOJoin eventoDAO;

    public EventoService() throws AppException {
        this.eventoDAO = new EventoDAOJoin();
    }

    public RegistroEventoNombreDTO ultimoEventoConNombre(int idCasillero) throws AppException {
        return eventoDAO.ultimoEventoConNombre(idCasillero);
    }

    public List<RegistroEventoNombreDTO> listarConNombre(int idCasillero) throws AppException {
        return eventoDAO.listarConNombre(idCasillero);
    }
}







