package UserInterface.DesktopApp.Controllers;

import BusinessLogic.Services.AuditoriaService;
import DataAccess.DTOs.AuditoriaDTO;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;
import java.util.List;

public class AuditoriaController {

    private final AuditoriaService service;

    public AuditoriaController(AppComponent app) {
        this.service = app.auditoriaService;
    }

    public List<AuditoriaDTO> admin(int limit) throws AppException {
        return service.auditoriaAdmin(limit);
    }

    public List<AuditoriaDTO> adminPorCasillero(int idCasillero, int limit) throws AppException {
        return service.auditoriaAdminPorCasillero(idCasillero, limit);
    }

    public List<AuditoriaDTO> estudiante(int idEstudiante, int limit) throws AppException {
        return service.auditoriaEstudiante(idEstudiante, limit);
    }
}

