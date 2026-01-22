import DataAccess.DAOs.EstadoSolicitudDAO;
import DataAccess.DAOs.SolicitudDAO;
import DataAccess.DAOs.TokenAccesoDAO;
import DataAccess.DTOs.EstadoSolicitudDTO;
import DataAccess.DTOs.SolicitudDTO;
import DataAccess.DTOs.TokenAccesoDTO;
import Infrastructure.AppException;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        // var estadoDAO = new EstadoCasilleroDAO();
        // System.out.println("Locked ID = " + estadoDAO.obtenerIdPorNombre("Locked"));

        // var casDAO = new CasilleroDAO();
        // System.out.println(casDAO.obtenerPorId(1));
        // casDAO.incrementarIntentos(1);
        // System.out.println(casDAO.obtenerPorId(1));

    

        // var teDAO = new TipoEventoDAO();
        // System.out.println(teDAO.findIdByName("Pin OK"));
        // System.out.println(teDAO.findByName("Pin FAIL"));
        // System.out.println(teDAO.findAll().size());

        // var udao = new UsuarioDAO();
        // System.out.println(udao.obtenerPorId(1));
        // System.out.println(udao.login("admin", "admin123"));
        // System.out.println(udao.listarActivos().size());

        // var utdao = new UsuarioTipoDAO();
        // System.out.println(utdao.obtenerIdPorNombre("Admin"));
        // System.out.println(utdao.obtenerPorNombre("Estudiante"));
        // System.out.println(utdao.listarActivos());


        try {
            testEstadoSolicitud_Solicitud_Token();
        } catch (AppException e) {
            System.out.println("\n? FALLÓ UN TEST (AppException): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("\n? FALLÓ UN TEST (Exception): " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =========================
    //  TEST 3 EN 1
    // =========================
    private static void testEstadoSolicitud_Solicitud_Token() throws AppException {
        System.out.println("\n===== TEST EstadoSolicitudDAO =====");

        EstadoSolicitudDAO estadoDAO = new EstadoSolicitudDAO();
        List<EstadoSolicitudDTO> estados = estadoDAO.readAll();
        System.out.println("Total estados solicitud: " + estados.size());

        if (!estados.isEmpty()) {
            System.out.println("Primero:\n" + estados.get(0));
        }

        // Si tienes método obtenerIdPorNombre("Pendiente") úsalo, si no, lo buscamos manualmente
        Integer idPendiente = buscarIdEstadoSolicitud(estados, "Pendiente");
        Integer idAprobada  = buscarIdEstadoSolicitud(estados, "Aprobada");

        System.out.println("Pendiente: " + idPendiente);
        System.out.println("ID Aprobada: " + idAprobada);

        // readBy
        if (idAprobada != null) {
            EstadoSolicitudDTO aprobada = estadoDAO.readBy(idAprobada);
            System.out.println("Por ID (Aprobada):\n" + aprobada);
        }

        System.out.println("\n===== TEST SolicitudDAO =====");

        SolicitudDAO solicitudDAO = new SolicitudDAO();

        List<SolicitudDTO> solicitudes = solicitudDAO.readAll();
        System.out.println("Total solicitudes: " + solicitudes.size());

        if (!solicitudes.isEmpty()) {
            System.out.println("Primera:\n" + solicitudes.get(0));
        }

        // readBy
        SolicitudDTO sol1 = solicitudDAO.readBy(1);
        System.out.println("Solicitud ID=1:\n" + sol1);

        // listar por casillero (si tu método se llama distinto, cambia aquí)
        List<SolicitudDTO> solicitudesCas1 = solicitudDAO.listarPorCasillero(1);
        System.out.println("Solicitudes casillero 1: " + solicitudesCas1.size());

        // crear solicitud nueva (casillero 3, admin 1, estado Pendiente)
        // Si tu DAO NO tiene crearSolicitud, comenta esto.
        Integer nuevaIdSolicitud = null;
        if (idPendiente == null) idPendiente = 1; // fallback

        nuevaIdSolicitud = solicitudDAO.crearSolicitud(3, 1, idPendiente);
        System.out.println("Nueva solicitud creada ID: " + nuevaIdSolicitud);

        SolicitudDTO nuevaSolicitud = solicitudDAO.readBy(nuevaIdSolicitud);
        System.out.println("Nueva solicitud DTO:\n" + nuevaSolicitud);

        System.out.println("\n===== TEST TokenAccesoDAO =====");

        TokenAccesoDAO tokenDAO = new TokenAccesoDAO();

        // crear token con expiración 15 min, para la nueva solicitud y casillero 3
        Integer nuevoIdToken = tokenDAO.crearToken15Min(nuevaIdSolicitud, 3, "hash_token_prueba_" + System.currentTimeMillis());
        System.out.println("Nuevo Token creado ID: " + nuevoIdToken);

        TokenAccesoDTO tokenLeido = tokenDAO.obtenerPorId(nuevoIdToken);
        System.out.println("Token por ID:\n" + tokenLeido);

        // obtener activo por casillero 3
        TokenAccesoDTO tokenActivo = tokenDAO.obtenerActivoPorCasillero(3);
        System.out.println("Token ACTIVO casillero 3:\n" + tokenActivo);

        // listar por solicitud
        List<TokenAccesoDTO> tokensSolicitud = tokenDAO.listarPorSolicitud(nuevaIdSolicitud);
        System.out.println("Tokens de la solicitud " + nuevaIdSolicitud + ": " + tokensSolicitud.size());
        if (!tokensSolicitud.isEmpty()) {
            System.out.println("Primero:\n" + tokensSolicitud.get(0));
        }

        // desactivar token
        tokenDAO.desactivarToken(nuevoIdToken);
        System.out.println("Token desactivado: " + nuevoIdToken);

        TokenAccesoDTO tokenLuego = tokenDAO.obtenerPorId(nuevoIdToken);
        System.out.println("Token luego de desactivar:\n" + tokenLuego);

        // si quieres: expirar vencidos
        tokenDAO.expirarTokensVencidos();
        System.out.println("ExpirarTokensVencidos() ejecutado ✅");
    }

    // =========================
    //  HELPERS DEL TEST
    // =========================
    private static Integer buscarIdEstadoSolicitud(List<EstadoSolicitudDTO> list, String nombre) {
        if (list == null) return null;
        for (EstadoSolicitudDTO e : list) {
            if (e != null && e.getNombre() != null && e.getNombre().equalsIgnoreCase(nombre)) {
                return e.getIdEstadoSolicitud();
            }
        }
        return null;
    }



    
}
