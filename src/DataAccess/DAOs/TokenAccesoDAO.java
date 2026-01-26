package DataAccess.DAOs;

import DataAccess.DTOs.TokenAccesoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class TokenAccesoDAO extends DataHelperSQLiteDAO<TokenAccesoDTO> {

    public TokenAccesoDAO() throws AppException {
        super(TokenAccesoDTO.class, "Tokenacceso", "idTokenacceso");
    }

    public Integer crearToken15Min(int idSolicitud, int idCasillero, String tokenHash) throws AppException {
        String sql =
            "INSERT INTO Tokenacceso (idSolicitud, idCasillero, TokenHash, Estado, FechaExpiracion) " +
            "VALUES (?, ?, ?, 'A', datetime('now','localtime','+15 minutes'))";

        try (var stmt = openConnection().prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
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

    public TokenAccesoDTO obtenerPorId(int idTokenacceso) throws AppException {
        String sql = "SELECT * FROM Tokenacceso WHERE idTokenacceso = ? AND Estado='A' LIMIT 1";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idTokenacceso);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }

    public TokenAccesoDTO obtenerActivoPorCasillero(int idCasillero) throws AppException {
        String sql =
            "SELECT * FROM Tokenacceso " +
            "WHERE idCasillero = ? AND Estado='A' " +
            "  AND (FechaExpiracion IS NULL OR FechaExpiracion > datetime('now','localtime')) " +
            "ORDER BY FechaModificacion DESC, idTokenacceso DESC " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerActivoPorCasillero");
        }
    }

    public List<TokenAccesoDTO> listarPorSolicitud(int idSolicitud) throws AppException {
        String sql =
            "SELECT * FROM Tokenacceso " +
            "WHERE idSolicitud = ? AND Estado='A' " +
            "ORDER BY FechaModificacion DESC, idTokenacceso DESC";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idSolicitud);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarPorSolicitud");
        }
    }

    public void desactivarToken(int idTokenacceso) throws AppException {
        String sql =
            "UPDATE Tokenacceso " +
            "SET Estado='X', FechaModificacion=datetime('now','localtime') " +
            "WHERE idTokenacceso = ? AND Estado='A'";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idTokenacceso);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "desactivarToken");
        }
    }

    public void desactivarTokensPorCasillero(int idCasillero) throws AppException {
        String sql =
            "UPDATE Tokenacceso " +
            "SET Estado='X', FechaModificacion=datetime('now','localtime') " +
            "WHERE idCasillero = ? AND Estado='A'";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCasillero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "desactivarTokensPorCasillero");
        }
    }

    // (OPCIONAL) Limpieza: marca como X los tokens vencidos
    public void expirarTokensVencidos() throws AppException {
        String sql =
            "UPDATE Tokenacceso " +
            "SET Estado='X', FechaModificacion=datetime('now','localtime') " +
            "WHERE Estado='A' AND FechaExpiracion IS NOT NULL " +
            "  AND FechaExpiracion <= datetime('now','localtime')";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "expirarTokensVencidos");
        }
    }
}

