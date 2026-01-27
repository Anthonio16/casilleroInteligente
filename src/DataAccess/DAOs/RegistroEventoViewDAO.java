package DataAccess.DAOs;

import DataAccess.DTOs.RegistroEventoViewDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

import java.util.List;

public class RegistroEventoViewDAO extends DataHelperSQLiteDAO<RegistroEventoViewDTO> {

    public RegistroEventoViewDAO() throws AppException {
        super(RegistroEventoViewDTO.class, "RegistroEvento", "idRegistroEvento");
    }


    public List<RegistroEventoViewDTO> listarConNombre(int idCasillero) throws AppException {
        String sql =
            "SELECT " +
            "  re.idRegistroEvento, " +
            "  re.idTipoEvento, " +
            "  te.Nombre AS NombreTipoEvento, " +
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

    public RegistroEventoViewDTO ultimoEventoConNombre(int idCasillero) throws AppException {
        String sql =
            "SELECT " +
            "  re.idRegistroEvento, " +
            "  re.idTipoEvento, " +
            "  te.Nombre AS NombreTipoEvento, " +
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
}

