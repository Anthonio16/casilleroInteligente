package DataAccess.DAOs;

import DataAccess.DTOs.RegistroEventoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class RegistroEventoDAO extends DataHelperSQLiteDAO<RegistroEventoDTO> {

    public RegistroEventoDAO() throws AppException {
        super(RegistroEventoDTO.class, "RegistroEvento", "idRegistroEvento");
    }

    // Inserta un evento (fechas y estado las maneja SQLite por DEFAULT)
    public boolean crearEvento(int idTipoEvento, int idUsuario, int idCasillero) throws AppException {
        String sql = "INSERT INTO RegistroEvento (idTipoEvento, idUsuario, idCasillero) VALUES (?, ?, ?)";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idTipoEvento);
            stmt.setInt(2, idUsuario);
            stmt.setInt(3, idCasillero);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "crearEvento");
        }
    }

    // Eventos por casillero (más reciente primero)
    public List<RegistroEventoDTO> obtenerEventosPorCasillero(int idCasillero) throws AppException {
        String sql =
            "SELECT * FROM RegistroEvento " +
            "WHERE idCasillero = ? AND Estado = 'A' " +
            "ORDER BY FechaModificacion DESC, idRegistroEvento DESC";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerEventosPorCasillero");
        }
    }

    // Eventos por usuario (más reciente primero)
    public List<RegistroEventoDTO> obtenerEventosPorUsuario(int idUsuario) throws AppException {
        String sql =
            "SELECT * FROM RegistroEvento " +
            "WHERE idUsuario = ? AND Estado = 'A' " +
            "ORDER BY FechaModificacion DESC, idRegistroEvento DESC";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerEventosPorUsuario");
        }
    }

    // Último evento de un casillero
    public RegistroEventoDTO obtenerUltimoEventoPorCasillero(int idCasillero) throws AppException {
        String sql =
            "SELECT * FROM RegistroEvento " +
            "WHERE idCasillero = ? AND Estado = 'A' " +
            "ORDER BY FechaModificacion DESC, idRegistroEvento DESC " +
            "LIMIT 1";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerUltimoEventoPorCasillero");
        }
    }
}


