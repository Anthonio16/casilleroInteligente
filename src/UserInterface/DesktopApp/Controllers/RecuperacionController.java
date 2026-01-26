package UserInterface.DesktopApp.Controllers;

import BusinessLogic.Services.RecuperacionService;
import DataAccess.DTOs.SolicitudDTO;
import DataAccess.DTOs.TokenAccesoDTO;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import java.util.List;

public class RecuperacionController {

    private final AppComponent app;
    private final RecuperacionService service;

    public RecuperacionController(AppComponent app) {
        this.app = app;
        this.service = app.recuperacionService;
    }

    public List<SolicitudDTO> listarPendientes() throws AppException {
        return service.listarPendientes();
    }

    public Integer aprobar(int idSolicitud) throws AppException {
        int idAdmin = app.getCurrentUser().getIdUsuario();
        return service.aprobarSolicitud(idSolicitud, idAdmin);
    }

    public void rechazar(int idSolicitud) throws AppException {
        int idAdmin = app.getCurrentUser().getIdUsuario();
        service.rechazarSolicitud(idSolicitud, idAdmin);
    }

    public TokenAccesoDTO tokenActivo(int idCasillero) throws AppException {
        return service.tokenActivoCasillero(idCasillero);
    }
}

