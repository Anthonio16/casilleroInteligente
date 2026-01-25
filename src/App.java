import DataAccess.DTOs.SolicitudDTO;

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


    //     System.out.println("\n===== TEST EstadoSolicitudDAO =====");

    //         EstadoSolicitudDAO estadoDAO = new EstadoSolicitudDAO();
    //         var estados = estadoDAO.readAll(); // ya funciona porque tabla tiene Estado='A'
    //         System.out.println("Total estados solicitud: " + estados.size());
    //         if (!estados.isEmpty()) System.out.println("Primero:\n" + estados.get(0));

    //         Integer idPendiente = estadoDAO.obtenerIdPorNombre("Pendiente");
    //         System.out.println("Pendiente ID: " + idPendiente);

    //         System.out.println("\n===== TEST SolicitudDAO =====");
    //         SolicitudDAO solicitudDAO = new SolicitudDAO();

    //         var solicitudes = solicitudDAO.readAll();
    //         System.out.println("Total solicitudes: " + solicitudes.size());
    //         if (!solicitudes.isEmpty()) System.out.println("Primera:\n" + solicitudes.get(0));

    //         Integer nuevaId = solicitudDAO.crearSolicitud(3, 1, (idPendiente != null ? idPendiente : 1));
    //         System.out.println("Nueva solicitud creada ID: " + nuevaId);
    //         System.out.println("Nueva solicitud:\n" + solicitudDAO.readBy(nuevaId));

    //         System.out.println("\n===== TEST TokenAccesoDAO =====");
    //         TokenAccesoDAO tokenDAO = new TokenAccesoDAO();

    //         Integer tokenId = tokenDAO.crearToken15Min(nuevaId, 3, "hash_" + System.currentTimeMillis());
    //         System.out.println("Token creado ID: " + tokenId);

    //         var activo = tokenDAO.obtenerActivoPorCasillero(3);
    //         System.out.println("Token activo casillero 3:\n" + activo);

    //         var tokens = tokenDAO.listarPorSolicitud(nuevaId);
    //         System.out.println("Tokens por solicitud " + nuevaId + ": " + tokens.size());

    //         tokenDAO.desactivarToken(tokenId);
    //         System.out.println("Token desactivado ID: " + tokenId);

    // System.out.println("\n===== TEST RegistroEventoDAO =====");

    // RegistroEventoDAO dao = new RegistroEventoDAO();

    // // 1) Crear evento nuevo (ejemplo: Pin FAIL = 2, usuario 2, casillero 1)
    // boolean ok = dao.crearEvento(2, 2, 1);
    // System.out.println("Evento insertado: " + ok);

    // // 2) Último evento del casillero 1
    // var ultimo = dao.obtenerUltimoEventoPorCasillero(1);
    // System.out.println("Último evento casillero 1:\n" + ultimo);

    // // 3) Lista eventos casillero 1
    // var listaC = dao.obtenerEventosPorCasillero(1);
    // System.out.println("Eventos casillero 1: " + listaC.size());
    // if (!listaC.isEmpty()) System.out.println("Primero:\n" + listaC.get(0));

    // // 4) Lista eventos usuario 2
    // var listaU = dao.obtenerEventosPorUsuario(2);
    // System.out.println("Eventos usuario 2: " + listaU.size());
    // if (!listaU.isEmpty()) System.out.println("Primero:\n" + listaU.get(0));    


    // System.out.println("\n===== TEST EstadoCasilleroDAO =====");
    // var dao = new EstadoCasilleroDAO();

    // System.out.println("Locked ID: " + dao.obtenerIdPorNombre("Locked"));
    // System.out.println("Ready to Unlock ID: " + dao.obtenerIdPorNombre("Ready to Unlock"));

    // var lista = dao.listarActivos();
    // System.out.println("Total estados activos: " + lista.size());
    // if (!lista.isEmpty()) System.out.println("Primero:\n" + lista.get(0));


    // System.out.println("\n===== TEST CasilleroService.validarPin (v2) =====");

    // var service = new BusinessLogic.Services.CasilleroService();
    // var casDAO = new DataAccess.DAOs.CasilleroDAO();
    // var solDAO = new DataAccess.DAOs.SolicitudDAO();

    // int idCasillero = 4; // usa uno que no te importe bloquear
    // int idUsuario   = 2;

    // System.out.println("Antes:\n" + casDAO.obtenerPorId(idCasillero));

    // System.out.println("Try 1: " + service.validarPin(idCasillero, idUsuario, "0000"));
    // System.out.println("Try 2: " + service.validarPin(idCasillero, idUsuario, "0000"));
    // System.out.println("Try 3: " + service.validarPin(idCasillero, idUsuario, "0000"));

    // System.out.println("Después:\n" + casDAO.obtenerPorId(idCasillero));

    // var solicitudes = solDAO.listarPorCasillero(idCasillero);
    // System.out.println("Solicitudes para casillero " + idCasillero + ": " + solicitudes.size());
    // if (!solicitudes.isEmpty()) System.out.println("Última:\n" + solicitudes.get(0));


    
    // System.out.println("\n===== TEST RecuperacionService (APROBAR + RECHAZAR) =====");

    // var rs = new BusinessLogic.Services.RecuperacionService();

    // var pendientes = rs.listarPendientes();
    // System.out.println("Pendientes: " + pendientes.size());

    // if (pendientes.isEmpty()) {
    //     System.out.println("No hay solicitudes pendientes para probar.");
    //     return;
    // }

    // int idAdmin = 1;

    // // ====== 1) APROBAR primera pendiente ======
    // var solAprobar = pendientes.get(0);
    // int idSolicitudAprobar = solAprobar.getIdSolicitud();
    // int idCasilleroAprobar = solAprobar.getIdCasillero();

    // System.out.println("\n--- APROBAR ---");
    // System.out.println("Solicitud a aprobar:\n" + solAprobar);

    // Integer idToken = rs.aprobarSolicitud(idSolicitudAprobar, idAdmin);
    // System.out.println("Aprobada  Token creado ID: " + idToken);

    // var tokenActivo = rs.tokenActivoCasillero(idCasilleroAprobar);
    // System.out.println("Token activo casillero " + idCasilleroAprobar + ":\n" + tokenActivo);

    // // ====== 2) RECHAZAR segunda pendiente (si existe) ======
    // System.out.println("\n--- RECHAZAR ---");

    // if (pendientes.size() < 2) {
    //     System.out.println("Solo hay 1 pendiente. Creando otra solicitud pendiente para poder rechazar...");

    //     // Si tu CasilleroService crea solicitudes al bloquear, puedes crear manual con SolicitudDAO:
    //     var sdao = new DataAccess.DAOs.SolicitudDAO();

    //     // Crea una solicitud pendiente para el casillero 2 (ajusta si quieres otro)
    //     Integer nuevaId = sdao.crearSolicitud(2, idAdmin, 1);
    //     System.out.println("Nueva solicitud pendiente creada ID: " + nuevaId);

    //     // Recargar pendientes
    //     pendientes = rs.listarPendientes();
    //     System.out.println("Pendientes ahora: " + pendientes.size());

    //     if (pendientes.isEmpty()) {
    //         System.out.println("No pude generar una pendiente para rechazar.");
    //         return;
    //     }
    // }

    // // Elegimos una solicitud distinta a la aprobada (por si quedó aún pendiente alguna)
    // DataAccess.DTOs.SolicitudDTO solRechazar = null;
    // for (var s : pendientes) {
    //     if (s.getIdSolicitud() != null && s.getIdSolicitud() != idSolicitudAprobar) {
    //         solRechazar = s;
    //         break;
    //     }
    // }
    // if (solRechazar == null) {
    //     // fallback: toma la primera (aunque normalmente no debería pasar)
    //     solRechazar = pendientes.get(0);
    // }

    // int idSolicitudRechazar = solRechazar.getIdSolicitud();
    // System.out.println("Solicitud a rechazar:\n" + solRechazar);

    // rs.rechazarSolicitud(idSolicitudRechazar, idAdmin);
    // System.out.println("Rechazada  (idSolicitud=" + idSolicitudRechazar + ")");

    System.out.println("\n===== TEST RecuperacionService (FORZADO) =====");

    int idAdmin = 1;
    int idCasilleroAprobar = 1;
    int idCasilleroRechazar = 2;

    var rec = new BusinessLogic.Services.RecuperacionService();
    var solicitudDAO = new DataAccess.DAOs.SolicitudDAO();

    // ---------- APROBAR ----------
    System.out.println("\n--- APROBAR ---");

    var pendientes = rec.listarPendientes();
    if (pendientes == null || pendientes.isEmpty()) {
        System.out.println("No hay pendientes. Creando una pendiente para aprobar...");
        Integer nueva = solicitudDAO.crearSolicitud(idCasilleroAprobar, idAdmin, 1); // 1=Pendiente
        System.out.println("Nueva solicitud pendiente creada ID: " + nueva);
        pendientes = rec.listarPendientes();
    }

    if (pendientes == null || pendientes.isEmpty()) {
        System.out.println("Sigue sin haber pendientes. Revisa listarPendientes() y EstadoSolicitud=1.");
        return;
    }

    var solA = pendientes.get(0);
    System.out.println("Solicitud a aprobar:\n" + solA);

    Integer idToken = rec.aprobarSolicitud(solA.getIdSolicitud(), idAdmin);
    System.out.println("Aprobada ✅ Token creado ID: " + idToken);

    var tokenActivo = rec.tokenActivoCasillero(solA.getIdCasillero());
    System.out.println("Token activo casillero " + solA.getIdCasillero() + ":\n" + tokenActivo);

    // ---------- RECHAZAR ----------
    System.out.println("\n--- RECHAZAR ---");

    // Creamos una pendiente nueva para rechazar sí o sí
    Integer idSolR = solicitudDAO.crearSolicitud(idCasilleroRechazar, idAdmin, 1);
    System.out.println("Nueva solicitud pendiente creada ID: " + idSolR);

    // Volvemos a consultar pendientes y buscamos esa
    pendientes = rec.listarPendientes();
    SolicitudDTO solR = null;
    for (SolicitudDTO s : pendientes) {
        if (s.getIdSolicitud() != null && s.getIdSolicitud().intValue() == idSolR.intValue()) {
            solR = s;
            break;
        }
    }
    if (solR == null) {
        // fallback: usa la primera pendiente
        solR = pendientes.get(0);
    }

    System.out.println("Solicitud a rechazar:\n" + solR);
    rec.rechazarSolicitud(solR.getIdSolicitud(), idAdmin);
    System.out.println("Rechazada ✅ (idSolicitud=" + solR.getIdSolicitud() + ")");
    }





    
}

