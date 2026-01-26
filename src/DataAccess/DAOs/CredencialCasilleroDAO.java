package DataAccess.DAOs;

import DataAccess.DTOs.CredencialCasilleroDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class CredencialCasilleroDAO extends DataHelperSQLiteDAO<CredencialCasilleroDTO> {

    public CredencialCasilleroDAO() throws AppException {
        super(CredencialCasilleroDTO.class, "CredencialCasillero", "idCredencialCasillero");
    }

    public CredencialCasilleroDTO getByCasillero(int idCasillero) throws AppException {
        String sql =
            "SELECT * FROM CredencialCasillero " +
            "WHERE idCasillero = ? AND Estado = 'A' " +
            "LIMIT 1";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setInt(1, idCasillero);
            try (var rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "getByCasillero");
        }
    }

    public boolean updatePinHashByCasillero(int idCasillero, String nuevoPinHash) throws AppException {
        String sql =
            "UPDATE CredencialCasillero " +
            "SET pinHash = ?, FechaModificacion = datetime('now','localtime') " +
            "WHERE idCasillero = ? AND Estado = 'A'";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setString(1, nuevoPinHash);
            stmt.setInt(2, idCasillero);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "updatePinHashByCasillero");
        }
    }

    public boolean updatePinHashById(int idCredencialCasillero, String nuevoPinHash) throws AppException {
        String sql =
            "UPDATE CredencialCasillero " +
            "SET pinHash = ?, FechaModificacion = datetime('now','localtime') " +
            "WHERE idCredencialCasillero = ? AND Estado = 'A'";

        try (var stmt = openConnection().prepareStatement(sql)) {
            stmt.setString(1, nuevoPinHash);
            stmt.setInt(2, idCredencialCasillero);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "updatePinHashById");
        }
    }
}


