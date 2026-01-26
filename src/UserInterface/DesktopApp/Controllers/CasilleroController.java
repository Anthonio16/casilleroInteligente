package UserInterface.DesktopApp.Controllers;

import BusinessLogic.Services.CasilleroService;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;

public class CasilleroController {

    private final AppComponent app;
    private final CasilleroService service;

    public CasilleroController(AppComponent app) {
        this.app = app;
        this.service = app.casilleroService;
    }

    public CasilleroService.ResultadoValidacionPin validarPin(int idCasillero, String pin) throws AppException {
        int idUsuario = app.getCurrentUser().getIdUsuario();
        return service.validarPin(idCasillero, idUsuario, pin);
    }
}

