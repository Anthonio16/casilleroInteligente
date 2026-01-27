package UserInterface.DesktopApp;

import BusinessLogic.Services.AuditoriaService;
import BusinessLogic.Services.AuthService;
import BusinessLogic.Services.CasilleroService;
import BusinessLogic.Services.EventoService;
import BusinessLogic.Services.RecuperacionService;
import BusinessLogic.Services.TokenService;
import DataAccess.DTOs.UsuarioDTO;
import Infrastructure.AppException;
import UserInterface.DesktopApp.Controllers.*;
import UserInterface.DesktopApp.Forms.*;

public class AppComponent {

    // ===== Sesión =====
    private UsuarioDTO currentUser;

    public UsuarioDTO getCurrentUser() { return currentUser; }
    public void setCurrentUser(UsuarioDTO u) { this.currentUser = u; }

    public boolean isAdmin() {
        return currentUser != null && currentUser.getIdUsuarioTipo() != null && currentUser.getIdUsuarioTipo() == 1;
    }

    // ===== BL Services =====
    public final AuthService authService;
    public final CasilleroService casilleroService;
    public final RecuperacionService recuperacionService;
    public final TokenService tokenService;
    public final EventoService eventoService;
    public final BusinessLogic.Services.AuditoriaService auditoriaService;

    // ===== Controllers =====
    public final AuthController authController;
    public final CasilleroController casilleroController;
    public final RecuperacionController recuperacionController;
    public final TokenController tokenController;
    public final EventoController eventoController;
    public final UserInterface.DesktopApp.Controllers.AuditoriaController auditoriaController;

    // ===== Forms =====
    public final PSplashScreen pSplash;
    public final PLogin pLogin;
    public final PHome pHome;
    public final PValidarPin pValidarPin;
    public final PRecuperaciones pRecuperaciones;
    public final PValidarToken pValidarToken;
    public final PEventos pEventos;

    public AppComponent() throws AppException {

        // 1) Instanciar Services reales (los tuyos)
        this.authService = new AuthService();
        this.casilleroService = new CasilleroService();
        this.recuperacionService = new RecuperacionService();
        this.tokenService = new TokenService();
        this.eventoService = new EventoService();
        this.auditoriaService = new AuditoriaService();


        // 2) Controllers
        this.authController = new AuthController(this);
        this.casilleroController = new CasilleroController(this);
        this.recuperacionController = new RecuperacionController(this);
        this.tokenController = new TokenController(this);
        this.eventoController = new EventoController(this);
        this.auditoriaController = new AuditoriaController(this);


        // 3) Forms (pantallas P...)
        this.pSplash = new PSplashScreen(this);
        this.pLogin = new PLogin(this);
        this.pHome = new PHome(this);
        this.pValidarPin = new PValidarPin(this);
        this.pRecuperaciones = new PRecuperaciones(this);
        this.pValidarToken = new PValidarToken(this);
        this.pEventos = new PEventos(this);
    }

    // Navegación simple
    public void showLogin() {
        hideAll();
        pLogin.setLocationRelativeTo(null);
        pLogin.setVisible(true);
    }

    public void showHome() {
        hideAll();
        pHome.refresh();
        pHome.setLocationRelativeTo(null);
        pHome.setVisible(true);
    }

    public void showValidarPin() {
        hideAll();
        pValidarPin.refresh();
        pValidarPin.setLocationRelativeTo(null);
        pValidarPin.setVisible(true);
    }

    public void showRecuperaciones() {
        hideAll();
        pRecuperaciones.refresh();
        pRecuperaciones.setLocationRelativeTo(null);
        pRecuperaciones.setVisible(true);
    }

    public void showValidarToken() {
        hideAll();
        pValidarToken.refresh();
        pValidarToken.setLocationRelativeTo(null);
        pValidarToken.setVisible(true);
    }

    public void showEventos() {
        hideAll();
        pEventos.refresh();
        pEventos.setLocationRelativeTo(null);
        pEventos.setVisible(true);
    }

    private void hideAll() {
        pSplash.setVisible(false);
        pLogin.setVisible(false);
        pHome.setVisible(false);
        pValidarPin.setVisible(false);
        pRecuperaciones.setVisible(false);
        pValidarToken.setVisible(false);
        pEventos.setVisible(false);
    }
}

