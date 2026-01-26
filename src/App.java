// =========================================================
// App.java  (TEST COMPLETO: LOGIN + PIN(3 FAILS) + APROBAR + TOKEN + EVENTOS JOIN)
// =========================================================
import java.util.List;

import BusinessLogic.Services.AuthService;
import BusinessLogic.Services.CasilleroService;
import BusinessLogic.Services.RecuperacionService;
import BusinessLogic.Services.EventoService;

import DataAccess.DTOs.UsuarioDTO;
import DataAccess.DTOs.SolicitudDTO;
import DataAccess.DTOs.TokenAccesoDTO;
import DataAccess.DTOs.CasilleroDTO;
import DataAccess.DTOs.EventoConNombreDTO;

import DataAccess.DAOs.CasilleroDAO;
import DataAccess.DAOs.TokenAccesoDAO;

import Infrastructure.AppException;

public class App {

    public static void main(String[] args) {
        try {
            System.out.println("===== LOGIN (patmic/patmic123) =====");

            AuthService auth = new AuthService();
            UsuarioDTO admin = auth.login("patmic", "patmic123");

            System.out.println("\nLOGIN OK:");
            System.out.println(admin);

            // ===== Services =====
            CasilleroService casilleroService = new CasilleroService();
            RecuperacionService recuperacionService = new RecuperacionService();
            EventoService eventoService = new EventoService();

            // ===== DAOs (solo para imprimir estado y token) =====
            CasilleroDAO casilleroDAO = new CasilleroDAO();
            TokenAccesoDAO tokenDAO = new TokenAccesoDAO();

            System.out.println("\n===== TEST RecuperacionService (APROBAR + RECHAZAR) =====");

            // -----------------------------------------------------------------
            // 1) Pendientes iniciales
            // -----------------------------------------------------------------
            List<SolicitudDTO> pendientes = recuperacionService.listarPendientes();
            System.out.println("Pendientes iniciales: " + (pendientes == null ? 0 : pendientes.size()));

            // -----------------------------------------------------------------
            // 2) Si no hay pendientes, creamos una con 3 FAILS en casillero 1
            // -----------------------------------------------------------------
            if (pendientes == null || pendientes.isEmpty()) {
                System.out.println("\nNo hay pendientes. Creando una pendiente con CasilleroService (3 FAILS)...");
                crearPendienteConTresFails(casilleroService, 1, 2); // casillero=1, usuario=2 (estudiante2)
                pendientes = recuperacionService.listarPendientes();
                System.out.println("Pendientes ahora: " + (pendientes == null ? 0 : pendientes.size()));
            }

            if (pendientes == null || pendientes.isEmpty()) {
                throw new AppException("No se pudo crear una solicitud pendiente para probar", null, App.class, "main");
            }

            // Tomamos la primera pendiente (más reciente, por tu ORDER BY DESC)
            SolicitudDTO solAprobar = pendientes.get(0);

            // -----------------------------------------------------------------
            // 3) APROBAR
            // -----------------------------------------------------------------
            System.out.println("\n--- APROBAR ---");
            System.out.println("Solicitud a aprobar:");
            System.out.println(solAprobar);

            Integer idTokenCreado = recuperacionService.aprobarSolicitud(solAprobar.getIdSolicitud(), admin.getIdUsuario());
            System.out.println("Aprobada | Token creado ID: " + idTokenCreado);

            // Token activo del casillero
            TokenAccesoDTO tokenActivo = tokenDAO.obtenerActivoPorCasillero(solAprobar.getIdCasillero());
            System.out.println("Token activo casillero " + solAprobar.getIdCasillero() + ":");
            System.out.println(tokenActivo);

            // Casillero debe quedar READY=1
            CasilleroDTO cas1 = casilleroDAO.obtenerPorId(solAprobar.getIdCasillero());
            System.out.println("Casillero después de aprobar (debe quedar READY=1):");
            System.out.println(cas1);

            // Último evento con nombre (JOIN)
            System.out.println("Último evento casillero " + solAprobar.getIdCasillero() + " (JOIN):");
            EventoConNombreDTO ultJoin1 = eventoService.ultimoEventoConNombre(solAprobar.getIdCasillero());
            System.out.println(ultJoin1);

            // -----------------------------------------------------------------
            // 4) Validar TOKEN (debe abrir / dejar READY=1 y registrar Token OK + Desbloqueo)
            // -----------------------------------------------------------------
            System.out.println("\n--- VALIDAR TOKEN (debe ser OK) ---");
            if (tokenActivo == null || tokenActivo.getTokenHash() == null) {
                System.out.println("No hay token activo para probar validarToken().");
            } else {
                boolean tokenOk = casilleroService.validarToken(
                    solAprobar.getIdCasillero(),
                    admin.getIdUsuario(),          // quien ejecuta la acción
                    tokenActivo.getTokenHash()     // usamos exactamente el TokenHash para que pase
                );
                System.out.println("validarToken -> " + (tokenOk ? "OK" : "FAIL"));

                CasilleroDTO cas1b = casilleroDAO.obtenerPorId(solAprobar.getIdCasillero());
                System.out.println("Casillero después de validar token (READY=1):");
                System.out.println(cas1b);

                System.out.println("Último evento casillero " + solAprobar.getIdCasillero() + " (JOIN):");
                EventoConNombreDTO ultJoin1b = eventoService.ultimoEventoConNombre(solAprobar.getIdCasillero());
                System.out.println(ultJoin1b);
            }

            // -----------------------------------------------------------------
            // 5) RECHAZAR: forzamos 3 FAILS en casillero 2 para crear otra pendiente
            // -----------------------------------------------------------------
            System.out.println("\n--- RECHAZAR ---");
            System.out.println("Forzando 3 FAILS en casillero 2 (usuario 2) ...");
            crearPendienteConTresFails(casilleroService, 2, 2);

            List<SolicitudDTO> pendientes2 = recuperacionService.listarPendientes();
            if (pendientes2 == null || pendientes2.isEmpty()) {
                System.out.println("No hay pendientes para rechazar (raro).");
            } else {
                // La más reciente debería ser del casillero 2
                SolicitudDTO solRechazar = pendientes2.get(0);

                System.out.println("Solicitud a rechazar:");
                System.out.println(solRechazar);

                recuperacionService.rechazarSolicitud(solRechazar.getIdSolicitud(), admin.getIdUsuario());
                System.out.println("Rechazada (idSolicitud=" + solRechazar.getIdSolicitud() + ")");

                System.out.println("Último evento casillero " + solRechazar.getIdCasillero() + " (JOIN):");
                EventoConNombreDTO ultJoin2 = eventoService.ultimoEventoConNombre(solRechazar.getIdCasillero());
                System.out.println(ultJoin2);
            }

            // -----------------------------------------------------------------
            // 6) LISTAR eventos con nombre (JOIN) para casillero 1 (TOP)
            // -----------------------------------------------------------------
            System.out.println("\n--- EVENTOS CASILLERO 1 (JOIN / TOP) ---");
            List<EventoConNombreDTO> eventos1 = eventoService.listarConNombre(1);
            if (eventos1 == null || eventos1.isEmpty()) {
                System.out.println("No hay eventos para casillero 1");
            } else {
                int top = Math.min(8, eventos1.size());
                for (int i = 0; i < top; i++) {
                    System.out.println(eventos1.get(i));
                }
            }

        } catch (AppException ae) {
            System.err.println("\nFALLÓ (AppException): " + ae.getMessage());
            ae.printStackTrace();
        } catch (Exception e) {
            System.err.println("\nFALLÓ (Exception): " + (e != null ? e.getMessage() : "null"));
            e.printStackTrace();
        }
    }

    // =========================================================
    // Helpers de test
    // =========================================================

    /**
     * Fuerza 3 fallos seguidos para bloquear y crear solicitud pendiente.
     * - casilleroService.validarPin se encarga de:
     *   - incrementar intentos
     *   - al 3er fail -> LOCKED + evento Locked 3 Fails + crear Solicitud Pendiente
     */
    private static void crearPendienteConTresFails(CasilleroService casilleroService, int idCasillero, int idUsuario)
        throws AppException {

        // Pin incorrecto intencional (cualquier valor que NO coincida con pinHash)
        String pinMalo = "00000";

        for (int i = 1; i <= 3; i++) {
            CasilleroService.ResultadoValidacionPin r = casilleroService.validarPin(idCasillero, idUsuario, pinMalo);
            System.out.println("Try " + i + ": " + r);
            if (r == CasilleroService.ResultadoValidacionPin.BLOQUEADO) break;
        }

        // Mostrar estado casillero
        CasilleroDAO casilleroDAO = new CasilleroDAO();
        CasilleroDTO dto = casilleroDAO.obtenerPorId(idCasillero);
        System.out.println("Casillero actual:");
        System.out.println(dto);
    }
}
