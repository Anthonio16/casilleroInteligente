package DataAccess.DAOs;

import java.util.List;

import DataAccess.DTOs.EstadoCasilleroDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class EstadoCasilleroDAO extends DataHelperSQLiteDAO<EstadoCasilleroDTO> {

    public EstadoCasilleroDAO() throws AppException {
        super(EstadoCasilleroDTO.class, "EstadoCasillero", "IdEstadoCasillero");
    }

    /**
     * Obtiene el ID del estado por nombre (ej: "Locked", "Ready to Unlock")
     */
    public Integer obtenerIdPorNombre(String nombre) throws AppException {
        String query =
            "SELECT " +
            "  idEstadoCasillero AS IdEstadoCasillero, " +
            "  Nombre            AS Nombre, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM EstadoCasillero " +
            "WHERE Nombre = ? " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setString(1, nombre);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EstadoCasilleroDTO dto = mapResultSetToEntity(rs);
                    return dto.getIdEstadoCasillero();
                }
                return null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerIdPorNombre");
        }
    }

    /**
     * Lista todos los estados de casillero
     */
    public List<EstadoCasilleroDTO> listarTodos() throws AppException {
        String query =
            "SELECT " +
            "  idEstadoCasillero AS IdEstadoCasillero, " +
            "  Nombre            AS Nombre, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM EstadoCasillero " +
            "ORDER BY idEstadoCasillero ASC";

        try (var stmt = openConnection().prepareStatement(query);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarTodos");
        }
    }
}

