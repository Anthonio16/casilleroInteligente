package DataAccess.DAOs;

import DataAccess.DTOs.SolicitudDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class SolicitudDAO extends DataHelperSQLiteDAO<SolicitudDTO> {

    public SolicitudDAO() throws AppException {
        super(SolicitudDTO.class, "Solicitud", "IdSolicitud");
    }

    // ✅ MÉTODO LIMPIO (3 parámetros)
    public Integer crearSolicitud(int idCasillero, int idAdmin, int idEstadoSolicitud) throws AppException {
        String query =
            "INSERT INTO Solicitud (idCasillero, idAdmin, idEstadoSolicitud, Estado) " +
            "VALUES (?, ?, ?, 'A')";

        try (var stmt = openConnection().prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idCasillero);
            stmt.setInt(2, idAdmin);
            stmt.setInt(3, idEstadoSolicitud);
            stmt.executeUpdate();

            try (var keys = stmt.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "crearSolicitud");
        }
    }

    public SolicitudDTO obtenerPorId(int idSolicitud) throws AppException {
        String query =
            "SELECT " +
            "  idSolicitud       AS IdSolicitud, " +
            "  idCasillero       AS IdCasillero, " +
            "  idAdmin           AS IdAdmin, " +
            "  idEstadoSolicitud AS IdEstadoSolicitud, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM Solicitud " +
            "WHERE idSolicitud = ? AND Estado = 'A' " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idSolicitud);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }

    public List<SolicitudDTO> listarTodasActivas() throws AppException {
        String query =
            "SELECT " +
            "  idSolicitud       AS IdSolicitud, " +
            "  idCasillero       AS IdCasillero, " +
            "  idAdmin           AS IdAdmin, " +
            "  idEstadoSolicitud AS IdEstadoSolicitud, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM Solicitud " +
            "WHERE Estado = 'A' " +
            "ORDER BY idSolicitud DESC";

        try (var stmt = openConnection().prepareStatement(query);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarTodasActivas");
        }
    }

    public List<SolicitudDTO> listarPorCasillero(int idCasillero) throws AppException {
        String query =
            "SELECT " +
            "  idSolicitud       AS IdSolicitud, " +
            "  idCasillero       AS IdCasillero, " +
            "  idAdmin           AS IdAdmin, " +
            "  idEstadoSolicitud AS IdEstadoSolicitud, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM Solicitud " +
            "WHERE idCasillero = ? AND Estado = 'A' " +
            "ORDER BY idSolicitud DESC";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarPorCasillero");
        }
    }
}
