package Backend;

import BusinessLogic.Services.CasilleroService;
import Infrastructure.AppException;

/**
 * Puente opcional para integrar mensajes del dispositivo (Arduino/ESP/Serial/MQTT/etc.)
 * con la lógica de negocio. No es obligatorio usarlo, pero evita que Backend/ quede vacío.
 *
 * Formato de ejemplo:
 *   TOKEN;idCasillero;idUsuario;tokenIngresado
 *   PIN;idCasillero;idUsuario;pinIngresado
 */
public class DeviceBridge {

    private final CasilleroService casilleroService;

    public DeviceBridge() throws AppException {
        this.casilleroService = new CasilleroService();
    }

    public String onMessage(String msg) throws AppException {
        if (msg == null) return "ERR:MSG_NULL";
        String[] p = msg.trim().split(";");
        if (p.length == 0) return "ERR:MSG_EMPTY";

        String cmd = p[0].trim().toUpperCase();

        switch (cmd) {
            case "TOKEN": {
                if (p.length < 4) return "ERR:TOKEN_FORMAT";
                int idCasillero = Integer.parseInt(p[1]);
                int idUsuario   = Integer.parseInt(p[2]);
                String token    = p[3];
                boolean ok = casilleroService.validarToken(idCasillero, idUsuario, token);
                return ok ? "OK" : "FAIL";
            }
            case "PIN": {
                if (p.length < 4) return "ERR:PIN_FORMAT";
                int idCasillero = Integer.parseInt(p[1]);
                int idUsuario   = Integer.parseInt(p[2]);
                String pin      = p[3];
                CasilleroService.ResultadoValidacionPin r =
                        casilleroService.validarPin(idCasillero, idUsuario, pin);
                return r.name();
            }
            default:
                return "ERR:CMD_UNKNOWN";
        }
    }
}
