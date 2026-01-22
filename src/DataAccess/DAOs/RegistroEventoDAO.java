package DataAccess.DAOs;

import DataAccess.DTOs.RegistroEventoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class RegistroEventoDAO extends DataHelperSQLiteDAO<RegistroEventoDTO> {

    public RegistroEventoDAO() throws AppException {
        super(RegistroEventoDTO.class, "RegistroEvento", "IdRegistroEvento");
    }

    /**
     * Inserta un evento. NO insertamos fechas porque en SQLite ya tienen DEFAULT.
     */
    public void crearEvento(int idTipoEvento, int idUsuario, int idCasillero) throws AppException {
        String query = "INSERT INTO RegistroEvento (idTipoEvento, idUsuario, idCasillero) VALUES (?, ?, ?)";
        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idTipoEvento);
            stmt.setInt(2, idUsuario);
            stmt.setInt(3, idCasillero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "crearEvento");
        }
    }

    /**
     * Devuelve eventos por casillero (más reciente primero).
     * Usamos ALIAS para que el mapper encuentre los campos del DTO.
     */
    public List<RegistroEventoDTO> obtenerEventosCasillero(int idCasillero) throws AppException {
        String query =
            "SELECT " +
            "  idRegistroEvento  AS IdRegistroEvento, " +
            "  idTipoEvento      AS IdTipoEvento, " +
            "  idUsuario         AS IdUsuario, " +
            "  idCasillero       AS IdCasillero, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM RegistroEvento " +
            "WHERE idCasillero = ? " +
            "ORDER BY FechaModificacion DESC, idRegistroEvento DESC";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerEventosCasillero");
        }
    }

    /**
     * Devuelve eventos por usuario (más reciente primero).
     */
    public List<RegistroEventoDTO> obtenerEventosUsuario(int idUsuario) throws AppException {
        String query =
            "SELECT " +
            "  idRegistroEvento  AS IdRegistroEvento, " +
            "  idTipoEvento      AS IdTipoEvento, " +
            "  idUsuario         AS IdUsuario, " +
            "  idCasillero       AS IdCasillero, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM RegistroEvento " +
            "WHERE idUsuario = ? " +
            "ORDER BY FechaModificacion DESC, idRegistroEvento DESC";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerEventosUsuario");
        }
    }

    /**
     * Devuelve el último evento registrado para un casillero.
     */
    public RegistroEventoDTO obtenerUltimoEventoCasillero(int idCasillero) throws AppException {
        String query =
            "SELECT " +
            "  idRegistroEvento  AS IdRegistroEvento, " +
            "  idTipoEvento      AS IdTipoEvento, " +
            "  idUsuario         AS IdUsuario, " +
            "  idCasillero       AS IdCasillero, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM RegistroEvento " +
            "WHERE idCasillero = ? " +
            "ORDER BY FechaModificacion DESC, idRegistroEvento DESC " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerUltimoEventoCasillero");
        }
    }
}

