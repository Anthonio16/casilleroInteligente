package DataAccess.DAOs;

import DataAccess.DTOs.CredencialCasilleroDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CredencialCasilleroDAO extends DataHelperSQLiteDAO<CredencialCasilleroDTO> {

    public CredencialCasilleroDAO() throws AppException {
        super(CredencialCasilleroDTO.class, "CredencialCasillero", "IdCredencialCasillero");
    }

    public CredencialCasilleroDTO getByCasillero(int idCasillero) throws AppException {
        String query =
            "SELECT " +
            "  idCredencialCasillero AS IdCredencialCasillero, " +
            "  idCasillero           AS IdCasillero, " +
            "  pinHash               AS pinHash " +
            "FROM CredencialCasillero " +
            "WHERE idCasillero = ? " +
            "LIMIT 1";

        try (PreparedStatement stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "getByCasillero");
        }
    }

    public void updatePinHashByCasillero(int idCasillero, String nuevoPinHash) throws AppException {
        String query =
            "UPDATE CredencialCasillero " +
            "SET pinHash = ? " +
            "WHERE idCasillero = ?";

        try (PreparedStatement stmt = openConnection().prepareStatement(query)) {
            stmt.setString(1, nuevoPinHash);
            stmt.setInt(2, idCasillero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "updatePinHashByCasillero");
        }
    }

    public void updatePinHashById(int idCredencialCasillero, String nuevoPinHash) throws AppException {
        String query =
            "UPDATE CredencialCasillero " +
            "SET pinHash = ? " +
            "WHERE idCredencialCasillero = ?";

        try (PreparedStatement stmt = openConnection().prepareStatement(query)) {
            stmt.setString(1, nuevoPinHash);
            stmt.setInt(2, idCredencialCasillero);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new AppException(null, e, getClass(), "updatePinHashById");
        }
    }
}

