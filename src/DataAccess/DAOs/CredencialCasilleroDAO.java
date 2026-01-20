package DataAccess.DAOs;
import DataAccess.DTOs.CredencialCasilleroDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class CredencialCasilleroDAO extends DataHelperSQLiteDAO<CredencialCasilleroDTO> {
    public CredencialCasilleroDAO() throws AppException {
        super(CredencialCasilleroDTO.class, "CredencialCasillero", "IdCredencialCasillero");
    }
    
}
