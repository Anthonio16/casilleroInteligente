package DataAccess.DAOs;

import DataAccess.DTOs.TokenAccesoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.util.List;

public class TokenAccesoDAO extends DataHelperSQLiteDAO<TokenAccesoDTO> {

    public TokenAccesoDAO() throws AppException {
        super(TokenAccesoDTO.class, "TokenAcceso", "IdTokenAcceso");
    }

    public Integer crearToken15Min(int IdSolicitud, int IdCasillero, String TokenHash) throws AppException {
        String sql =
            "INSERT INTO TokenAcceso (IdSolicitud, IdCasillero, TokenHash, Estado, FechaExpiracion) " +
            "VALUES (?, ?, ?, 'A', datetime('now','localtime','+15 minutes'))";

        try (var stmt = openConnection().prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, IdSolicitud);
            stmt.setInt(2, IdCasillero);
            stmt.setString(3, TokenHash);
            stmt.executeUpdate();

            try (var keys = stmt.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "crearToken15Min");
        }
    }

    public TokenAccesoDTO obtenerPorId(int IdTokenAcceso) throws AppException {
        String sql = "SELECT * FROM TokenAcceso WHERE IdTokenAcceso = ? AND Estado='A' LIMIT 1";
        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, IdTokenAcceso);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerPorId");
        }
    }

    public TokenAccesoDTO obtenerActivoPorCasillero(int IdCasillero) throws AppException {
        String sql =
            "SELECT * FROM TokenAcceso " +
            "WHERE IdCasillero = ? AND Estado='A' " +
            "  AND (FechaExpiracion IS NULL OR FechaExpiracion > datetime('now','localtime')) " +
            "ORDER BY FechaModificacion DESC, IdTokenAcceso DESC " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, IdCasillero);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "obtenerActivoPorCasillero");
        }
    }

    public List<TokenAccesoDTO> listarPorSolicitud(int IdSolicitud) throws AppException {
        String sql =
            "SELECT * FROM TokenAcceso " +
            "WHERE IdSolicitud = ? AND Estado='A' " +
            "ORDER BY FechaModificacion DESC, IdTokenAcceso DESC";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, IdSolicitud);
            try (var rs = stmt.executeQuery()) {
                return mapResultSetToEntityList(rs);
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "listarPorSolicitud");
        }
    }

    public void desactivarToken(int IdTokenAcceso) throws AppException {
        String sql =
            "UPDATE TokenAcceso " +
            "SET Estado='X', FechaModificacion=datetime('now','localtime') " +
            "WHERE IdTokenAcceso = ? AND Estado='A'";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, IdTokenAcceso);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "desactivarToken");
        }
    }

    public void desactivarTokensPorCasillero(int IdCasillero) throws AppException {
        String sql =
            "UPDATE TokenAcceso " +
            "SET Estado='X', FechaModificacion=datetime('now','localtime') " +
            "WHERE IdCasillero = ? AND Estado='A'";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, IdCasillero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "desactivarTokensPorCasillero");
        }
    }

    // (OPCIONAL) Limpieza: marca como X los tokens vencidos
    public void expirarTokensVencidos() throws AppException {
        String sql =
            "UPDATE TokenAcceso " +
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

