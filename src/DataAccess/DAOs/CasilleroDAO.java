package DataAccess.DAOs;

import java.util.List;

import DataAccess.DTOs.CasilleroDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class CasilleroDAO extends DataHelperSQLiteDAO<CasilleroDTO> {

    public CasilleroDAO() throws AppException {
        super(CasilleroDTO.class, "Casillero", "IdCasillero");
    }

    /**
     * Obtiene un casillero por ID
     */
    public CasilleroDTO obtenerPorId(int idCasillero) throws AppException {
        String query =
            "SELECT " +
            "  idCasillero       AS IdCasillero, " +
            "  idEstadoCasillero AS IdEstadoCasillero, " +
            "  idEstudiante      AS IdEstudiante, " +
            "  IntentosFallidos  AS IntentosFallidos, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM Casillero " +
            "WHERE idCasillero = ? " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }

    /**
     * Lista todos los casilleros
     */
    public List<CasilleroDTO> listarTodos() throws AppException {
        String query =
            "SELECT " +
            "  idCasillero       AS IdCasillero, " +
            "  idEstadoCasillero AS IdEstadoCasillero, " +
            "  idEstudiante      AS IdEstudiante, " +
            "  IntentosFallidos  AS IntentosFallidos, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM Casillero " +
            "ORDER BY idCasillero ASC";

        try (var stmt = openConnection().prepareStatement(query);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarTodos");
        }
    }

    /**
     * Lista casilleros por estudiante (mis casilleros)
     */
    public List<CasilleroDTO> obtenerPorEstudiante(int idEstudiante) throws AppException {
        String query =
            "SELECT " +
            "  idCasillero       AS IdCasillero, " +
            "  idEstadoCasillero AS IdEstadoCasillero, " +
            "  idEstudiante      AS IdEstudiante, " +
            "  IntentosFallidos  AS IntentosFallidos, " +
            "  Descripcion       AS Descripcion, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica " +
            "FROM Casillero " +
            "WHERE idEstudiante = ? " +
            "ORDER BY idCasillero ASC";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idEstudiante);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorEstudiante");
        }
    }

    /**
     * Lista casilleros disponibles (por nombre del estado: "Ready to Unlock")
     * Nota: esto filtra por el catálogo EstadoCasillero.Nombre
     */
    public List<CasilleroDTO> obtenerDisponibles() throws AppException {
        String query =
            "SELECT " +
            "  c.idCasillero       AS IdCasillero, " +
            "  c.idEstadoCasillero AS IdEstadoCasillero, " +
            "  c.idEstudiante      AS IdEstudiante, " +
            "  c.IntentosFallidos  AS IntentosFallidos, " +
            "  c.Descripcion       AS Descripcion, " +
            "  c.Estado            AS Estado, " +
            "  c.FechaCreacion     AS FechaCreacion, " +
            "  c.FechaModificacion AS FechaModifica " +
            "FROM Casillero c " +
            "JOIN EstadoCasillero ec ON ec.idEstadoCasillero = c.idEstadoCasillero " +
            "WHERE ec.Nombre = 'Ready to Unlock' " +
            "ORDER BY c.idCasillero ASC";

        try (var stmt = openConnection().prepareStatement(query);
             var rs = stmt.executeQuery()) {
            return mapResultSetToEntityList(rs);
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerDisponibles");
        }
    }

    /**
     * Incrementa intentos fallidos en 1
     */
    public void incrementarIntentos(int idCasillero) throws AppException {
        String query =
            "UPDATE Casillero " +
            "SET IntentosFallidos = IntentosFallidos + 1, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE idCasillero = ?";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "incrementarIntentos");
        }
    }

    /**
     * Resetea intentos fallidos a 0
     */
    public void resetIntentos(int idCasillero) throws AppException {
        String query =
            "UPDATE Casillero " +
            "SET IntentosFallidos = 0, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE idCasillero = ?";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "resetIntentos");
        }
    }

    /**
     * Actualiza el estado de un casillero (por idEstadoCasillero)
     */
    public void actualizarEstado(int idCasillero, int idEstadoCasillero) throws AppException {
        String query =
            "UPDATE Casillero " +
            "SET idEstadoCasillero = ?, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE idCasillero = ?";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idEstadoCasillero);
            stmt.setInt(2, idCasillero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "actualizarEstado");
        }
    }

    /**
     * Asigna o libera un estudiante en el casillero
     * (si idEstudiante es null, lo libera)
     */
    public void asignarEstudiante(int idCasillero, Integer idEstudiante) throws AppException {
        String query =
            "UPDATE Casillero " +
            "SET idEstudiante = ?, " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE idCasillero = ?";

        try (var stmt = openConnection().prepareStatement(query)) {
            if (idEstudiante == null) stmt.setNull(1, java.sql.Types.INTEGER);
            else stmt.setInt(1, idEstudiante);

            stmt.setInt(2, idCasillero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "asignarEstudiante");
        }
    }
}

