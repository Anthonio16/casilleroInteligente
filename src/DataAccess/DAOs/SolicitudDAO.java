package DataAccess.DAOs;

import DataAccess.DTOs.SolicitudDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class SolicitudDAO extends DataHelperSQLiteDAO<SolicitudDTO> {

    public SolicitudDAO() throws AppException {
        super(SolicitudDTO.class, "Solicitud", "IdSolicitud");
    }

    public Integer crearSolicitud(int IdCasillero, int IdEstudianteSolicitante, Integer IdAdmin, int IdEstadoSolicitud) throws AppException {
        String sql =
            "INSERT INTO Solicitud (IdCasillero, IdEstudianteSolicitante, IdAdmin, IdEstadoSolicitud, Estado) " +
            "VALUES (?, ?, ?, ?, 'A')";

        try (var stmt = openConnection().prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, IdCasillero);
            stmt.setInt(2, IdEstudianteSolicitante);
            stmt.setObject(3, IdAdmin); // puede ser NULL
            stmt.setInt(4, IdEstadoSolicitud);

            stmt.executeUpdate();
            try (var keys = stmt.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "crearSolicitud");
        }
    }


    public SolicitudDTO obtenerPorId(int idSolicitud) throws AppException {
        String sql = "SELECT * FROM Solicitud WHERE IdSolicitud = ? AND Estado='A' LIMIT 1";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idSolicitud);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }


    public List<SolicitudDTO> listarPendientes() throws AppException {
        String sql =
            "SELECT * FROM Solicitud " +
            "WHERE Estado='A' AND IdEstadoSolicitud = 1 " +
            "ORDER BY idSolicitud DESC";

        try (var stmt = openConnection().prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarPendientes");
        }
    }

    public boolean actualizarEstadoSolicitud(int idSolicitud, int IdEstadoSolicitud) throws AppException {
        String sql =
            "UPDATE Solicitud " +
            "SET IdEstadoSolicitud = ?, FechaModificacion = datetime('now','localtime') " +
            "WHERE IdSolicitud = ? AND Estado = 'A'";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, IdEstadoSolicitud);
            stmt.setInt(2, idSolicitud);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "actualizarEstadoSolicitud");
        }
    }

    public List<SolicitudDTO> listarPorCasillero(int IdCasillero) throws AppException {
    String sql =
        "SELECT * FROM Solicitud " +
        "WHERE Estado='A' AND IdCasillero = ? " +
        "ORDER BY IdSolicitud DESC";
    try (var stmt = openConnection().prepareStatement(sql)) {
        stmt.setInt(1, IdCasillero);
        try (var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        }
    } catch (Exception e) {
        throw new AppException(null, e, getClass(), "listarPorCasillero");
    }
}

    public boolean atenderSolicitud(int idSolicitud, int IdAdmin, int IdEstadoSolicitud) throws AppException {
        String sql =
            "UPDATE Solicitud " +
            "SET IdAdmin = ?, IdEstadoSolicitud = ?, FechaModificacion = datetime('now','localtime') " +
            "WHERE IdSolicitud = ? AND Estado = 'A'";
    
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, IdAdmin);
            stmt.setInt(2, IdEstadoSolicitud);
            stmt.setInt(3, idSolicitud);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "atenderSolicitud");
        }
    }


}


