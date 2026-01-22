package DataAccess.DAOs;

import DataAccess.DTOs.EstadoSolicitudDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class EstadoSolicitudDAO extends DataHelperSQLiteDAO<EstadoSolicitudDTO> {

    public EstadoSolicitudDAO() throws AppException {
        super(EstadoSolicitudDTO.class, "EstadoSolicitud", "IdEstadoSolicitud");
    }

    public EstadoSolicitudDTO obtenerPorId(int idEstadoSolicitud) throws AppException {
        String query =
            "SELECT " +
            "  idEstadoSolicitud AS IdEstadoSolicitud, " +
            "  Nombre            AS Nombre, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM EstadoSolicitud " +
            "WHERE idEstadoSolicitud = ? AND Estado = 'A' " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idEstadoSolicitud);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }

    public EstadoSolicitudDTO obtenerPorNombre(String nombre) throws AppException {
        String query =
            "SELECT " +
            "  idEstadoSolicitud AS IdEstadoSolicitud, " +
            "  Nombre            AS Nombre, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM EstadoSolicitud " +
            "WHERE Nombre = ? AND Estado = 'A' " +
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
        EstadoSolicitudDTO dto = obtenerPorNombre(nombre);
        return dto == null ? null : dto.getIdEstadoSolicitud();
    }

    public List<EstadoSolicitudDTO> listarTodosActivos() throws AppException {
        String query =
            "SELECT " +
            "  idEstadoSolicitud AS IdEstadoSolicitud, " +
            "  Nombre            AS Nombre, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM EstadoSolicitud " +
            "WHERE Estado = 'A' " +
            "ORDER BY idEstadoSolicitud ASC";

        try (var stmt = openConnection().prepareStatement(query);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarTodosActivos");
        }
    }
}


