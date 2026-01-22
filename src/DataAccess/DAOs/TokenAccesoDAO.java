package DataAccess.DAOs;

import DataAccess.DTOs.TokenAccesoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class TokenAccesoDAO extends DataHelperSQLiteDAO<TokenAccesoDTO> {

    public TokenAccesoDAO() throws AppException {
        super(TokenAccesoDTO.class, "Tokenacceso", "IdTokenAcceso");
    }

    /**
     * Crea un token ACTIVO (Estado='A') para una solicitud/casillero.
     * fechaExpiracion puede ser null si no deseas expirar.
     */
    public Integer crearToken(int idSolicitud, int idCasillero, String tokenHash, String fechaExpiracion) throws AppException {
        String query =
            "INSERT INTO Tokenacceso (idSolicitud, idCasillero, TokenHash, Estado, FechaExpiracion) " +
            "VALUES (?, ?, ?, 'A', ?)";

        try (var stmt = openConnection().prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idSolicitud);
            stmt.setInt(2, idCasillero);
            stmt.setString(3, tokenHash);
            stmt.setString(4, fechaExpiracion); // puede ser null
            stmt.executeUpdate();

            try (var keys = stmt.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "crearToken");
        }
    }

    /**
     * Crea token con expiración automática +15 min (Estado='A').
     */
    public Integer crearToken15Min(int idSolicitud, int idCasillero, String tokenHash) throws AppException {
        String query =
            "INSERT INTO Tokenacceso (idSolicitud, idCasillero, TokenHash, Estado, FechaExpiracion) " +
            "VALUES (?, ?, ?, 'A', datetime('now','localtime','+15 minutes'))";

        try (var stmt = openConnection().prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idSolicitud);
            stmt.setInt(2, idCasillero);
            stmt.setString(3, tokenHash);
            stmt.executeUpdate();

            try (var keys = stmt.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "crearToken15Min");
        }
    }

    public TokenAccesoDTO obtenerPorId(int idTokenAcceso) throws AppException {
        String query =
            "SELECT " +
            "  idTokenacceso     AS IdTokenAcceso, " +
            "  idSolicitud       AS IdSolicitud, " +
            "  idCasillero       AS IdCasillero, " +
            "  TokenHash         AS TokenHash, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica, " +
            "  FechaExpiracion   AS FechaExpiracion " +
            "FROM Tokenacceso " +
            "WHERE idTokenacceso = ? " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idTokenAcceso);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }

    /**
     * Token ACTIVO del casillero (Estado='A') y NO vencido (o expiración null).
     */
    public TokenAccesoDTO obtenerActivoPorCasillero(int idCasillero) throws AppException {
        String query =
            "SELECT " +
            "  idTokenacceso     AS IdTokenAcceso, " +
            "  idSolicitud       AS IdSolicitud, " +
            "  idCasillero       AS IdCasillero, " +
            "  TokenHash         AS TokenHash, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica, " +
            "  FechaExpiracion   AS FechaExpiracion " +
            "FROM Tokenacceso " +
            "WHERE idCasillero = ? " +
            "  AND Estado = 'A' " +
            "  AND (FechaExpiracion IS NULL OR FechaExpiracion > datetime('now','localtime')) " +
            "ORDER BY FechaModificacion DESC, idTokenacceso DESC " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerActivoPorCasillero");
        }
    }

    /**
     * Lista tokens por solicitud (solo activos si quieres).
     * Nota: aquí te dejo sin filtro de Estado por si quieres ver histórico.
     */
    public List<TokenAccesoDTO> listarPorSolicitud(int idSolicitud) throws AppException {
        String query =
            "SELECT " +
            "  idTokenacceso     AS IdTokenAcceso, " +
            "  idSolicitud       AS IdSolicitud, " +
            "  idCasillero       AS IdCasillero, " +
            "  TokenHash         AS TokenHash, " +
            "  Estado            AS Estado, " +
            "  FechaCreacion     AS FechaCreacion, " +
            "  FechaModificacion AS FechaModifica, " +
            "  FechaExpiracion   AS FechaExpiracion " +
            "FROM Tokenacceso " +
            "WHERE idSolicitud = ? " +
            "ORDER BY FechaModificacion DESC, idTokenacceso DESC";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idSolicitud);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarPorSolicitud");
        }
    }

    /**
     * Desactiva (Estado='X') un token específico y actualiza FechaModificacion.
     */
    public void desactivarToken(int idTokenAcceso) throws AppException {
        String query =
            "UPDATE Tokenacceso " +
            "SET Estado = 'X', " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE idTokenacceso = ?";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idTokenAcceso);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "desactivarToken");
        }
    }

    /**
     * Desactiva (Estado='X') todos los tokens activos de un casillero.
     */
    public void desactivarTokensPorCasillero(int idCasillero) throws AppException {
        String query =
            "UPDATE Tokenacceso " +
            "SET Estado = 'X', " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE idCasillero = ? AND Estado = 'A'";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "desactivarTokensPorCasillero");
        }
    }

    /**
     * Opción limpia: marca como 'X' los tokens vencidos que sigan en 'A'.
     * (Así no inventas estados nuevos y todo queda en A/X).
     */
    public void expirarTokensVencidos() throws AppException {
        String query =
            "UPDATE Tokenacceso " +
            "SET Estado = 'X', " +
            "    FechaModificacion = datetime('now','localtime') " +
            "WHERE Estado = 'A' " +
            "  AND FechaExpiracion IS NOT NULL " +
            "  AND FechaExpiracion <= datetime('now','localtime')";

        try (var stmt = openConnection().prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "expirarTokensVencidos");
        }
    }
}
