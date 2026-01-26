package DataAccess.DAOs;

import DataAccess.DTOs.EstadoSolicitudDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class EstadoSolicitudDAO extends DataHelperSQLiteDAO<EstadoSolicitudDTO> {

    public EstadoSolicitudDAO() throws AppException {
        super(EstadoSolicitudDTO.class, "EstadoSolicitud", "idEstadoSolicitud");
    }

    public EstadoSolicitudDTO obtenerPorId(int id) throws AppException {
        String sql = "SELECT * FROM EstadoSolicitud WHERE idEstadoSolicitud = ? AND Estado='A' LIMIT 1";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }

    public EstadoSolicitudDTO obtenerPorNombre(String nombre) throws AppException {
        String sql = "SELECT * FROM EstadoSolicitud WHERE Nombre = ? AND Estado='A' LIMIT 1";
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
        EstadoSolicitudDTO dto = obtenerPorNombre(nombre);
        return (dto == null) ? null : dto.getIdEstadoSolicitud();
    }

    public List<EstadoSolicitudDTO> listarTodos() throws AppException {
        String sql = "SELECT * FROM EstadoSolicitud WHERE Estado = 'A' ORDER BY idEstadoSolicitud ASC";
        try (var stmt = openConnection().prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarTodos");
        }
    }
}




