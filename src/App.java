// =========================================================
// App.java  (LOGIN + RECUPERACIÓN + TOKEN + EVENTOS JOIN)
// =========================================================
import BusinessLogic.Services.AuthService;
import BusinessLogic.Services.CasilleroService;
import BusinessLogic.Services.EventoService;
import BusinessLogic.Services.RecuperacionService;
import BusinessLogic.Services.TokenService;

import DataAccess.DAOs.CasilleroDAO;

import DataAccess.DTOs.CasilleroDTO;
import DataAccess.DTOs.RegistroEventoNombreDTO;
import DataAccess.DTOs.SolicitudDTO;
import DataAccess.DTOs.TokenAccesoDTO;
import DataAccess.DTOs.UsuarioDTO;

import Infrastructure.AppException;

import java.util.List;

public class App {

    public static void main(String[] args) {

        try {
            // =========================
            // 1) LOGIN ADMIN
            // =========================
            System.out.println("===== LOGIN (patmic/patmic123) =====");
            AuthService auth = new AuthService();
            UsuarioDTO admin = auth.login("patmic", "patmic123");
            System.out.println("LOGIN OK:");
            System.out.println(admin);

            int idAdmin = admin.getIdUsuario();

            // IDs de prueba (ajusta si tu BD tiene otros)
            int idEstudiante = 2; // estudiante1
            int cas1 = 1;
            int cas2 = 2;

            // =========================
            // 2) SERVICES + DAO apoyo
            // =========================
            CasilleroService casilleroService = new CasilleroService();
            RecuperacionService recService    = new RecuperacionService();
            TokenService tokenService         = new TokenService();
            EventoService eventoService       = new EventoService();

            CasilleroDAO casilleroDAO         = new CasilleroDAO(); // para imprimir estado del casillero

            // =========================================================
            // TEST RecuperacionService (APROBAR + RECHAZAR)
            // =========================================================
            System.out.println("\n===== TEST RecuperacionService (APROBAR + RECHAZAR) =====");

            // =========================
            // 3) APROBAR
            // =========================
            List<SolicitudDTO> pendientes = recService.listarPendientes();
            System.out.println("Pendientes iniciales: " + (pendientes == null ? 0 : pendientes.size()));

            if (pendientes == null || pendientes.isEmpty()) {
                System.out.println("\nNo hay pendientes. Creando una pendiente con CasilleroService (3 FAILS)...\n");
                crearPendienteConTresFails(casilleroService, cas1, idEstudiante);
                pendientes = recService.listarPendientes();
                System.out.println("Pendientes ahora: " + (pendientes == null ? 0 : pendientes.size()));
            }

            SolicitudDTO solAprobar = pendientes.get(0);

            System.out.println("\n--- APROBAR ---");
            System.out.println("Solicitud a aprobar:");
            System.out.println(solAprobar);

            Integer idToken = recService.aprobarSolicitud(solAprobar.getIdSolicitud(), idAdmin);
            System.out.println("Aprobada | Token creado ID: " + idToken);

            TokenAccesoDTO tokenActivo = recService.tokenActivoCasillero(solAprobar.getIdCasillero());
            System.out.println("Token activo casillero " + solAprobar.getIdCasillero() + ":");
            System.out.println(tokenActivo);

            // Casillero después de aprobar (debe quedar READY=1 e intentos=0 si tu service lo hace así)
            CasilleroDTO casAprob = casilleroDAO.obtenerPorId(solAprobar.getIdCasillero());
            System.out.println("Casillero después de aprobar (debe quedar READY=1):");
            System.out.println(casAprob);

            // Validar token (debe ser OK)
            if (tokenActivo != null && tokenActivo.getTokenHash() != null) {
                System.out.println("\n--- VALIDAR TOKEN (debe ser OK) ---");
                TokenService.ResultadoValidacionToken rTok = tokenService.validarToken(
                    tokenActivo.getIdCasillero(),
                    idAdmin,
                    tokenActivo.getTokenHash()
                );
                System.out.println("validarToken -> " + rTok);

                CasilleroDTO casPostTok = casilleroDAO.obtenerPorId(tokenActivo.getIdCasillero());
                System.out.println("Casillero después de validar token (READY=1):");
                System.out.println(casPostTok);
            } else {
                System.out.println("\nNo hay token activo para validar.");
            }

            // Último evento con nombre (JOIN)
            System.out.println("\nÚltimo evento casillero " + solAprobar.getIdCasillero() + " (JOIN):");
            RegistroEventoNombreDTO ult1 = eventoService.ultimoEventoConNombre(solAprobar.getIdCasillero());
            System.out.println(ult1);

            // =========================
            // 4) RECHAZAR (creamos otra pendiente en cas2)
            // =========================
            System.out.println("\n--- RECHAZAR ---");
            System.out.println("Forzando 3 FAILS en casillero " + cas2 + " (usuario " + idEstudiante + ") ...");
            crearPendienteConTresFails(casilleroService, cas2, idEstudiante);

            List<SolicitudDTO> pend2 = recService.listarPendientes();
            if (pend2 == null || pend2.isEmpty()) {
                System.out.println("No se generó pendiente para rechazar.");
                return;
            }

            // buscamos una pendiente de cas2
            SolicitudDTO solRech = null;
            for (SolicitudDTO s : pend2) {
                if (s != null && s.getIdCasillero() != null && s.getIdCasillero() == cas2) {
                    solRech = s;
                    break;
                }
            }
            if (solRech == null) solRech = pend2.get(0);

            System.out.println("Solicitud a rechazar:");
            System.out.println(solRech);

            recService.rechazarSolicitud(solRech.getIdSolicitud(), idAdmin);
            System.out.println("Rechazada (idSolicitud=" + solRech.getIdSolicitud() + ")");

            // Último evento con nombre (JOIN)
            System.out.println("Último evento casillero " + solRech.getIdCasillero() + " (JOIN):");
            RegistroEventoNombreDTO ult2 = eventoService.ultimoEventoConNombre(solRech.getIdCasillero());
            System.out.println(ult2);

            // =========================
            // 5) LISTAR EVENTOS CON NOMBRE (JOIN)
            // =========================
            System.out.println("\n--- EVENTOS CASILLERO " + cas1 + " (JOIN TOP) ---");
            List<RegistroEventoNombreDTO> evs1 = eventoService.listarConNombre(cas1);
            imprimirTop(evs1, 10);

        } catch (AppException ae) {
            System.out.println("\nFALLÓ (AppException): " + ae.getMessage());
            ae.printStackTrace();
        } catch (Exception e) {
            System.out.println("\nFALLÓ (Exception): " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =========================================================
    // Helpers de test
    // =========================================================
    private static void crearPendienteConTresFails(CasilleroService casService, int idCasillero, int idUsuario) throws AppException {
        for (int i = 1; i <= 3; i++) {
            CasilleroService.ResultadoValidacionPin r = casService.validarPin(idCasillero, idUsuario, "0000");
            System.out.println("Try " + i + ": " + r);
            if (r == CasilleroService.ResultadoValidacionPin.BLOQUEADO) break;
        }
    }

    private static void imprimirTop(List<?> list, int max) {
        if (list == null || list.isEmpty()) {
            System.out.println("(sin eventos)");
            return;
        }
        int n = Math.min(max, list.size());
        for (int i = 0; i < n; i++) {
            System.out.println(list.get(i));
        }
    }
}
