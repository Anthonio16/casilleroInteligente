package UserInterface.DesktopApp.Controllers;

import BusinessLogic.Services.EventoService;
import DataAccess.DTOs.RegistroEventoNombreDTO;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import java.util.List;

public class EventoController {

    private final EventoService service;

    public EventoController(AppComponent app) {
        this.service = app.eventoService;
    }

    public RegistroEventoNombreDTO ultimo(int idCasillero) throws AppException {
        return service.ultimoEventoConNombre(idCasillero);
    }

    public List<RegistroEventoNombreDTO> listar(int idCasillero) throws AppException {
        return service.listarConNombre(idCasillero);
    }
}

