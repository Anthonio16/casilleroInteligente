package DataAccess.DAOs;

import DataAccess.DTOs.UsuarioDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class UsuarioDAO extends DataHelperSQLiteDAO<UsuarioDTO> {

    public UsuarioDAO() throws AppException {
        super(UsuarioDTO.class, "Usuario", "idUsuario");
    }

    public UsuarioDTO obtenerPorNombre(String nombre) throws AppException {
        String sql = "SELECT * FROM Usuario WHERE Nombre = ? AND Estado='A' LIMIT 1";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorNombre");
        }
    }

    public UsuarioDTO login(String nombre, String clave) throws AppException {
        String sql = "SELECT * FROM Usuario WHERE Nombre = ? AND Clave = ? AND Estado='A' LIMIT 1";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, clave);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "login");
        }
    }

}
