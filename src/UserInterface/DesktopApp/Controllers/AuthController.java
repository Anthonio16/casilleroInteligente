package UserInterface.DesktopApp.Controllers;

import BusinessLogic.Services.AuthService;
import DataAccess.DTOs.UsuarioDTO;
import Infrastructure.AppException;
import UserInterface.DesktopApp.AppComponent;

public class AuthController {

    private final AppComponent app;
    private final AuthService service;

    public AuthController(AppComponent app) {
        this.app = app;
        this.service = app.authService;
    }

    public UsuarioDTO login(String user, String pass) throws AppException {
        UsuarioDTO u = service.login(user, pass);
        app.setCurrentUser(u);
        return u;
    }

    public void logout() {
        app.setCurrentUser(null);
    }
}

