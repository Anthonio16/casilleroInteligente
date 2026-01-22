package DataAccess.DAOs;

import DataAccess.DTOs.UsuarioDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class UsuarioDAO extends DataHelperSQLiteDAO<UsuarioDTO> {

    public UsuarioDAO() throws AppException {
        super(UsuarioDTO.class, "Usuario", "IdUsuario");
    }

    public UsuarioDTO obtenerPorId(int idUsuario) throws AppException {
        String query =
            "SELECT " +
            "  idUsuario         AS IdUsuario, " +
            "  idUsuarioTipo     AS IdUsuarioTipo, " +
            "  Nombre            AS Nombre, " +
            "  Clave             AS Clave, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM Usuario " +
            "WHERE idUsuario = ? " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }

    public UsuarioDTO obtenerPorNombre(String nombre) throws AppException {
        String query =
            "SELECT " +
            "  idUsuario         AS IdUsuario, " +
            "  idUsuarioTipo     AS IdUsuarioTipo, " +
            "  Nombre            AS Nombre, " +
            "  Clave             AS Clave, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM Usuario " +
            "WHERE Nombre = ? " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorNombre");
        }
    }

    public List<UsuarioDTO> listarActivos() throws AppException {
        String query =
            "SELECT " +
            "  idUsuario         AS IdUsuario, " +
            "  idUsuarioTipo     AS IdUsuarioTipo, " +
            "  Nombre            AS Nombre, " +
            "  Clave             AS Clave, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM Usuario " +
            "WHERE Estado = 'Activo' " +
            "ORDER BY idUsuario ASC";

        try (var stmt = openConnection().prepareStatement(query);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarActivos");
        }
    }

    /**
     * Login simple: busca un usuario activo por Nombre + Clave.
     * (Luego pueden hashear la clave; por ahora sirve para pruebas.)
     */
    public UsuarioDTO login(String nombre, String clave) throws AppException {
        String query =
            "SELECT " +
            "  idUsuario         AS IdUsuario, " +
            "  idUsuarioTipo     AS IdUsuarioTipo, " +
            "  Nombre            AS Nombre, " +
            "  Clave             AS Clave, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM Usuario " +
            "WHERE Nombre = ? AND Clave = ? AND Estado = 'Activo' " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
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
