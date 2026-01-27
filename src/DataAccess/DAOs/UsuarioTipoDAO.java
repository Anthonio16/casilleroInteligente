package DataAccess.DAOs;

import DataAccess.DTOs.UsuarioTipoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class UsuarioTipoDAO extends DataHelperSQLiteDAO<UsuarioTipoDTO> {

    public UsuarioTipoDAO() throws AppException {
        super(UsuarioTipoDTO.class, "UsuarioTipo", "IdUsuarioTipo");
    }

    public UsuarioTipoDTO obtenerPorId(int idUsuarioTipo) throws AppException {
        String query =
            "SELECT " +
            "  idUsuarioTipo     AS IdUsuarioTipo, " +
            "  Nombre            AS Nombre, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM UsuarioTipo " +
            "WHERE idUsuarioTipo = ? " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idUsuarioTipo);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }

    public UsuarioTipoDTO obtenerPorNombre(String nombre) throws AppException {
        String query =
            "SELECT " +
            "  idUsuarioTipo     AS IdUsuarioTipo, " +
            "  Nombre            AS Nombre, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM UsuarioTipo " +
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

    public Integer obtenerIdPorNombre(String nombre) throws AppException {
        UsuarioTipoDTO dto = obtenerPorNombre(nombre);
        return (dto == null) ? null : dto.getIdUsuarioTipo();
    }


    public List<UsuarioTipoDTO> listarActivos() throws AppException {
        String query =
            "SELECT " +
            "  idUsuarioTipo     AS IdUsuarioTipo, " +
            "  Nombre            AS Nombre, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM UsuarioTipo " +
            "WHERE Estado = 'Activo' " +
            "ORDER BY idUsuarioTipo ASC";

        try (var stmt = openConnection().prepareStatement(query);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarActivos");
        }
    }

    public List<UsuarioTipoDTO> listarTodos() throws AppException {
        String query =
            "SELECT " +
            "  idUsuarioTipo     AS IdUsuarioTipo, " +
            "  Nombre            AS Nombre, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM UsuarioTipo " +
            "ORDER BY idUsuarioTipo ASC";

        try (var stmt = openConnection().prepareStatement(query);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarTodos");
        }
    }
}

