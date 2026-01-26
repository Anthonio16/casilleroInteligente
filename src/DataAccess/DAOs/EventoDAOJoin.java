// =========================================================
// DataAccess/DAOs/EventoDAOJoin.java
// =========================================================
package DataAccess.DAOs;

import java.util.List;

import DataAccess.DTOs.EventoConNombreDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class EventoDAOJoin extends DataHelperSQLiteDAO<EventoConNombreDTO> {

    public EventoDAOJoin() throws AppException {
        // OJO: la "tabla" aquí no la usaremos para CRUD genérico.
        // Solo usamos el mapper del helper con queries personalizadas.
        super(EventoConNombreDTO.class, "RegistroEvento", "idRegistroEvento");
    }

    public EventoConNombreDTO ultimoEventoConNombre(int idCasillero) throws AppException {
        String sql =
            "SELECT " +
            "  re.idRegistroEvento, " +
            "  re.idTipoEvento, " +
            "  te.Nombre AS NombreEvento, " +
            "  re.idUsuario, " +
            "  re.idCasillero, " +
            "  re.Estado, " +
            "  re.FechaCreacion, " +
            "  re.FechaModificacion " +
            "FROM RegistroEvento re " +
            "JOIN TipoEvento te ON te.idTipoEvento = re.idTipoEvento " +
            "WHERE re.idCasillero = ? AND re.Estado = 'A' " +
            "ORDER BY re.FechaModificacion DESC, re.idRegistroEvento DESC " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "ultimoEventoConNombre");
        }
    }

    public List<EventoConNombreDTO> listarConNombre(int idCasillero) throws AppException {
        String sql =
            "SELECT " +
            "  re.idRegistroEvento, " +
            "  re.idTipoEvento, " +
            "  te.Nombre AS NombreEvento, " +
            "  re.idUsuario, " +
            "  re.idCasillero, " +
            "  re.Estado, " +
            "  re.FechaCreacion, " +
            "  re.FechaModificacion " +
            "FROM RegistroEvento re " +
            "JOIN TipoEvento te ON te.idTipoEvento = re.idTipoEvento " +
            "WHERE re.idCasillero = ? AND re.Estado = 'A' " +
            "ORDER BY re.FechaModificacion DESC, re.idRegistroEvento DESC";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarConNombre");
        }
    }
}

