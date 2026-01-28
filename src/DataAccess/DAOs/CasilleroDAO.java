package DataAccess.DAOs;

import DataAccess.DTOs.CasilleroDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class CasilleroDAO extends DataHelperSQLiteDAO<CasilleroDTO> {

    public CasilleroDAO() throws AppException {
        super(CasilleroDTO.class, "Casillero", "IdCasillero");
    }

    public CasilleroDTO obtenerPorId(int IdCasillero) throws AppException {
        String sql = "SELECT * FROM Casillero WHERE IdCasillero = ? AND Estado = 'A' LIMIT 1";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, IdCasillero);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }

    public List<CasilleroDTO> listarTodos() throws AppException {
        String sql = "SELECT * FROM Casillero WHERE Estado = 'A' ORDER BY IdCasillero ASC";
        try (var stmt = openConnection().prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarTodos");
        }
    }

    public List<CasilleroDTO> obtenerPorEstudiante(int idEstudiante) throws AppException {
        String sql = "SELECT * FROM Casillero WHERE IdEstudiante = ? AND Estado = 'A' ORDER BY IdCasillero ASC";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idEstudiante);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorEstudiante");
        }
    }

    public List<CasilleroDTO> obtenerDisponibles() throws AppException {
        String sql =
            "SELECT " +
            "  c.IdCasillero, " +
            "  c.IdEstadoCasillero, " +
            "  c.IdEstudiante, " +
            "  c.IntentosFallidos, " +
            "  c.Descripcion, " +
            "  c.Estado, " +
            "  c.FechaCreacion, " +
            "  c.FechaModificacion " +
            "FROM Casillero c " +
            "JOIN EstadoCasillero ec ON ec.IdEstadoCasillero = c.IdEstadoCasillero " +
            "WHERE c.Estado = 'A' AND ec.Nombre = 'Ready to Unlock' " +
            "ORDER BY c.IdCasillero ASC";

        try (var stmt = openConnection().prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerDisponibles");
        }
    }


    public boolean incrementarIntentos(int IdCasillero) throws AppException {
        String sql =
            "UPDATE Casillero " +
            "SET IntentosFallidos = IntentosFallidos + 1, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE IdCasillero = ? AND Estado = 'A'";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, IdCasillero);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "incrementarIntentos");
        }
    }

    public boolean resetIntentos(int IdCasillero) throws AppException {
        String sql =
            "UPDATE Casillero " +
            "SET IntentosFallidos = 0, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE IdCasillero = ? AND Estado = 'A'";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, IdCasillero);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "resetIntentos");
        }
    }

    public boolean actualizarEstado(int IdCasillero, int IdEstadoCasillero) throws AppException {
        String sql =
            "UPDATE Casillero " +
            "SET IdEstadoCasillero = ?, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE IdCasillero = ? AND Estado = 'A'";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, IdEstadoCasillero);
            stmt.setInt(2, IdCasillero);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "actualizarEstado");
        }
    }

    public boolean asignarEstudiante(int IdCasillero, Integer idEstudiante) throws AppException {
        String sql =
            "UPDATE Casillero " +
            "SET IdEstudiante = ?, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE IdCasillero = ? AND Estado = 'A'";
        try (var stmt = openConnection().prepareStatement(sql)) {
            if (idEstudiante == null) stmt.setNull(1, java.sql.Types.INTEGER);
            else stmt.setInt(1, idEstudiante);

            stmt.setInt(2, IdCasillero);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "asignarEstudiante");
        }
    }
}
