package DataAccess.DAOs;

import DataAccess.DTOs.SolicitudDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class SolicitudDAO extends DataHelperSQLiteDAO<SolicitudDTO> {

    public SolicitudDAO() throws AppException {
        super(SolicitudDTO.class, "Solicitud", "idSolicitud");
    }

    public Integer crearSolicitud(int idCasillero, int idEstudianteSolicitante, Integer idAdmin, int idEstadoSolicitud) throws AppException {
        String sql =
            "INSERT INTO Solicitud (idCasillero, idEstudianteSolicitante, idAdmin, idEstadoSolicitud, Estado) " +
            "VALUES (?, ?, ?, ?, 'A')";

        try (var stmt = openConnection().prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idCasillero);
            stmt.setInt(2, idEstudianteSolicitante);
            stmt.setObject(3, idAdmin); // puede ser NULL
            stmt.setInt(4, idEstadoSolicitud);

            stmt.executeUpdate();
            try (var keys = stmt.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "crearSolicitud");
        }
    }


    public SolicitudDTO obtenerPorId(int idSolicitud) throws AppException {
        String sql = "SELECT * FROM Solicitud WHERE idSolicitud = ? AND Estado='A' LIMIT 1";
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
            "WHERE Estado='A' AND idEstadoSolicitud = 1 " +
            "ORDER BY idSolicitud DESC";

        try (var stmt = openConnection().prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarPendientes");
        }
    }

    public boolean actualizarEstadoSolicitud(int idSolicitud, int idEstadoSolicitud) throws AppException {
        String sql =
            "UPDATE Solicitud " +
            "SET idEstadoSolicitud = ?, FechaModificacion = datetime('now','localtime') " +
            "WHERE idSolicitud = ? AND Estado = 'A'";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idEstadoSolicitud);
            stmt.setInt(2, idSolicitud);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "actualizarEstadoSolicitud");
        }
    }

    public List<SolicitudDTO> listarPorCasillero(int idCasillero) throws AppException {
    String sql =
        "SELECT * FROM Solicitud " +
        "WHERE Estado='A' AND idCasillero = ? " +
        "ORDER BY idSolicitud DESC";
    try (var stmt = openConnection().prepareStatement(sql)) {
        stmt.setInt(1, idCasillero);
        try (var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        }
    } catch (Exception e) {
        throw new AppException(null, e, getClass(), "listarPorCasillero");
    }
}

    public boolean atenderSolicitud(int idSolicitud, int idAdmin, int idEstadoSolicitud) throws AppException {
        String sql =
            "UPDATE Solicitud " +
            "SET idAdmin = ?, idEstadoSolicitud = ?, FechaModificacion = datetime('now','localtime') " +
            "WHERE idSolicitud = ? AND Estado = 'A'";
    
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idAdmin);
            stmt.setInt(2, idEstadoSolicitud);
            stmt.setInt(3, idSolicitud);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "atenderSolicitud");
        }
    }


}


