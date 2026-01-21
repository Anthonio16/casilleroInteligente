package DataAccess.DAOs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccess.DTOs.CredencialCasilleroDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;


public class CredencialCasilleroDAO extends DataHelperSQLiteDAO<CredencialCasilleroDTO> {
    public CredencialCasilleroDAO() throws AppException {
        super(CredencialCasilleroDTO.class, "CredencialCasillero", "IdCredencialCasillero");
    }
    public CredencialCasilleroDTO getByCasillero (int idCasillero) throws AppException {
        String query = "SELECT * FROM CredencialCasillero WHERE IdCasillero = ?";
        try (PreparedStatement stmt = openConnection().prepareStatement(query)) {
            stmt.setInt(1, idCasillero);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? mapResultSetToEntity(rs) : null;
            }
        } catch (SQLException e) {
            throw new AppException(null, e, getClass(), "getByCasillero");
        }
    }

    public void updatePinHash(int idCredencialCasillero, String nuevoPinHash) throws AppException {
        String query = "UPDATE CredencialCasillero SET PinHash = ? WHERE IdCredencialCasillero = ?";
        try (PreparedStatement stmt = openConnection().prepareStatement(query)) {
            stmt.setString(1, nuevoPinHash);
            stmt.setInt(2, idCredencialCasillero);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new AppException(null, e, getClass(), "updatePinHash");
        }
    }
}
