package DataAccess.DAOs;

import DataAccess.DTOs.AuditoriaDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class AuditoriaDAO extends DataHelperSQLiteDAO<AuditoriaDTO> {

    public AuditoriaDAO() throws AppException {
        super(AuditoriaDTO.class, "vw_Auditoria_Admin", "idRegistroEvento");
    }

    public List<AuditoriaDTO> listarAdmin(int limit) throws AppException {
        String sql =
            "SELECT * FROM vw_Auditoria_Admin " +
            "ORDER BY Fecha DESC, idRegistroEvento DESC " +
            "LIMIT ?";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, limit);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarAdmin");
        }
    }

    public List<AuditoriaDTO> listarAdminPorCasillero(int idCasillero, int limit) throws AppException {
        String sql =
            "SELECT * FROM vw_Auditoria_Admin " +
            "WHERE idCasillero = ? " +
            "ORDER BY Fecha DESC, idRegistroEvento DESC " +
            "LIMIT ?";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCasillero);
            stmt.setInt(2, limit);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarAdminPorCasillero");
        }
    }

    public List<AuditoriaDTO> listarEstudiante(int idEstudiante, int limit) throws AppException {
        String sql =
            "SELECT * FROM vw_Auditoria_Estudiante " +
            "WHERE idEstudiante = ? " +
            "ORDER BY Fecha DESC, idRegistroEvento DESC " +
            "LIMIT ?";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idEstudiante);
            stmt.setInt(2, limit);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarEstudiante");
        }
    }
}

