package DataAccess.DAOs;

import DataAccess.DTOs.RegistroEventoNombreDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class EventoDAOJoin extends DataHelperSQLiteDAO<RegistroEventoNombreDTO> {

    public EventoDAOJoin() throws AppException {
        super(RegistroEventoNombreDTO.class, "RegistroEvento", "idRegistroEvento");
    }

    public RegistroEventoNombreDTO ultimoEventoConNombre(int idCasillero) throws AppException {
        String sql =
            "SELECT re.idRegistroEvento, re.idTipoEvento, te.Nombre AS Nombre, " +
            "       re.idUsuario, re.idCasillero, re.Estado, re.FechaCreacion, re.FechaModificacion " +
            "FROM RegistroEvento re " +
            "JOIN TipoEvento te ON te.idTipoEvento = re.idTipoEvento " +
            "WHERE re.idCasillero = ? AND re.Estado='A' " +
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

    public List<RegistroEventoNombreDTO> listarConNombre(int idCasillero) throws AppException {
        String sql =
            "SELECT re.idRegistroEvento, re.idTipoEvento, te.Nombre AS Nombre, " +
            "       re.idUsuario, re.idCasillero, re.Estado, re.FechaCreacion, re.FechaModificacion " +
            "FROM RegistroEvento re " +
            "JOIN TipoEvento te ON te.idTipoEvento = re.idTipoEvento " +
            "WHERE re.idCasillero = ? AND re.Estado='A' " +
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


