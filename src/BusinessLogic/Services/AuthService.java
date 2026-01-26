package BusinessLogic.Services;

import DataAccess.DAOs.UsuarioDAO;
import DataAccess.DTOs.UsuarioDTO;
import Infrastructure.AppException;

public class AuthService {

    private final UsuarioDAO usuarioDAO;

    public AuthService() throws AppException {
        this.usuarioDAO = new UsuarioDAO();
    }

    public UsuarioDTO login(String nombre, String clave) throws AppException {
        try {
            // TRIM para evitar espacios accidentales
            String n = (nombre == null) ? "" : nombre.trim();
            String c = (clave  == null) ? "" : clave.trim();

            UsuarioDTO user = usuarioDAO.login(n, c); // usa SQL directo
            if (user == null) {
                throw new AppException("Usuario o clave incorrectos", null, getClass(), "login");
            }
            return user;

        } catch (AppException ae) {
            throw ae;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "login");
        }
    }
}
