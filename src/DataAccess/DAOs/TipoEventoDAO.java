package DataAccess.DAOs;

import DataAccess.DTOs.TipoEventoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class TipoEventoDAO extends DataHelperSQLiteDAO<TipoEventoDTO> {

    public TipoEventoDAO() throws AppException {
        super(TipoEventoDTO.class, "TipoEvento", "IdTipoEvento");
    }

    /**
     * Busca un tipo de evento por su Nombre (ej: "Pin OK", "Pin FAIL"...)
     */
    public TipoEventoDTO findByName(String nombreEvento) throws AppException {
        String query =
            "SELECT " +
            "  idTipoEvento      AS IdTipoEvento, " +
            "  Nombre            AS Nombre, " +
            "  Descripcion       AS Descripcion, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM TipoEvento " +
            "WHERE Nombre = ? " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setString(1, nombreEvento);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "findByName");
        }
    }

    /**
     * Devuelve el ID del tipo de evento por Nombre (más cómodo para BusinessLogic)
     */
    public Integer findIdByName(String nombreEvento) throws AppException {
        TipoEventoDTO dto = findByName(nombreEvento);
        return (dto == null) ? null : dto.getIdTipoEvento();
    }

    /**
     * Lista todos los tipos de evento (NO usamos readAll del helper porque TipoEvento no tiene Estado)
     */
    public List<TipoEventoDTO> findAll() throws AppException {
        String query =
            "SELECT " +
            "  idTipoEvento      AS IdTipoEvento, " +
            "  Nombre            AS Nombre, " +
            "  Descripcion       AS Descripcion, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM TipoEvento " +
            "ORDER BY idTipoEvento ASC";

        try (var stmt = openConnection().prepareStatement(query);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "findAll");
        }
    }
}
