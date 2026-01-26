package UserInterface.DesktopApp.Controllers;

import BusinessLogic.Services.TokenService;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;

public class TokenController {

    private final AppComponent app;
    private final TokenService service;

    public TokenController(AppComponent app) {
        this.app = app;
        this.service = app.tokenService;
    }

    public TokenService.ResultadoValidacionToken validarToken(int idCasillero, String tokenIngresado) throws AppException {
        int idUsuario = app.getCurrentUser().getIdUsuario();
        return service.validarToken(idCasillero, idUsuario, tokenIngresado);
    }
}

