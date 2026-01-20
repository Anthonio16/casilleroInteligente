package DataAccess.DAOs;
import DataAccess.DTOs.RegistroEventoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class RegistroEventoDAO extends DataHelperSQLiteDAO<RegistroEventoDTO> {
    public RegistroEventoDAO() throws AppException {
        super(RegistroEventoDTO.class, "RegistroEvento", "IdRegistroEvento");
    }
    
}