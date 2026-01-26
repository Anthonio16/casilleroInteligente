package DataAccess.DAOs;

import DataAccess.DTOs.CasilleroDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class CasilleroDAO extends DataHelperSQLiteDAO<CasilleroDTO> {

    public CasilleroDAO() throws AppException {
        super(CasilleroDTO.class, "Casillero", "idCasillero");
    }

    // =========================
    // READS
    // =========================

    public CasilleroDTO obtenerPorId(int idCasillero) throws AppException {
        String sql = "SELECT * FROM Casillero WHERE idCasillero = ? AND Estado = 'A' LIMIT 1";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }

    public List<CasilleroDTO> listarTodos() throws AppException {
        String sql = "SELECT * FROM Casillero WHERE Estado = 'A' ORDER BY idCasillero ASC";
        try (var stmt = openConnection().prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarTodos");
        }
    }

    public List<CasilleroDTO> obtenerPorEstudiante(int idEstudiante) throws AppException {
        String sql = "SELECT * FROM Casillero WHERE idEstudiante = ? AND Estado = 'A' ORDER BY idCasillero ASC";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idEstudiante);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorEstudiante");
        }
    }

    /**
     * Casilleros disponibles filtrando por el catálogo EstadoCasillero.Nombre.
     * Importante: aquí NO usamos SELECT * porque hay JOIN.
     * Por eso seleccionamos solo columnas de Casillero (con sus nombres reales).
     */
    public List<CasilleroDTO> obtenerDisponibles() throws AppException {
        String sql =
            "SELECT " +
            "  c.idCasillero, " +
            "  c.idEstadoCasillero, " +
            "  c.idEstudiante, " +
            "  c.IntentosFallidos, " +
            "  c.Descripcion, " +
            "  c.Estado, " +
            "  c.FechaCreacion, " +
            "  c.FechaModificacion " +
            "FROM Casillero c " +
            "JOIN EstadoCasillero ec ON ec.idEstadoCasillero = c.idEstadoCasillero " +
            "WHERE c.Estado = 'A' AND ec.Nombre = 'Ready to Unlock' " +
            "ORDER BY c.idCasillero ASC";

        try (var stmt = openConnection().prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerDisponibles");
        }
    }

    // =========================
    // UPDATES (negocio)
    // =========================

    public boolean incrementarIntentos(int idCasillero) throws AppException {
        String sql =
            "UPDATE Casillero " +
            "SET IntentosFallidos = IntentosFallidos + 1, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE idCasillero = ? AND Estado = 'A'";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCasillero);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "incrementarIntentos");
        }
    }

    public boolean resetIntentos(int idCasillero) throws AppException {
        String sql =
            "UPDATE Casillero " +
            "SET IntentosFallidos = 0, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE idCasillero = ? AND Estado = 'A'";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCasillero);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "resetIntentos");
        }
    }

    public boolean actualizarEstado(int idCasillero, int idEstadoCasillero) throws AppException {
        String sql =
            "UPDATE Casillero " +
            "SET idEstadoCasillero = ?, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE idCasillero = ? AND Estado = 'A'";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idEstadoCasillero);
            stmt.setInt(2, idCasillero);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "actualizarEstado");
        }
    }

    public boolean asignarEstudiante(int idCasillero, Integer idEstudiante) throws AppException {
        String sql =
            "UPDATE Casillero " +
            "SET idEstudiante = ?, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE idCasillero = ? AND Estado = 'A'";
        try (var stmt = openConnection().prepareStatement(sql)) {
            if (idEstudiante == null) stmt.setNull(1, java.sql.Types.INTEGER);
            else stmt.setInt(1, idEstudiante);

            stmt.setInt(2, idCasillero);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "asignarEstudiante");
        }
    }
}
