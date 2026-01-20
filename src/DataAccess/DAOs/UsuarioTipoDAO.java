package DataAccess.DAOs;

import DataAccess.DTOs.UsuarioTipoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class UsuarioTipoDAO extends DataHelperSQLiteDAO<UsuarioTipoDTO> {

    public UsuarioTipoDAO() throws AppException {
        super(UsuarioTipoDTO.class, "UsuarioTipo", "IdUsuarioTipo");
    }
    
}
