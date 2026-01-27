package DataAccess.DAOs;

import DataAccess.DTOs.EstadoCasilleroDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class EstadoCasilleroDAO extends DataHelperSQLiteDAO<EstadoCasilleroDTO> {

    public EstadoCasilleroDAO() throws AppException {
        super(EstadoCasilleroDTO.class, "EstadoCasillero", "idEstadoCasillero");
    }

    public EstadoCasilleroDTO obtenerPorNombre(String nombre) throws AppException {
        String sql =
            "SELECT * FROM EstadoCasillero " +
            "WHERE Nombre = ? AND Estado = 'A' " +
            "LIMIT 1";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorNombre");
        }
    }

    public Integer obtenerIdPorNombre(String nombre) throws AppException {
        EstadoCasilleroDTO dto = obtenerPorNombre(nombre);
        return dto == null ? null : dto.getIdEstadoCasillero();
    }

    public List<EstadoCasilleroDTO> listarActivos() throws AppException {
        String sql =
            "SELECT * FROM EstadoCasillero " +
            "WHERE Estado = 'A' " +
            "ORDER BY idEstadoCasillero ASC";
        try (var stmt = openConnection().prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarActivos");
        }
    }
}
